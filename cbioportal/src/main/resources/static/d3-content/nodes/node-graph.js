
// get the data
let links = [];

async function getPatientListInformation() {
    let client = new Api();

    let patientListResult = await client.GetAllPatients();

    return patientListResult.data.map((patient) => {
        return {
            patientId: patient.patientId,
            value: patient.patientId + " | " + patient.sex + " | " + patient.vitalStatus + " | " + patient.samples[0].cancerType
        }
    });
}

async function getPatientsSimilarityGraph(patientId, patientCount, depth = 0) {
    patientCount = patientCount == null || patientCount == "" ? DEFAULT_MAX_PATIENT_COUNT : patientCount;
    depth = depth == null || depth == "" ? 0 : depth;

    let localList = [];

    let client = new Api();

    let response = await client.GetSimilarPatients(patientId, patientCount);

    response.data.map((patient) => {
        patientList.push(patient);

        localList.push({
            source: response.config.params.id,
            target: patient.patientId,
            value: patient.similarity * 10
        });
    });

    links = await recursiveGetPatientList(patientCount, depth, 0, [], localList); //depth - 1 is actual depth
}

async function recursiveGetPatientList(patientCount, depth, iteration, state, next) {
    let localPatientList = [];

    if (depth == iteration) {
        return new Promise((resolve, reject) => { resolve([...state, ...next]); });
    } else {
        let client = new Api();
        let filteredArr = next.filter(p => state.map(s => s.source).indexOf(p.target) < 0); //excluding targets already calculated        

        const response = await Promise.all(filteredArr.map(async (patientNode) => {
            return await client.GetSimilarPatients(patientNode.target, patientCount);
        }));

        response.map((content) => {
            content.data.map((patient) => {
                patientList.push(patient);

                localPatientList.push({
                    source: content.config.params.id,
                    target: patient.patientId,
                    value: patient.similarity * 10
                });
            });
        });

        return await recursiveGetPatientList(patientCount, depth, iteration + 1, [...state, ...next], localPatientList);
    }
}

