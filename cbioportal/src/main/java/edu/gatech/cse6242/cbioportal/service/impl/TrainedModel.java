package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import edu.gatech.cse6242.cbioportal.util.DatasetUtil;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.tools.weka.WekaClassifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import weka.classifiers.trees.RandomForest;

import java.io.IOException;

@Service
public class TrainedModel {

    @Cacheable("trainedRF")
    public Classifier getTrainedRandomForest() throws IOException {
        CustomDataset dataset = DatasetUtil.loadCnaData();

        RandomForest rf = new RandomForest();
        rf.setMaxDepth(100);
        rf.setNumTrees(50);
        System.out.println("Max Depth: " + rf.getMaxDepth() +
                " Num Features: " + rf.getNumFeatures() +
                " Num Trees: " + rf.getNumTrees());
        Classifier cls = new WekaClassifier(rf);
        cls.buildClassifier(dataset);
        return cls;
    }
}
