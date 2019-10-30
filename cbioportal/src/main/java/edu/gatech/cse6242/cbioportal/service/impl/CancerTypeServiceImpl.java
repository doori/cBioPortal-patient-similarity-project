package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.CancerTypeDTO;
import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.persistence.PatientRepository;
import edu.gatech.cse6242.cbioportal.service.CancerTypeService;
import edu.gatech.cse6242.cbioportal.service.ClassificationService;
import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import edu.gatech.cse6242.cbioportal.util.DatasetUtil;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CancerTypeServiceImpl implements CancerTypeService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ClassificationService classificationService;

    @Override
    public CancerTypeDTO predictCancerTypeRF(String patientId) throws IOException {
        // load data
        CustomDataset cnaData = DatasetUtil.loadCnaData();

        Classifier cls = classificationService.getTrainedRandomForest(cnaData);
        return predictCancerType(patientId, cls, cnaData);
    }

    @Override
    public CancerTypeDTO predictCancerTypeMLP(String patientId) throws Exception {
        // load data
        CustomDataset cnaData = DatasetUtil.loadCnaData();

        Classifier cls = classificationService.getTrainedMultiLayerPerceptron(cnaData);
        return predictCancerType(patientId, cls, cnaData);
    }

    private CancerTypeDTO predictCancerType(String patientId, Classifier cls, CustomDataset cnaData) throws IOException {
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
        for (Instance ex : cnaData) {
            Object predictedClassValue = cls.classify(ex);
            Object realClassValue = inst.classValue();
            if (realClassValue.equals(predictedClassValue))
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
