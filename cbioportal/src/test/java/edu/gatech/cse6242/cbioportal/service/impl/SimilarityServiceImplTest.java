package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.Patient;
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

    @Test
    public void jaccardIndex() throws IOException {
        List<Patient> patients = similarityService.jaccardIndex("P-0000004-T01-IM3", 10);

    }
}