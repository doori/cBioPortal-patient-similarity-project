package edu.gatech.cse6242.cbioportal.service.impl;

import net.sf.javaml.core.Instance;
import Jama.Matrix;
import net.sf.javaml.distance.AbstractDistance;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public class MahalanobisSimilarity extends AbstractDistance {

    private static final long serialVersionUID = -5844297515283628612L;

    public double measure(Instance i, Instance j) {
        //XXX optimize
        double[][] del = new double[3][1];
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 1; n++) {
                del[m][n] = i.value(m) - j.value(m);
            }
        }
        Matrix M1 = new Matrix(del);
        Matrix M2 = M1.transpose();

        double[][] covar = new double[3][3];
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                covar[m][n] += (i.value(m) - j.value(m)) * (i.value(n) - j.value(n));
            }
        }
        double dist = 0.0;
        try {
            Matrix cov = new Matrix(covar);
            Matrix covInv = cov.inverse();
            Matrix temp1 = M2.times(covInv);
            Matrix temp2 = temp1.times(M1);
            dist = temp2.trace();
            if (dist > 0.) {
                dist = Math.sqrt(dist);
            }
        } catch (Exception e) {
            RealMatrix cov = MatrixUtils.createRealMatrix(covar);
            DecompositionSolver solver = new SingularValueDecomposition(cov).getSolver();
            RealMatrix covInv = solver.getInverse();

            RealMatrix m2 = MatrixUtils.createRealMatrix(M2.getArray());
            RealMatrix m1 = MatrixUtils.createRealMatrix(M1.getArray());
            RealMatrix temp1 = m2.multiply(covInv);
            RealMatrix temp2 = temp1.multiply(m1);
            dist = temp2.getTrace();
            if (dist > 0.) {
                dist = Math.sqrt(dist);
            }
        }
        return dist;
    }
}
