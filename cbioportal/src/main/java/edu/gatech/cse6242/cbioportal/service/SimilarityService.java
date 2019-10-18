package edu.gatech.cse6242.cbioportal.service;

import edu.gatech.cse6242.cbioportal.model.Patient;

import java.io.IOException;
import java.util.List;

/*
Interface for Similarity Metrics Service
 */
public interface SimilarityService {

    public List<Patient> jaccardIndex(String patientId, int limit) throws IOException;

}
