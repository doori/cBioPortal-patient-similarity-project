

async function getCancerDistributionGraph(patientId) {
    let client = new Api();

    let result = await client.GetCancerTypePrediction(patientId);

    patientCancerDistribution = result.data;
}



function doDrawCancerDistributionGraph(data) {
    let parent = d3.select("#patient-similarity-cancer-distribution");
    console.log('parent.node().getBoundingClientRect().width')
    console.log(parent.node().getBoundingClientRect().width)

    console.log('parent.node().getBoundingClientRect().height')
    console.log(parent.node().getBoundingClientRect().height) //350

    // let svg = parent.append("svg")

    // console.log('+parent.style("width").slice(0, -2) + 200');
    // console.log(+parent.style("width").slice(0, -2) + 200)

    // // let svg = d3.select("#patient-similarity-cancer-distribution"),
    // let margin = {
    //     top: 20,
    //     right: 20,
    //     bottom: 20,
    //     left: 50,
    // };
    // // width = +svg.attr("width") - margin.left - margin.right,
    // // height = +svg.attr("height") - margin.top - margin.bottom;
    // const tooltip = d3.select("body").append("div").attr("class", "toolTip");
    // let x = d3.scaleBand().rangeRound([0, width]).padding(0.1),
    //     y = d3.scaleLinear().rangeRound([height, 0]);
    // let g = svg.append("g")
    //     .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    // //import test data from csv and roll up to get the frequency for each type of cancer
    // //result is array of objects

    // // let cancerCount = d3.nest()
    // //     .key(function (d) { return d.CANCER_TYPE; })
    // //     .rollup(function (v) { return v.length; })
    // //     .entries(data);

    // const cancerCount = [
    //     { key: "Some Cancer Type A", value: "123" },
    //     { key: "Some Cancer Type B", value: "456" },
    //     { key: "Some Cancer Type C", value: "789" },
    // ]



    // //sort array by count, descending + limit to the top 15
    // cancerCount.sort((a, b) => (a.value > b.value) ? -1 : 1)
    // let top15 = cancerCount.slice(0, 15);
    // console.log('JSON.stringify(top15)');
    // console.log(JSON.stringify(top15));
    // //basic sum function
    // const mysum = function (items, prop) {
    //     return items.reduce(function (a, b) {
    //         return a + b[prop];
    //     }, 0);
    // };
    // //get the overall # of patients
    // mytotal = mysum(cancerCount, 'value');
    // console.log(mytotal)
    // //group cancer types beyond the top 15 into an aggregated other
    // othertotal = mytotal - mysum(top15, 'value');
    // console.log(othertotal)
    // other_obj = { "key": "Other Cancer Types", "value": othertotal }
    // console.log(other_obj)
    // //add other object to the top 15 array of objects
    // top15.push(other_obj)
    // //set the x axis and y axis based on top 15 array then actually adding the x axis
    // x.domain(top15.map(function (d) { return d.key }))
    // y.domain([0, d3.max(top15, function (d) { return (d.value / mytotal) })]);
    // g.append("g")
    //     .attr("class", "axis axis--x")
    //     .attr("transform", "translate(0," + height + ")")
    //     .call(d3.axisBottom(x));

    // g.selectAll("text")
    //     .attr("transform", "rotate(90)")
    //     .attr("x", 10)
    //     .attr("y", 0)
    //     .attr("dy", ".35em")
    //     .style("text-anchor", "start");
    // //formatting and setting up of Y axis and adding title
    // let formatPercent = d3.format(".0%");

    // g.append("g")
    //     .attr("class", "axis axis--y")
    //     .call(d3.axisLeft(y).ticks(10))

    // g.append("text")
    //     .attr('x', 100)
    //     .attr('y', -1)
    //     .attr("font-family", "sans-serif")
    //     .attr("font-size", "16px")
    //     .attr("font-weight", "bold")
    //     .text('Distribution of Cancer Types: Top 15 Types + Aggregated Other');

    // //adding the bar chart and mouseover
    // g.selectAll(".bar")
    //     .data(top15)
    //     .enter().append("rect")
    //     .attr("class", "bar")
    //     .attr("x", function (d) { return x(d.key); })
    //     .attr("y", function (d) { return y((d.value) / mytotal); })
    //     .attr("width", x.bandwidth())
    //     .attr("height", function (d) { return height - y(d.value / mytotal); })
    //     .on("mousemove", function (d) {
    //         tooltip
    //             .style("left", d3.event.pageX - 50 + "px")
    //             .style("top", d3.event.pageY - 70 + "px")
    //             .style("display", "inline-block")
    //             .html((d.key) + "<br>" + ((d.value / mytotal) * 100).toFixed(2) + "%" + " | " + (d.value));
    //     })
    //     .on("mouseout", function (d) { tooltip.style("display", "none"); });
}
