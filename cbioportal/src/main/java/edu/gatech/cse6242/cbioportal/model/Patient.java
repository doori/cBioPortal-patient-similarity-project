package edu.gatech.cse6242.cbioportal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="PATIENT")
public class Patient {
    @Id
    private Long id;
    private String patientId;
    private String sex;
    private String vitalStatus;
    private String smokingHistory;
    private String osMonths;
    private String osStatus;
    @JsonManagedReference
    @OneToMany(fetch=FetchType.EAGER, mappedBy="patient", cascade=CascadeType.ALL)
    @NotNull
    private List<Sample> samples = new ArrayList<>();

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getVitalStatus() {
        return vitalStatus;
    }

    public void setVitalStatus(String vitalStatus) {
        this.vitalStatus = vitalStatus;
    }

    public String getSmokingHistory() {
        return smokingHistory;
    }

    public void setSmokingHistory(String smokingHistory) {
        this.smokingHistory = smokingHistory;
    }

    public String getOsMonths() {
        return osMonths;
    }

    public void setOsMonths(String osMonths) {
        this.osMonths = osMonths;
    }

    public String getOsStatus() {
        return osStatus;
    }

    public void setOsStatus(String osStatus) {
        this.osStatus = osStatus;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}