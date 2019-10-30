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

    /*
    ~3min ##Default HP
    Max Depth: 0 Num Features: 0 Num Trees: 10
    Head and Neck Cancer
    Correct predictions  923
    Wrong predictions 9412

    ~6min
    Max Depth: 0 Num Features: 0 Num Trees: 50
    Head and Neck Cancer
    Correct predictions  923
    Wrong predictions 9412

    Head and Neck Cancer: 0.4803063702596676
    Breast Cancer: 0.4215355875210164

    ~6s
    Max Depth: 10 Num Features: 0 Num Trees: 10
    Non-Small Cell Lung Cancer
    Correct predictions  1009
    Wrong predictions 9326

    Non-Small Cell Lung Cancer: 0.17022478347541695
    Breast Cancer: 0.08682329655602736

    ~30s
    Max Depth: 10 Num Features: 0 Num Trees: 50
    Non-Small Cell Lung Cancer
    Correct predictions  1028
    Wrong predictions 9307

    Non-Small Cell Lung Cancer: 0.15577614107765597
    Breast Cancer: 0.09896664262683103

    ~1.5min
    Max Depth: 50 Num Features: 0 Num Trees: 50
    Non-Small Cell Lung Cancer
    Correct predictions  893
    Wrong predictions 9442

    Non-Small Cell Lung Cancer: 0.13843426904297731
    Breast Cancer: 0.09984765766073261

    ~2min
    Max Depth: 100 Num Features: 0 Num Trees: 50
    Head and Neck Cancer
    Correct predictions  892
    Wrong predictions 9443

    Head and Neck Cancer: 0.21607369945917912
    Breast Cancer: 0.09761646633382923
     */
}