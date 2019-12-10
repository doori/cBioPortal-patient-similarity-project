package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.PatientDTO;
import edu.gatech.cse6242.cbioportal.persistence.SampleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimilarityServiceImplTest {

    @Autowired
    private SimilarityServiceImpl similarityService;

    @Autowired
    private SampleRepository sampleRepository;

    @Test
    public void testJaccard() throws IOException {
        // Jaccard may return less patients than the limit.
        // with exact match (Ai == Bi), 94 returned when queried for 100
        // with any alterations (Ai != 0) && (Bi != 0), 141 returned when queried for 200
        int limit = 5;
        List<PatientDTO> patients = similarityService.jaccard("P-0000273", limit);

        for (PatientDTO p: patients) {
            System.out.println(p.getPatientId() + " " + p.getCancerType() + " " + p.getSimilarity());
        }

        Assert.assertEquals(limit, patients.size());
    }
}