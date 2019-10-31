package edu.gatech.cse6242.cbioportal.service.impl;

import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import edu.gatech.cse6242.cbioportal.util.DatasetUtil;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.tools.weka.WekaClassifier;
import net.sf.javaml.utils.MathUtils;
import weka.classifiers.trees.RandomForest;

import java.io.IOException;
import java.util.Map;

/*
Model Evaluation with Cross Validation and save results to file
 */
public class ModelEvaluation {

    public static void main(String[] args) throws IOException{
        // RandomForest model evaluation
        CustomDataset dataset = DatasetUtil.loadCnaDataWithoutUnknownTypes();
        RandomForest rf = new RandomForest();
        rf.setMaxDepth(100);
        rf.setNumTrees(50);
        System.out.println("Max Depth: " + rf.getMaxDepth() +
                " Num Features: " + rf.getNumFeatures() +
                " Num Trees: " + rf.getNumTrees());
        Classifier cls = new WekaClassifier(rf);
        cls.buildClassifier(dataset);

        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(cls, dataset);

        int numClasses = pm.size();
        double[] accuracy = new double[numClasses];
        double[] errorRate = new double[numClasses];
        double[] fMeasure = new double[numClasses];

        int i = 0;
        for (Object o : pm.keySet()) {
            PerformanceMeasure perf = pm.get(o);
            accuracy[i] = perf.getAccuracy();
            errorRate[i] = perf.getErrorRate();
            fMeasure[i] = perf.getFMeasure();
            i++;
        }
        System.out.println("Average Training Accuracy: " + MathUtils.arithmicMean(accuracy) +
                " Error Rate: " + MathUtils.arithmicMean(errorRate) +
                " F Measure: "  + MathUtils.arithmicMean(fMeasure));
    }
    /*
    Max Depth: 100 Num Features: 0 Num Trees: 50

    **With Unknown Cancer Types:
    Average Accuracy: 0.9819383970327364 Error Rate: 0.01806160296726334 F Measure: 0.34641085010558226

    **Without Unknown Cancer Types:
    Average Training Accuracy: 0.9817272947283211 Error Rate: 0.0182727052716788 F Measure: 0.3372729020748604
     */
}
