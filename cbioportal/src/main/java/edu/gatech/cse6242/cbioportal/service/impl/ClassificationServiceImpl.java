package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.service.ClassificationService;
import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.tools.weka.WekaClassifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.RandomForest;

@Service
public class ClassificationServiceImpl implements ClassificationService {

    @Cacheable("trainedRF")
    public Classifier getTrainedRandomForest(CustomDataset dataset) {
        // Default:: Max Depth: 0 Num Features: 0 Num Trees: 10
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

    @Cacheable("trainedMLP")
    public Classifier getTrainedMultiLayerPerceptron(CustomDataset dataset) throws Exception {
        // Default Hidden Layers: a Decay: false Learning Rate: 0.3 Training Time: 500
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        mlp.setDecay(true);
        mlp.setTrainingTime(10);
        System.out.println("Hidden Layers: " + mlp.getHiddenLayers() +
                " Decay: " + mlp.getDecay() +
                " Learning Rate: " + mlp.getLearningRate() +
                " Training Time: " + mlp.getTrainingTime());
        Classifier cls = new WekaClassifier(mlp);
        cls.buildClassifier(dataset);
        return cls;
    }
}
