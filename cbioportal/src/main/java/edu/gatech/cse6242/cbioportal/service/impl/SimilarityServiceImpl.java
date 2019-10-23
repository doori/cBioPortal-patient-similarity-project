package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.persistence.PatientRepository;
import edu.gatech.cse6242.cbioportal.service.SimilarityService;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.tools.data.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.javaml.core.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
Implementation of Similarity Metrics.
 */
@Service
public class SimilarityServiceImpl implements SimilarityService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<Patient> jaccardIndex(String patientId, int limit) throws IOException {
        JaccardSimilarity jaccard = new JaccardSimilarity();
        return getSimilarPatients(patientId, limit, jaccard);
    }

    private List<Patient> getSimilarPatients(String patientId, int limit, DistanceMeasure dm) throws IOException {

        File file = new File("data/msk_processed.tsv");
        Dataset cnaData = FileHandler.loadDataset(file, 0, "\t");

        // remove header
        cnaData.remove(0);
        Patient patient = patientRepository.findByPatientId(patientId);
        int id = patient.getId().intValue();

        Instance inst = cnaData.instance(id);
        // TODO extend kNearest to return Map of Instance, similarity double?
        Set<Instance> similarPatients = cnaData.kNearest(limit, inst, dm);

        return convertToPatientList(similarPatients, cnaData);
    }

    /*
    Retrieves indices of instances from the dataset and queries patients based on the indices.
     */
    // TODO list of PatientDTO
    private List<Patient> convertToPatientList(Set<Instance> instances, Dataset ds) {
        List<Long> ids = new ArrayList<>();
        for (Instance inst : instances) {
            int idx = ds.indexOf(inst);
            ids.add(Long.valueOf(idx));
        }

        return patientRepository.findAllById(ids);
    }


}
