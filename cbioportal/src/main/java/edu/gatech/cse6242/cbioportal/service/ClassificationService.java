package edu.gatech.cse6242.cbioportal.service;

import edu.gatech.cse6242.cbioportal.util.CustomDataset;
import net.sf.javaml.classification.Classifier;

public interface ClassificationService {

    public Classifier getTrainedRandomForest(CustomDataset dataset);

    public Classifier getTrainedKNearestNeighbors(CustomDataset dataset) throws Exception;

}
