// import CoreClient from './CoreClient';

class Api {
    constructor() {
        this.apiPath = 'http://localhost:8080/api';

        this.client = new CoreClient({ url: this.apiPath });
        this.client.createEntities(
            [
                { name: 'patient' },
                { name: 'patients' },
                { name: 'similar-patients' },
            ]);
    }

    GetPatientInfo(patientId) {
        return this.client.endpoints.patient.getAll({ id: patientId });
    }

    GetSimilarPatients(patientId, count) {
        return this.client.endpoints.similarPatients.getAll({ id: patientId, limit: count });
    }

    GetAllPatients() {
        return this.client.endpoints.patients.getAll();
    }
}
