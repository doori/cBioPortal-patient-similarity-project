package edu.gatech.cse6242.cbioportal.service.impl;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.AbstractSimilarity;

/*
Implementation of Jaccard Similarity where
the numerator is the number of common genomic alterations (non-zero elements) in A and B
and the denominator is the number of distinct alterations in A and B.

          # (Ai != 0) && (Ai == Bi) ^ 2
JI(A,B) = -----------------------------
          # (Ai != 0) || (Bi != 0)

This implementation assumes that A and B have equal length.
 */
public class JaccardExpSimilarity extends AbstractSimilarity {

    @Override
    public double measure(Instance a, Instance b) {

        double numer = 0.0;
        int denom = 0;
        for (int i = 0; i < a.noAttributes(); i++) {
            double ai = a.get(i);
            double bi = b.get(i);
            if (ai != 0.0) {
                denom++;
                if (ai == bi) {
                    numer++;
                }
            }
            if (bi != 0.0) {
                denom++;
            }
        }
        if (denom == numer || numer == 0.0) {
            return 0.0000001;
        }
        return numer * numer / (denom - numer);
    }
}
