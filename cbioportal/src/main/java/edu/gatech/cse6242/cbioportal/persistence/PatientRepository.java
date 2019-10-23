package edu.gatech.cse6242.cbioportal.persistence;

import edu.gatech.cse6242.cbioportal.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    public Patient findByPatientId(String patientId);
}
