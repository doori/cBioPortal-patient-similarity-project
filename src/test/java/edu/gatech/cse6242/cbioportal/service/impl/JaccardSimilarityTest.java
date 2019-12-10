package edu.gatech.cse6242.cbioportal.service.impl;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class JaccardSimilarityTest {

    @Test
    public void measure() {
        JaccardSimilarity jaccard = new JaccardSimilarity();
        double[] av = new double[] {0,1.0,0,2.0,0,0,0,4.0,0,0};
        Instance ad = new SparseInstance(av, "positive");
        System.out.println(ad);

        double[] bv = new double[] {0,1.0,0,2.0,0,0,0,4.0,0,0};
        Instance bd = new SparseInstance(bv, "positive");
        System.out.println(bd);

        double[] cv = new double[] {0,0,0,0,0,0,0,0,0,0};
        Instance cd = new SparseInstance(cv, "negative");
        System.out.println(cd);

        double[] dv = new double[] {0,0,1.0,0,2.0,0,0,0,4.0,0};
        Instance dd = new SparseInstance(dv, "positive");
        System.out.println(dd);

        double same = jaccard.measure(ad, bd);
        double zero = jaccard.measure(ad, cd);
        double alsoZero = jaccard.measure(ad, dd);

        Assert.assertTrue(1.0 == same);
        Assert.assertTrue(0.0 == zero);
        Assert.assertTrue(0.0 == alsoZero);
    }
}