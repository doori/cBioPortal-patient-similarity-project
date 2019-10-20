DROP TABLE IF EXISTS PATIENT;

CREATE TABLE PATIENT (
  id INT AUTO_INCREMENT(0,1) PRIMARY KEY,
  patient_id VARCHAR(250) NOT NULL,
  cancer_type VARCHAR(250) NOT NULL
);

INSERT INTO PATIENT (patient_id, cancer_type) VALUES
  ('P1', 'Cancer1'),
  ('P2', 'Cancer2'),
  ('P3', 'Cancer3');