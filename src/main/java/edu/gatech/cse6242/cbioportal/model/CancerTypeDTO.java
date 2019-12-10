package edu.gatech.cse6242.cbioportal.model;

import java.util.Map;

public class CancerTypeDTO {

    private String patientId;
    private String cancerType;
    private Map<Object, Double> classDistribution;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCancerType() {
        return cancerType;
    }

    public void setCancerType(String cancerType) {
        this.cancerType = cancerType;
    }

    public Map<Object, Double> getClassDistribution() {
        return classDistribution;
    }

    public void setClassDistribution(Map<Object, Double> classDistribution) {
        this.classDistribution = classDistribution;
    }
}
