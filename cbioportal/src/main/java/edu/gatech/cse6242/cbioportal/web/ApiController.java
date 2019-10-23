package edu.gatech.cse6242.cbioportal.web;

import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.service.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@RequestMapping("api")
@RestController
public class ApiController {

    @Autowired
    SimilarityService similarityService;

    @GetMapping("helloworld")
    public ResponseEntity<List<Patient>> getPatients() {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("similar-patients")
    public ResponseEntity<List<Patient>> getSimilarPatients(
            @RequestParam String patientId,
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "jaccard") String similarity) throws IOException {

        List<Patient> patients = null;
        if ("jaccard".equalsIgnoreCase(similarity)) {
            patients = similarityService.jaccardIndex(patientId, limit);
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}