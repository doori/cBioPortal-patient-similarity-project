package edu.gatech.cse6242.cbioportal.service;

import edu.gatech.cse6242.cbioportal.model.CancerTypeDTO;

import java.io.IOException;

public interface CancerTypeService {

    CancerTypeDTO predictCancerTypeRF(String patientId) throws IOException;
}
