function doPopulatePatientsTable(patientList) {
    let tableElem = $('#patients-table').DataTable();
    tableElem.clear();

    $.each(patientList, (i, patient) => {
        tableElem.row.add([
            patient.patientId,
            patient.similarity.toFixed(4),
            patient.cancerType,
            patient.sex,
            patient.vitalStatus,
            patient.smokingHistory,
            patient.osMonths,
            patient.osStatus,
            patient.sampleCollectionSource,
            patient.specimenPreservationType,
            patient.specimenType,
            patient.dnaInput,
            patient.sampleCoverage,
            patient.tumorPurity,
            patient.matchedStatus,
            patient.sampleType,
            patient.primarySite,
            patient.metastaticSite,
            patient.sampleClass,
            patient.oncotreeCode,
            patient.cancerTypeDetailed,
        ]);
    })

    tableElem.draw();
}