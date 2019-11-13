
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

async function getPatientsGraph(patientId, patientCount = 100, depth = 0) {
    patientCount = patientCount == null || patientCount == "" ? 100 : patientCount;
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
function doDrawNodeGraph() {
    var nodes = {};

    // Compute the distinct nodes from the links.
    links.forEach(function (link) {
        link.source = nodes[link.source] ||
            (nodes[link.source] = { name: link.source, destination: link.target });
        link.target = nodes[link.target] ||
            (nodes[link.target] = { name: link.target });
    });

    var parent = d3.select("#patient-similarity-node-graph");

    var svg = parent.append("svg"),
        width = +parent.style("width").slice(0, -2) + 200,
        height = +parent.style("height").slice(0, -2) + 200;

    svg.attr("width", width)
        .attr("height", height)
        .attr("preserveAspectRatio", "xMinYMin meet")

    var color = d3.scaleLinear().domain([6, 26]).range(["white", "blue"]);

    var force = d3.forceSimulation()
        .nodes(d3.values(nodes))
        .force("link", d3.forceLink(links).distance(100))
        .force('center', d3.forceCenter(width / 2, height / 2))
        .force("x", d3.forceX())
        .force("y", d3.forceY())
        .force("charge", d3.forceManyBody().strength(-250))
        .alphaTarget(1)
        .on("tick", tick);

    // add the links and the arrows
    var path = svg.append("g")
        .selectAll("path")
        .data(links)
        .enter()
        .append("path")
        .attr("class", function (d) { return d.value == 0 ? "dashedLink" : "solidLink"; }); //set the solid/dashed links

    // define the nodes
    var node = svg.selectAll(".node")
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
            minRadius = 10;

            let tempWeight = node.filter(function (l) {
                return d.name == l.destination;
            }).size();

            d.weight = minRadius + (tempWeight * 2);
            return d.weight;
        })
        .style("fill", function (d) {
            return color(d.weight);
        });

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
    node.on("dblclick", function (d) {
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


    // add the curvy lines
    function tick() {
        path.attr("d", function (d) {
            var dx = d.target.x - d.source.x,
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





