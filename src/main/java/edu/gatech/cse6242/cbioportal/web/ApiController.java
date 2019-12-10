package edu.gatech.cse6242.cbioportal.web;

import edu.gatech.cse6242.cbioportal.model.CancerTypeDTO;
import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.model.PatientDTO;
import edu.gatech.cse6242.cbioportal.service.CancerTypeService;
import edu.gatech.cse6242.cbioportal.service.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("api")
@RestController
public class ApiController {

    @Autowired
    SimilarityService similarityService;

    @Autowired
    CancerTypeService cancerTypeService;

    @GetMapping("patients")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> allPatients = similarityService.getAllPatients();
        return new ResponseEntity<>(allPatients, HttpStatus.OK);
    }

    @GetMapping("similar-patients")
    public ResponseEntity<List<PatientDTO>> getSimilarPatients(
            @RequestParam String id,
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "jaccard") String similarity) throws IOException {

        List<PatientDTO> patients = null;
        if ("jaccard".equalsIgnoreCase(similarity)) {
            patients = similarityService.jaccard(id, limit);
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("patient")
    public ResponseEntity<Patient> getPatientDetails(
            @RequestParam String id) {
        Patient patient = similarityService.getPatientDetails(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @GetMapping("cancer-type-prediction")
    public ResponseEntity<CancerTypeDTO> getCancerTypePrediction(
            @RequestParam String id,
            @RequestParam(defaultValue = "rf") String classifier) throws Exception {

        CancerTypeDTO cancerTypeDTO = null;
        if ("rf".equalsIgnoreCase(classifier)) {
            cancerTypeDTO = cancerTypeService.predictCancerTypeRF(id);
        }
        if ("knn".equalsIgnoreCase(classifier)) {
            cancerTypeDTO = cancerTypeService.predictCancerTypeKNN(id);
        }
        return new ResponseEntity<>(cancerTypeDTO, HttpStatus.OK);
    }
}