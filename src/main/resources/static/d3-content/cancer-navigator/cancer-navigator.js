const doPopulateCancerNavigators = (patientList) => {

    let panelBody = $("#patient-similarity-cancer-navigator-content");
    panelBody.empty();

    const cancerTypes = [...new Set(patientList.map(p => p.cancerType))].sort();

    $.each(cancerTypes, (i, cancerType) => {

        let div = $('<div>')
            .append(
                '<button type="button" class="btn btn-toggle" ' +
                '    data-toggle="button"' +
                '    aria-pressed="false" autocomplete="off" ' +
                '    data-placement="left"' +
                '    onclick="doToggleClick(this, \'' + cancerType + '\', ' + i + ')">' +
                '    <div class="handle"></div>' +
                '</button>' +
                '<div class="btn-toogle-label">' + cancerType + '</div>'
            )
            .appendTo(panelBody);
    })
}

const doToggleClick = (e, cancerType, optionIndex) => {
    let randomColor = getRandomColor();
    let isActivating = !$(e).hasClass("active");

    isActivating ? $(e).css("background-color", randomColor) : $(e).css("background-color", "#bdc1c8")

    d3.selectAll('.node').dispatch('customSelect',
        {
            detail:
            {
                cancerType: cancerType,
                optionIndex: optionIndex,
                active: isActivating,
                fillColor: randomColor
            }
        });
}

const padZero = (str, len) => {
    len = len || 2;
    var zeros = new Array(len).join('0');
    return (zeros + str).slice(-len);
}

const getRandomColor = () => {
    var color = Math.round(Math.random() * 0x1000000).toString(16);
    return "#" + padZero(color, 6);
}