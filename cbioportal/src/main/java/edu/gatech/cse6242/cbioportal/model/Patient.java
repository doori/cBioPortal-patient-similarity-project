package edu.gatech.cse6242.cbioportal.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Patient {
    @Id
    private Long id;
    private String patientId;
    private String cancerType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
