package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.CancerTypeDTO;
import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.persistence.PatientRepository;
import edu.gatech.cse6242.cbioportal.service.CancerTypeService;
import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import edu.gatech.cse6242.cbioportal.util.DatasetUtil;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.core.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CancerTypeServiceImpl implements CancerTypeService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public CancerTypeDTO predictCancerTypeRF(String patientId) throws IOException {
        // TODO hyper-parameter tuning
        RandomForest rf = new RandomForest(10, false, 100, new Random());
        return predictCancerType(patientId, rf);
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

        Map<Object, Double> classDistribution = cls.classDistribution(inst);

        // Keep classes with distribution > 0.0
        Map<Object, Double> nonZeroClasses = new HashMap<>();
        for (Object obj : classDistribution.keySet()) {
            if (classDistribution.get(obj).compareTo(0.0) > 0) {
                nonZeroClasses.put(obj, classDistribution.get(obj));
            }
        }

        CancerTypeDTO dto = new CancerTypeDTO();
        dto.setPatientId(patientId);
        dto.setCancerType(prediction);
        dto.setClassDistribution(nonZeroClasses);

        return dto;
    }
}
