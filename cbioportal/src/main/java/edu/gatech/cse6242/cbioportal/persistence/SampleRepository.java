package edu.gatech.cse6242.cbioportal.persistence;

import edu.gatech.cse6242.cbioportal.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

    @Query("SELECT s FROM Sample s WHERE s.patientId in :patientIds")
    public List<Sample> findAllByPatientId(List<String> patientIds);

}
