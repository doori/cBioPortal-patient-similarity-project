function doPopulateCancerNavigators(patientList) {
    alert('still pending!');

    let panelBody = $("#patient-similarity-cancer-navigator-content");
    panelBody.empty();

    const cancerTypes = [...new Set(patientList.map(p => p.cancerType))].sort();

    $.each(cancerTypes, (i, cancerType) => {

        let div = $('<div>')
            .append(
                '<button type="button" class="btn btn-toggle" data-toggle="button"' +
                '    aria-pressed="false" autocomplete="off" onclick="doToggleClick(\'' + cancerType + '\')">' +
                '    <div class="handle"></div>' +
                '</button>' +
                '<div class="btn-toogle-label">' + cancerType + '</div>'
            )
            .appendTo(panelBody);
    })
}

function doToggleClick(cancerType) {
    alert('...Trigger Event Pending... \nElement to Select on Graph: ' + cancerType)
}