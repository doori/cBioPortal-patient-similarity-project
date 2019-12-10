package edu.gatech.cse6242.cbioportal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;

/**
 * Provides a standard data set implementation.
 *
 * @see Dataset
 *
 * @author Thomas Abeel
 *
 */
public class CustomDataset extends DefaultDataset {

    /**
     *
     * Creates a data set that contains the provided instances
     *
     * @param coll
     *            collection with instances
     */
    public CustomDataset(Collection<Instance> coll) {
        this.addAll(coll);
    }

    /**
     * Creates an empty data set.
     */
    public CustomDataset(DefaultDataset dd) {
        super(dd);
    }

    /**
     * Returns the k instances of the given data set that are the closest to the
     * instance that is given as a parameter.
     *
     * @param dm
     *            the distance measure used to calculate the distance between
     *            instances
     * @param inst
     *            the instance for which we need to find the closest
     * @return the instances from the supplied data set that are closest to the
     *         supplied instance
     *
     */
    public Map<Integer, Double> kNearestMap(int k, Instance inst, DistanceMeasure dm) {
        Map<Integer, Double> closest = new HashMap<>();
        double max = dm.getMaxValue();
        for (int i = 0; i < this.size(); i++) {
            Instance other = this.get(i);
            double d = dm.measure(inst, other);
            if (dm.compare(d, max) && !inst.equals(other)) {
                closest.put(i, d);
                if (closest.size() > k)
                    max = removeFarthest(closest,dm);
            }

        }
        return closest;
    }

    /*
     * Removes the element from the vector that is farthest from the supplied
     * element.
     */
    private double removeFarthest(Map<Integer, Double> vector, DistanceMeasure dm) {
        Integer idx = null;
        double max = dm.getMinValue();
        for (Integer i : vector.keySet()) {
            double d = vector.get(i);

            if (dm.compare(max,d)) {
                max = d;
                idx = i;
            }
        }
        vector.remove(idx);
        return max;

    }
}