
// get the data
links = [
    {
        "source": "P-0000110",
        "target": "P-0004706",
        "value": 1
    },
    {
        "source": "P-0008312",
        "target": "P-0004706",
        "value": 0
    },
    {
        "source": "P-0008312",
        "target": "P-0003155",
        "value": 1
    },
    {
        "source": "P-0003415",
        "target": "P-0004706",
        "value": 0
    },
    {
        "source": "P-0003415",
        "target": "P-0003617",
        "value": 1
    },
    {
        "source": "P-0008312",
        "target": "P-0004112",
        "value": 0
    },
    {
        "source": "P-0003415",
        "target": "P-0004112",
        "value": 1
    },
    {
        "source": "P-0003411",
        "target": "P-0004706",
        "value": 0
    },
    {
        "source": "P-0003591",
        "target": "P-0004706",
        "value": 1
    },
    {
        "source": "P-0009846",
        "target": "P-0000110",
        "value": 0
    },
    {
        "source": "P-0003697",
        "target": "P-0000110",
        "value": 1
    },
    {
        "source": "P-0003677",
        "target": "P-0003611",
        "value": 0
    },
    {
        "source": "P-0004124",
        "target": "P-0004112",
        "value": 1
    },
    {
        "source": "P-0003611",
        "target": "P-0004112",
        "value": 0
    },
    {
        "source": "P-0004193",
        "target": "P-0003415",
        "value": 1
    },
    {
        "source": "P-0003491",
        "target": "P-0000110",
        "value": 0
    },
    {
        "source": "P-0003217",
        "target": "P-0003591",
        "value": 1
    },
    {
        "source": "P-0003217",
        "target": "P-0003167",
        "value": 0
    },
    {
        "source": "P-0003167",
        "target": "P-0003172",
        "value": 1
    },
    {
        "source": "P-0003044",
        "target": "P-0003591",
        "value": 0
    },
    {
        "source": "P-0003023",
        "target": "P-0003677",
        "value": 1
    },
    {
        "source": "P-0003015",
        "target": "P-0004706",
        "value": 0
    },
    {
        "source": "P-0005453",
        "target": "P-0004706",
        "value": 1
    },
    {
        "source": "P-0001238",
        "target": "P-0000110",
        "value": 0
    },
    {
        "source": "P-0003891",
        "target": "P-0003167",
        "value": 1
    },
    {
        "source": "P-0003168",
        "target": "P-0003677",
        "value": 0
    },
    {
        "source": "P-0003644",
        "target": "P-0003415",
        "value": 1
    },
    {
        "source": "P-0003011",
        "target": "P-0001264",
        "value": 0
    },
    {
        "source": "P-0001264",
        "target": "P-0004706",
        "value": 1
    }
];

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
    width = +parent.style("width").slice(0, -2),
    height = +parent.style("height").slice(0, -2);

svg.attr("width", width)
    .attr("height", height)

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

function dragged(d) {
    d.fx = d3.event.x;
    d.fy = d3.event.y;
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
