package edu.gatech.cse6242.cbioportal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="SAMPLE")
public class Sample {
    @Id
    private Long id;
    private String patientId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="PATIENT_IDX", nullable=false)
    private Patient patient;
    private String sampleId;
    private String sampleCollectionSource;
    private String specimenPreservationType;
    private String specimenType;
    private String dnaInput;
    private String sampleCoverage;
    private String tumorPurity;
    private String matchedStatus;
    private String sampleType;
    private String primarySite;
    private String metastaticSite;
    private String sampleClass;
    private String oncotreeCode;
    private String cancerType;
    private String cancerTypeDetailed;

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

    public Patient getPatient() { return patient; }

    public void setPatient(Patient patient) { this.patient = patient; }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleCollectionSource() {
        return sampleCollectionSource;
    }

    public void setSampleCollectionSource(String sampleCollectionSource) {
        this.sampleCollectionSource = sampleCollectionSource;
    }

    public String getSpecimenPreservationType() {
        return specimenPreservationType;
    }

    public void setSpecimenPreservationType(String specimenPreservationType) {
        this.specimenPreservationType = specimenPreservationType;
    }

    public String getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(String specimenType) {
        this.specimenType = specimenType;
    }

    public String getDnaInput() {
        return dnaInput;
    }

    public void setDnaInput(String dnaInput) {
        this.dnaInput = dnaInput;
    }

    public String getSampleCoverage() {
        return sampleCoverage;
    }

    public void setSampleCoverage(String sampleCoverage) {
        this.sampleCoverage = sampleCoverage;
    }

    public String getTumorPurity() {
        return tumorPurity;
    }

    public void setTumorPurity(String tumorPurity) {
        this.tumorPurity = tumorPurity;
    }

    public String getMatchedStatus() {
        return matchedStatus;
    }

    public void setMatchedStatus(String matchedStatus) {
        this.matchedStatus = matchedStatus;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getPrimarySite() {
        return primarySite;
    }

    public void setPrimarySite(String primarySite) {
        this.primarySite = primarySite;
    }

    public String getMetastaticSite() {
        return metastaticSite;
    }

    public void setMetastaticSite(String metastaticSite) {
        this.metastaticSite = metastaticSite;
    }

    public String getSampleClass() {
        return sampleClass;
    }

    public void setSampleClass(String sampleClass) {
        this.sampleClass = sampleClass;
    }

    public String getOncotreeCode() {
        return oncotreeCode;
    }

    public void setOncotreeCode(String oncotreeCode) {
        this.oncotreeCode = oncotreeCode;
    }

    public String getCancerType() {
        return cancerType;
    }

    public void setCancerType(String cancerType) {
        this.cancerType = cancerType;
    }

    public String getCancerTypeDetailed() {
        return cancerTypeDetailed;
    }

    public void setCancerTypeDetailed(String cancerTypeDetailed) {
        this.cancerTypeDetailed = cancerTypeDetailed;
    }
}
