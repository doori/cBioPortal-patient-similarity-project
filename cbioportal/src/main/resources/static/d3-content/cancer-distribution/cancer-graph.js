

async function getCancerDistributionGraph(patientId) {
    let client = new Api();

    let result = await client.GetCancerTypePrediction(patientId);

    patientCancerDistribution = result.data.classDistribution;
}



function doDrawCancerDistributionGraph(cancerDistributionRawData) {
    const cancerTypeElemLimit = 10;

    let cancerDistributionData = [];

    Object.keys(cancerDistributionRawData).map((key) => {
        var value = cancerDistributionRawData[key];
        cancerDistributionData.push({ 'key': key, 'value': value });
    });

    const tooltip = d3.select("body").append("div").attr("class", "toolTip");

    let parent = d3.select("#patient-similarity-cancer-distribution");

    let sortedCancerDistribution = cancerDistributionData.sort((a, b) => b.value - a.value);
    let filteredCancerDistribution = sortedCancerDistribution.slice(0, cancerTypeElemLimit);

    let margin = {
        top: 20,
        right: 50,
        bottom: 150,
        left: 40,
    },
        width = parent.node().getBoundingClientRect().width - 50,
        height = parent.node().getBoundingClientRect().height - margin.bottom;

    let svg = parent.append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom);

    let g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    var x = d3.scaleBand()
        .rangeRound([0, width])
        .padding(0.1);

    var y = d3.scaleLinear()
        .rangeRound([height, 0]);

    x.domain(filteredCancerDistribution.map(function (d) {
        return d.key;
    }));

    y.domain([0, d3.max(filteredCancerDistribution, function (d) {
        return Number(d.value);
    })]);

    g.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x))
        .selectAll("text")
        .attr("y", 10)
        .call(wrap, x.bandwidth());

    g.append("g")
        .call(d3.axisLeft(y))

    g.selectAll(".bar")
        .data(filteredCancerDistribution)
        .enter().append("rect")
        .attr("class", "bar")
        .attr("x", function (d) {
            return x(d.key);
        })
        .attr("y", function (d) {
            return y(Number(d.value));
        })
        .attr("width", x.bandwidth())
        .attr("height", function (d) {
            return height - y(Number(d.value));
        })
        .on("mousemove", function (d) {
            tooltip
                .style("left", d3.event.pageX - 50 + "px")
                .style("top", d3.event.pageY - 70 + "px")
                .style("display", "inline-block")
                .style("overflow", "visible")
                .html((d.key) + "<br>" + ((d.value / 100) * 100).toFixed(2) + "%");
        })
        .on("mouseout", function (d) { tooltip.style("display", "none"); });

    function wrap(text, width) {
        text.each(function () {
            let text = d3.select(this),
                words = text.text().split(/\s+/).reverse(),
                word,
                line = [],
                lineNumber = 0,
                lineHeight = 1.1, // ems
                y = text.attr("y"),
                dy = parseFloat(text.attr("dy")),
                tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em");
            while (word = words.pop()) {
                line.push(word);
                tspan.text(line.join(" "));
                if (tspan.node().getComputedTextLength() > width) {
                    line.pop();
                    tspan.text(line.join(" "));
                    line = [word];
                    tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
                }
            }
        });
    }
}
