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
    public void testEvaluateKnnModel() throws Exception {
        cancerTypeService.evaluateKnnModel();
    }
    /*
    Jaccard 0.34667

    (Small punishments for !match)
    JaccardAdj(0.1) 0.305
   *JaccardAdj(0.2) 0.345
    JaccardAdj(0.4) 0.31275
    JaccardAdj(0.6) 0.3275
    JaccardAdj(0.6) 0.31667

   *JaccardExpp(n^2/d) 0.345
    JaccardExp(n^2/d^2) 0.341667

    (ExactMatch, SomeAlteration, NoAlteration)
    JaccardExtended(3,2,-1) 0.32
    JaccardExtended(3,1,-1) 0.3225
    JaccardExtended(5,1,-1) 0.2833
    JaccardExtended(10,1,-1) 0.1975

    SKLEARN k=10
    auto      0.415
    ball_tree 0.3925
    kd_tree   0.4125
   *brute     0.4242
    */

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