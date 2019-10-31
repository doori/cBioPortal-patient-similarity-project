package edu.gatech.cse6242.cbioportal.util;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;

public class DatasetUtil {

    public static CustomDataset loadCnaData() throws IOException {
        File file = new File("data/msk_processed.tsv");
        Dataset data = FileHandler.loadDataset(file, 0, "\t");
        CustomDataset dataset = new CustomDataset(data);
        // remove header
        dataset.remove(0);
        return dataset;
    }

    public static CustomDataset loadCnaDataWithoutUnknownTypes() throws IOException {
        File file = new File("data/msk_without_unknown_types.tsv");
        Dataset data = FileHandler.loadDataset(file, 0, "\t");
        CustomDataset dataset = new CustomDataset(data);
        // remove header
        dataset.remove(0);
        return dataset;
    }
}
