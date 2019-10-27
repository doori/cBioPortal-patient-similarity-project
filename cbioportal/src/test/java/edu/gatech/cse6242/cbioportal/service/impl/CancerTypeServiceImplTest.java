package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.CancerTypeDTO;
import edu.gatech.cse6242.cbioportal.service.CancerTypeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CancerTypeServiceImplTest {

    @Autowired
    private CancerTypeService cancerTypeService;

    @Test
    public void predictCancerType() throws IOException {
        CancerTypeDTO dto = cancerTypeService.predictCancerTypeRF("P-0000004");

        Assert.assertTrue(!dto.getCancerType().isEmpty());
    }
}