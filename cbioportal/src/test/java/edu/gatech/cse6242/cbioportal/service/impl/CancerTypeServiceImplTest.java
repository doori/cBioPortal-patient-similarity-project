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
    public void testPredictCancerTypeRF() throws IOException {
        CancerTypeDTO dto = cancerTypeService.predictCancerTypeRF("P-0000004");

        Assert.assertTrue(!dto.getCancerType().isEmpty());
    }

    /*
    Max Depth: 100 Num Features: 0 Num Trees: 10
w/o Unknown
    Correct predictions  4659
    Wrong predictions 5496
w/o All zeros
    Correct predictions  3704
    Wrong predictions 1083
Top 15 cancers (for pred & true labels)
    Breast Cancer
    Correct predictions  2658
    Wrong predictions 475
     */

    @Test
    public void testPredictCancerTypeKNN() throws Exception {
        CancerTypeDTO dto = cancerTypeService.predictCancerTypeKNN("P-0000004");

        Assert.assertTrue(!dto.getCancerType().isEmpty());
    }
    /* k=10 Jaccard
    Correct predictions  1913
    Wrong predictions 8422
w/o All zeros
    Correct predictions  1672
    Wrong predictions 3115
Top 15 cancers (for pred & true labels)
    Non-Small Cell Lung Cancer
    Correct predictions  1481
    Wrong predictions 1403

     */
}