package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.model.Sample;
import edu.gatech.cse6242.cbioportal.persistence.SampleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimilarityServiceImplTest {

    @Autowired
    private SimilarityServiceImpl similarityService;

    @Autowired
    private SampleRepository sampleRepository;

    @Test
    public void jaccardIndex() throws IOException {
        // with exact match, 94 returned when queried for 100
        // with any alterations, 141 returned when queried for 200
        List<Patient> patients = similarityService.jaccardIndex("P-0000004", 200);

        List<String> patientIds = new ArrayList<>();
        for (Patient p: patients) {
            patientIds.add(p.getPatientId());
        }

        List<Sample> samples = sampleRepository.findAllByPatientId(patientIds);
        for (Sample s: samples) {
            System.out.println(s.getPatientId() + " " + s.getCancerType());
        }

        Assert.assertEquals(10, patients.size());
    }
}