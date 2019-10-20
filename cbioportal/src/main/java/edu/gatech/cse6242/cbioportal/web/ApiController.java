package edu.gatech.cse6242.cbioportal.web;

import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.service.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;

@RequestMapping("api")
@RestController
public class ApiController {

    @Autowired
    SimilarityService similarityService;

    @GetMapping("helloworld")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = similarityService.getPatientDetails(
                Arrays.asList(0L, 1L, 2L));

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}