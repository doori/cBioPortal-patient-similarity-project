package edu.gatech.cse6242.cbioportal.util;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;

public class DatasetUtil {

    public static CustomDataset loadCnaData() throws IOException {
        return loadData("data/msk_processed.tsv");
    }

    public static CustomDataset loadCnaDataWithoutUnknownTypes() throws IOException {
        return loadData("data/msk_without_unknown_types.tsv");
    }

    public static CustomDataset loadCnaDataWithoutZeros() throws IOException {
        return loadData("data/msk_processed_without_zeros.tsv");
    }

    public static CustomDataset loadCnaPatientData() throws IOException {
        return loadData("data/msk_processed_patient.tsv");
    }

    private static CustomDataset loadData(String fileName) throws IOException {
        File file = new File(fileName);
        Dataset data = FileHandler.loadDataset(file, 0, "\t");
        CustomDataset dataset = new CustomDataset(data);
        // remove header
        dataset.remove(0);
        return dataset;

    }
}
