package edu.gatech.cse6242.cbioportal.service.impl;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;

public class PolynomialKernelSimilarity implements DistanceMeasure {

    /**
     *
     */
    private static final long serialVersionUID = 113839833688979121L;
    private double exponent=1;

    public PolynomialKernelSimilarity(double exponent){
        this.exponent=exponent;
    }

    public double measure(Instance i, Instance j) {
        double result;
        result = dotProd(i, j);
        if (exponent != 1.0) {
            result = Math.pow(result, exponent);
        }
        return result;
    }

    /**
     * Calculates a dot product between two instances
     */
    protected final double dotProd(Instance inst1, Instance inst2) {

        double result = 0;

        for (int i = 0; i < inst1.noAttributes(); i++) {
            result += inst1.value(i) * inst2.value(i);
        }
        return result;
    }

    public boolean compare(double x, double y) {
        return (x < y);
        //throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public double getMinValue() {
        return 0.0;
        //throw new UnsupportedOperationException("Not implemented");
    }
    @Override
    public double getMaxValue() {
        return 1.0;
        //throw new UnsupportedOperationException("Not implemented");
    }


}