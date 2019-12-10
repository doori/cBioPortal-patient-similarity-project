package edu.gatech.cse6242.cbioportal.service;

import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.model.PatientDTO;

import java.io.IOException;
import java.util.List;

/*
Interface for Similarity Metrics Service
 */
public interface SimilarityService {

    List<PatientDTO> jaccard(String patientId, int limit) throws IOException;

    Patient getPatientDetails(String patientId);

    List<Patient> getAllPatients();
}
