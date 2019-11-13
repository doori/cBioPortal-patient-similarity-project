//TODO: implement sorting
function doPopulatePatientsTable(patientList, sortByField = "patientId", sortByAscending = true) {
    let tableBody = $("#patients-table-body");
    tableBody.empty();

    $.each(patientList, (i, patient) => {
        let tr = $('<tr>').append(
            $('<th scope="row" class="headcol">').text(patient.patientId),
            $('<td>').text(patient.similarity.toFixed(4)),
            $('<td>').text(patient.cancerType),
            $('<td>').text(patient.sex),
            $('<td>').text(patient.vitalStatus),
            $('<td>').text(patient.smokingHistory),
            $('<td>').text(patient.osMonths),
            $('<td>').text(patient.osStatus),
            $('<td>').text(patient.sampleCollectionSource),
            $('<td>').text(patient.specimenPreservationType),
            $('<td>').text(patient.specimenType),
            $('<td>').text(patient.dnaInput),
            $('<td>').text(patient.sampleCoverage),
            $('<td>').text(patient.tumorPurity),
            $('<td>').text(patient.matchedStatus),
            $('<td>').text(patient.sampleType),
            $('<td>').text(patient.primarySite),
            $('<td>').text(patient.metastaticSite),
            $('<td>').text(patient.sampleClass),
            $('<td>').text(patient.oncotreeCode),
            $('<td>').text(patient.cancerTypeDetailed),
        )
            .appendTo(tableBody);
    })
}