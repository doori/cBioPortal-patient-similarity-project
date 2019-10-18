package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.model.Patient;
import edu.gatech.cse6242.cbioportal.service.SimilarityService;
import net.sf.javaml.distance.JaccardIndexSimilarity;
import net.sf.javaml.tools.data.FileHandler;
import org.springframework.stereotype.Service;
import net.sf.javaml.core.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
Implementation of Similarity Metrics.
 */
@Service
public class SimilarityServiceImpl implements SimilarityService {

    @Override
    public List<Patient> jaccardIndex(String patientId, int limit) throws IOException {
        File file = new File(
                getClass().getClassLoader().getResource("data/data_CNA.txt").getFile()
        );
        Dataset cnaData = FileHandler.loadDataset(file, 0, "\\t");
        System.out.println(cnaData.instance(0));
        System.out.println(cnaData.instance(1));

        Instance inst = cnaData.instance(1);
        JaccardIndexSimilarity jaccardIndex = new JaccardIndexSimilarity();
        Set<Instance> similarPatients = cnaData.kNearest(limit, inst, jaccardIndex);

        Iterator it = similarPatients.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        return null;
    }
}
