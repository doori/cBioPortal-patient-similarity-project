package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.CancerTypeDTO;
import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.persistence.PatientRepository;
import edu.gatech.cse6242.cbioportal.service.CancerTypeService;
import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import edu.gatech.cse6242.cbioportal.util.DatasetUtil;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.weka.WekaClassifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.trees.RandomForest;

import java.io.IOException;
import java.util.*;

@Service
public class CancerTypeServiceImpl implements CancerTypeService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public CancerTypeDTO predictCancerTypeRF(String patientId) throws IOException {

        RandomForest rf = new RandomForest();
        rf.setMaxDepth(100);
        rf.setNumTrees(50);
        System.out.println("Max Depth: " + rf.getMaxDepth() +
                " Num Features: " + rf.getNumFeatures() +
                " Num Trees: " + rf.getNumTrees());
        Classifier rfc = new WekaClassifier(rf);
        return predictCancerType(patientId, rfc);
    }

    private CancerTypeDTO predictCancerType(String patientId, Classifier cls) throws IOException {
        // load data
        CustomDataset cnaData = DatasetUtil.loadCnaData();

        // train
        cls.buildClassifier(cnaData);

        // classify
        Patient patient = patientRepository.findByPatientId(patientId);
        Instance inst = cnaData.get(patient.getId().intValue());
        String prediction = cls.classify(inst).toString();
        System.out.println(prediction);

        // Keep classes with distribution > 0.0
        Map<Object, Double> classDistribution = cls.classDistribution(inst);
        Map<Object, Double> nonZeroClasses = new HashMap<>();
        for (Object obj : classDistribution.keySet()) {
            if (classDistribution.get(obj).compareTo(0.0) > 0) {
                nonZeroClasses.put(obj, classDistribution.get(obj));
            }
        }

        // Test
        int correct = 0, wrong = 0;
        /* Classify all instances and check with the correct class values */
        for (Instance ex : cnaData) {
            Object predictedClassValue = cls.classify(ex);
            Object realClassValue = inst.classValue();
            if (predictedClassValue.equals(realClassValue))
                correct++;
            else
                wrong++;
        }
        System.out.println("Correct predictions  " + correct);
        System.out.println("Wrong predictions " + wrong);

        CancerTypeDTO dto = new CancerTypeDTO();
        dto.setPatientId(patientId);
        dto.setCancerType(prediction);
        dto.setClassDistribution(nonZeroClasses);

        return dto;
    }
}
