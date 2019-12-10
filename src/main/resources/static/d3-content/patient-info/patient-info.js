const getPatientInfo = async (patientId) => {
    let client = new Api();
    return await client.GetPatientInfo(patientId);
}

const doPopulatePatientInfo = (patientInfo) => {
    let vitalStatus = patientInfo.vitalStatus == 'ALIVE' ? '<span style="color:#8bb92f;"><b>ALIVE</b></span>' : '<span style="color:#ee0303;"><b>DECEASED</b></span>';

    $("#patient-info").html(
        "<b>Patient ID:</b> " + patientInfo.patientId + " | " + 
        "<b>Sex:</b> " + patientInfo.sex + " | " + 
        "<b>Vital Status:</b> " + vitalStatus + " | " + 
        "<b>Cancer Type:</b> " + patientInfo.samples[0].cancerType + " | " + 
        "<b>Cancer Type Detailed:</b> " + patientInfo.samples[0].cancerTypeDetailed
        )
}