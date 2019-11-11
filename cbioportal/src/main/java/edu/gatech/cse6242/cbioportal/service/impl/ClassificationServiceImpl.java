package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.service.ClassificationService;
import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.tools.weka.WekaClassifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import weka.classifiers.trees.RandomForest;

@Service
public class ClassificationServiceImpl implements ClassificationService {

    @Cacheable("trainedRF")
    public Classifier getTrainedRandomForest(CustomDataset dataset) {
        // Default:: Max Depth: 0 Num Features: 0 Num Trees: 10
        RandomForest rf = new RandomForest();
        rf.setMaxDepth(100);
        rf.setNumTrees(10);
        System.out.println("Max Depth: " + rf.getMaxDepth() +
                " Num Features: " + rf.getNumFeatures() +
                " Num Trees: " + rf.getNumTrees());
        Classifier cls = new WekaClassifier(rf);
        cls.buildClassifier(dataset);
        return cls;
    }

    @Cacheable("trainedKNN")
    public Classifier getTrainedKNearestNeighbors(CustomDataset dataset) throws Exception {

        KNearestNeighbors cls = new KNearestNeighbors(10, new JaccardExpSimilarity());
        cls.buildClassifier(dataset);
        return cls;
    }
}
