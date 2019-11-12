package edu.gatech.cse6242.cbioportal.util;

import edu.gatech.cse6242.cbioportal.service.impl.JaccardSimilarity;
import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import edu.gatech.cse6242.cbioportal.util.DatasetUtil;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.tools.weka.WekaClassifier;
import net.sf.javaml.utils.MathUtils;
import weka.classifiers.trees.RandomForest;

import java.io.IOException;
import java.util.Map;

/*
Hyper-parameter tuning utility for RF and KNN.
 */
public class ClassficationModelHyperTuning {

    private static Classifier getRFmodel() {
        RandomForest rf = new RandomForest();
        rf.setMaxDepth(100);
        rf.setNumFeatures(100);
        rf.setNumTrees(50);
        System.out.println("Max Depth: " + rf.getMaxDepth() +
                " Num Features: " + rf.getNumFeatures() +
                " Num Trees: " + rf.getNumTrees());
        Classifier cls = new WekaClassifier(rf);
        return cls;
    }

    private static KNearestNeighbors getKNNmodel() {
        int k = 25;
        System.out.println(k);
        KNearestNeighbors cls = new KNearestNeighbors(k, new JaccardSimilarity());
        return cls;
    }

    public static void main(String[] args) throws IOException{

        CustomDataset dataset = DatasetUtil.loadCnaDataWithoutZeros();
        // RandomForest model
        Classifier cls = getRFmodel();

        // KNN model
        //KNearestNeighbors cls = getKNNmodel();

        cls.buildClassifier(dataset);
        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(cls, dataset);

        int numClasses = pm.size();
        double[] accuracy = new double[numClasses];
        double[] errorRate = new double[numClasses];
        double[] fMeasure = new double[numClasses];
        double[] fnr = new double[numClasses];
        double[] fpr = new double[numClasses];
        int i = 0;
        for (Object o : pm.keySet()) {
            PerformanceMeasure perf = pm.get(o);
            accuracy[i] = perf.getAccuracy();
            errorRate[i] = perf.getErrorRate();
            fMeasure[i] = perf.getFMeasure();
            fnr[i] = perf.getFNRate();
            fpr[i] = perf.getFPRate();
            i++;
        }
        System.out.println("Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate " +
                MathUtils.arithmicMean(accuracy) + ", " +
                MathUtils.arithmicMean(errorRate) + ", " +
                MathUtils.arithmicMean(fMeasure) + ", " +
                MathUtils.arithmicMean(fnr) + ", " +
                MathUtils.arithmicMean(fpr));
    }
    /*
    RF###
**  Max Depth: 100 Num Features: 0 Num Trees: 10 (14 ~ (log2(10355)+1))
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.981844863731656, 0.018155136268343808, 0.3370549918146893, NaN, 0.010567554698167763
w/ clinical data
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9829414610546686, 0.017058538945331395, 0.37978081959574117, NaN, 0.009964785010558856

    Max Depth: 100 Num Features: 0 Num Trees: 50
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9819383970327364, 0.01806160296726334, 0.34641085010558226, NaN, 0.010548068403556583

    Max Depth: 100 Num Features: 100 Num Trees: 10
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9817352039993549, 0.018264796000645057, 0.3356221678217629, NaN, 0.010631012559310173

    Max Depth: 100 Num Features: 100 Num Trees: 50
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.981841638445412, 0.018158361554587974, 0.3428833652426275, NaN, 0.010572420976344688

    Max Depth: 50 Num Features: 50 Num Trees: 10
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9807805192710852, 0.019219480728914686, 0.2928046453719586, NaN, 0.011238039346002692

    Max Depth: 50 Num Features: 50 Num Trees: 50
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9809643605870019, 0.019035639412997896, 0.30667985401951847, NaN, 0.011134158975015997

    KNN### dm: JaccardSimilarity
    #k=3
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9716852120625703, 0.02831478793742944, 0.05415105539255187, NaN, 0.014784792963570072

    #k=5
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9722560877277857, 0.027743912272214153, 0.05696712135068837, NaN, 0.014539404212185088

**  #k=10
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9728721174004192, 0.02712788259958071, 0.06146930184145845, NaN, 0.014282876860788302
w/ clinical data
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9732107724560553, 0.026789227543944517, 0.06065014647477221, NaN, 0.014099902493609828

    #k=20
    Average Training Accuracy, Error Rate, F Measure, TPrate, FPrate
    0.9732010965973227, 0.026798903402676988, 0.05339633113397584, NaN, 0.01430606098124899

    #k=40
    Average Training Accuracy, Error Rate, F Measure, TPrate, FPrate
    0.9730430575713594, 0.026956942428640537, 0.04914621852129556, NaN, 0.014430903016393298

    #k=60
    Average Training Accuracy, Error Rate, F Measure, TPrate, FPrate
    0.9736236090953072, 0.026376390904692786, 0.040626845107043404, NaN, 0.014951752584144525
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9736236090953072, 0.026376390904692786, 0.040626845107043404, NaN, 0.014951752584144525

    #k=80
    Average Training Accuracy, Error Rate, F Measure, FNrate, FPrate
    0.9734107402031931, 0.02658925979680696, 0.0374919307663356, NaN, 0.01507596795875631

    #k=100
    Average Training Accuracy, Error Rate, F Measure, TPrate, FPrate
    0.9732301241735203, 0.026769875826479595, 0.03429469870807979, NaN, 0.015179804836445513

     */
}