//TODO: COnfirm graph style before final version
function doDrawNodeGraph(initialPatientId) {
    let nodes = {};
    let fixedNodes = [];
    let highlightedCancerTypes = [];

    // Compute the distinct nodes from the links.
    links.forEach(function (link) {
        link.source = nodes[link.source] ||
            (nodes[link.source] = { name: link.source, destination: link.target });
        link.target = nodes[link.target] ||
            (nodes[link.target] = { name: link.target });
    });

    let parent = d3.select("#patient-similarity-node-graph");

    let svg = parent.append("svg"),
        width = parent.node().getBoundingClientRect().width - 30,
        height = parent.node().getBoundingClientRect().height - 30;
    //width = +parent.style("width").slice(0, -2) + 200,
    //height = +parent.style("height").slice(0, -2) + 200;

    svg.attr("width", width)
        .attr("height", height)
        .attr("preserveAspectRatio", "xMinYMin meet")

    let color = d3.scaleOrdinal(d3.schemeTableau10);    //d3.schemePaired

    let force = d3.forceSimulation()
        .nodes(d3.values(nodes))
        .force("link", d3.forceLink(links).distance(100))
        .force('center', d3.forceCenter(width / 2, height / 2))
        .force("x", d3.forceX())
        .force("y", d3.forceY())
        .force("charge", d3.forceManyBody().strength(-100))
        .alphaTarget(0.25)
        .on("tick", tick)


    // add the links and the arrows
    let path = svg.append("g")
        .selectAll("path")
        .data(links)
        .enter()
        .append("path")
        .attr("class", "solidLink"); //set the solid/dashed links

    // define the nodes
    let node = svg.selectAll(".node")
        .data(force.nodes())
        .enter().append("g")
        .attr("class", "node")
        .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended)
        );

    // add the nodes
    node.append("circle")
        .attr("r", function (d) {
            if (d.name == initialPatientId) {
                return 10;
            }
            minRadius = 6;

            return minRadius;
        });
    // .style("fill", function (d) {
    //    return color(20);
    // });

    // add the node labels
    node.append("text")
        .text(function (d) {
            return d.name;
        })
        .attr('x', 10)
        .attr('y', -15);

    //double click fixes or releases a node movement
    //this double click method is a little unreliable. Sometimes it will force the movement of the 
    //graph before recognizing the event. Slow double click should receive better responses
    node.on("click", function (d) {

        d.fixed = !d.fixed;

        if (d.fixed) {
            d3.select(this).selectAll("circle").style("fill", "red");
            d.fx = d.x;
            d.fy = d.y;
        } else {
            d3.select(this).selectAll("circle").style("fill", color(d.weight));
            d.fx = null;
            d.fy = null;
        }
    });

    node.on("customSelect", function (e) {
        if (patientList.some(p => p.cancerType == d3.event.detail.cancerType && p.patientId == e.name)) {
            if (d3.event.detail.active) {
                d3.select(this).selectAll("circle").style("fill", d3.event.detail.fillColor);
            } else {
                e.fixed ? d3.select(this).selectAll("circle").style("fill", "red") : d3.select(this).selectAll("circle").style("fill", null);
            }
        }
    });

    // add the curvy lines
    function tick() {
        path.attr("d", function (d) {
            let dx = d.target.x - d.source.x,
                dy = d.target.y - d.source.y,
                dr = Math.sqrt(dx * dx + dy * dy);

            return "M" +
                d.source.x + "," +
                d.source.y + "A" +
                dr + "," + dr + " 0 0,1 " +
                d.target.x + "," +
                d.target.y;
        });

        node
            .attr("transform", function (d) {
                return "translate(" + d.x + "," + d.y + ")";
            })


        node.attr("cx", function (d) { return d.x = Math.max(80, Math.min(width - 80, d.x)); })
            .attr("cy", function (d) { return d.y = Math.max(30, Math.min(height - 30, d.y)); });
    };

    function dragstarted(d) {
        if (!d3.event.active) force.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
    };
    //avoid drag outside the div box
    function dragged(d) {
        d.fx = d3.event.x > width - 20 ? width - 20 : d3.event.x < 20 ? 20 : d3.event.x;
        d.fy = d3.event.y > height - 20 ? height - 20 : d3.event.y < 20 ? 20 : d3.event.y;
    };

    function dragended(d) {
        if (!d3.event.active) force.alphaTarget(0);
        if (d.fixed == true) {
            d.fx = d.x;
            d.fy = d.y;
        }
        else {
            d.fx = null;
            d.fy = null;
        }

    };
}

function getUniquePatientList() {
    const uniquePatientList = [];
    const map = new Map();
    for (const patient of patientList) {
        if (!map.has(patient.patientId)) {
            map.set(patient.patientId, true);
            uniquePatientList.push({
                patientId: patient.patientId,
                similarity: patient.similarity,
                cancerType: patient.cancerType,
                sex: patient.sex,
                vitalStatus: patient.vitalStatus,
                smokingHistory: patient.smokingHistory,
                osMonths: patient.osMonths,
                osStatus: patient.osStatus,
                sampleCollectionSource: patient.sampleCollectionSource,
                specimenPreservationType: patient.specimenPreservationType,
                specimenType: patient.specimenType,
                dnaInput: patient.dnaInput,
                sampleCoverage: patient.sampleCoverage,
                tumorPurity: patient.tumorPurity,
                matchedStatus: patient.matchedStatus,
                sampleType: patient.sampleType,
                primarySite: patient.primarySite,
                metastaticSite: patient.metastaticSite,
                sampleClass: patient.sampleClass,
                oncotreeCode: patient.oncotreeCode,
                cancerTypeDetailed: patient.cancerTypeDetailed
            });
        }
    }
    return uniquePatientList;
}






