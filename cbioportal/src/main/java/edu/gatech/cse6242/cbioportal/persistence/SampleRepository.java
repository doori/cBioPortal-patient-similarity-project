package edu.gatech.cse6242.cbioportal.persistence;

import edu.gatech.cse6242.cbioportal.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

}
