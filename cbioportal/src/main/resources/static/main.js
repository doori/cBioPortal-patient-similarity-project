    const DEFAULT_MAX_PATIENT_COUNT = 35;
    const DEFAULT_MAX_GRAPH_DEPTH = 0;
    const MAX_SUGGESTED_NODES_TOTAL = 200;
    let showWarning = true;
    
    //to be used only by the node fill event ->cancer-navigator.js: node.on("customSelect", ()=>{...})
    //TODO: Refactor into a global store class or add functionality where node's cancerType can be accesed 
    //by anonymous functions. Currently there is no fast workaround for this. When creating the nodes, 
    // there is no way to know the source patient cancer type, only targetPatient's type
    let g_PatientList = []; 


    $(document).ready(() => {
        $('#patients-table').DataTable();
        $('[data-toggle="tooltip"]').tooltip({ trigger: "hover", delay: { show: 700, hide: 100 } })
    });

    $("form").submit((e) => { e.preventDefault(); });

    $("#patients-count, #patients-graph-depth").on("keyup", () => {
        let patientsCountVal = $("#patients-count").val() == "" ? DEFAULT_MAX_PATIENT_COUNT : $("#patients-count").val();
        let patientsGraphDepthVal = $("#patients-graph-depth").val() == "" ? DEFAULT_MAX_GRAPH_DEPTH : $("#patients-graph-depth").val();

        if (patientsCountVal > MAX_SUGGESTED_NODES_TOTAL || Math.pow(patientsCountVal, patientsGraphDepthVal) > MAX_SUGGESTED_NODES_TOTAL) {
            $("#patient-count-warning").show();
        } else {
            $("#patient-count-warning").hide();
        }
    });

    const hideWarning = () => { showWarning = false; };

    getPatientListInformation().then((list) => {
        $("#spinner-group").hide();

        $('#search-field').autocomplete({
            nameProperty: 'value',
            valueProperty: 'patientId',
            valueField: '#search-hidden-field',
            dataSource: list
        });

        $('#btn-do-search').show();
        $('#search-field').show();

        $("#btn-do-search").click(() => {
            let patientId = $("#search-hidden-field").val();
            let patientCount = $("#patients-count").val();
            let graphDepth = $("#patients-graph-depth").val();

            if (patientId == "") { return; }

            $("#patient-similarity-node-graph").empty();
            $("#patient-similarity-cancer-distribution").empty();

            $("#spinner-group").show();

            const patientGraphContent = getPatientsSimilarityAggregatedGraphContent(patientId, patientCount == "" ? null : patientCount, graphDepth == "" ? null : graphDepth);

            const patientCancerDistributionGraph = getCancerDistributionGraph(patientId);

            const patientInfo = getPatientInfo(patientId);

            Promise.all([patientGraphContent, patientCancerDistributionGraph, patientInfo]).then((results) => {
                let similarityLinks = results[0][0];
                let patientList = results[0][1];
                let uniquePatientList = getUniquePatientList(patientList);
                let selectedPatientInfo = results[2].data;
                g_PatientList = [...patientList];
                
                $("#spinner-group").hide();
                doDrawNodeGraph(patientId, similarityLinks);
                doPopulatePatientsTable(uniquePatientList);
                doDrawCancerDistributionGraph(patientList);
                doPopulateCancerNavigators(uniquePatientList);
                doPopulatePatientInfo(selectedPatientInfo)
            });

            if (patientCount == "") { $("#patients-count").val(DEFAULT_MAX_PATIENT_COUNT); }
            if (graphDepth == "") { $("#patients-graph-depth").val(DEFAULT_MAX_GRAPH_DEPTH); }
        });
    });

    function getUniquePatientList(patientList) {
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