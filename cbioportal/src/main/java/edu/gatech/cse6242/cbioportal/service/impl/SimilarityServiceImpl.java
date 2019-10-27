package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.model.PatientDTO;
import edu.gatech.cse6242.cbioportal.model.Sample;
import edu.gatech.cse6242.cbioportal.persistence.PatientRepository;
import edu.gatech.cse6242.cbioportal.persistence.SampleRepository;
import edu.gatech.cse6242.cbioportal.service.SimilarityService;
import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.tools.data.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.javaml.core.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/*
Implementation of Similarity Metrics.
 */
@Service
public class SimilarityServiceImpl implements SimilarityService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SampleRepository sampleRepository;

    @Override
    public List<PatientDTO> jaccard(String patientId, int limit) throws IOException {
        JaccardSimilarity jaccard = new JaccardSimilarity();
        return getSimilarPatients(patientId, limit, jaccard);
    }

    @Override
    public Patient getPatientDetails(String patientId) {
        return patientRepository.findByPatientId(patientId);
    }

    private List<PatientDTO> getSimilarPatients(String patientId, int limit, DistanceMeasure dm) throws IOException {

        File file = new File("data/msk_processed.tsv");
        Dataset data = FileHandler.loadDataset(file, 0, "\t");
        CustomDataset cnaData = new CustomDataset(data);

        // remove header
        cnaData.remove(0);
        Patient patient = patientRepository.findByPatientId(patientId);
        int id = patient.getId().intValue();

        Instance inst = cnaData.instance(id);
        Map<Integer, Double> similarPatients = cnaData.kNearestMap(limit, inst, dm);

        return convertToPatientDTO(similarPatients);
    }

    private List<PatientDTO> convertToPatientDTO(Map<Integer, Double> similarPatients) {

        List<Long> ids = similarPatients.keySet().stream()
                .map(Integer::toUnsignedLong)
                .collect(Collectors.toList());

        List<Patient> patients = patientRepository.findAllById(ids);

        List<PatientDTO> patientDTOs = new ArrayList<>();
        for (Patient patient : patients) {
            // populate PatientDTO
            PatientDTO dto = new PatientDTO();
            dto.setPatientId(patient.getPatientId());
            dto.setSimilarity(similarPatients.get(patient.getId().intValue()));
            dto.setSex(patient.getSex());
            dto.setVitalStatus(patient.getVitalStatus());
            dto.setOsMonths(patient.getOsMonths());
            dto.setOsStatus(patient.getOsStatus());
            dto.setSmokingHistory(patient.getSmokingHistory());

            List<Sample> samples = patient.getSamples();
            if (samples.size() > 0) {
                Sample sample = samples.get(0);
                dto.setCancerType(sample.getCancerType());
                dto.setCancerTypeDetailed(sample.getCancerTypeDetailed());
                dto.setDnaInput(sample.getDnaInput());
                dto.setMatchedStatus(sample.getMatchedStatus());
                dto.setMetastaticSite(sample.getMetastaticSite());
                dto.setOncotreeCode(sample.getOncotreeCode());
                dto.setPrimarySite(sample.getPrimarySite());
                dto.setSampleClass(sample.getSampleClass());
                dto.setSampleCollectionSource(sample.getSampleCollectionSource());
                dto.setSampleCoverage(sample.getSampleCoverage());
                dto.setSampleType(sample.getSampleType());
                dto.setSpecimenPreservationType(sample.getSpecimenPreservationType());
                dto.setSpecimenType(sample.getSpecimenType());
                dto.setTumorPurity(sample.getTumorPurity());
            }
            patientDTOs.add(dto);
        }
        return patientDTOs;
    }

}
