import pandas as pd
import sys

data_dir = "../../../../data/"

"""
Save features from clinical information from data_clinical_sample.txt 
and genomic alterations information from data_CNA.txt.
"""
def patient_matrix(save_fname):
  # Copy-number alterations
  dataCNA = pd.read_csv(data_dir + "data_CNA.txt", sep="\t", comment="#")

  dataCNA = dataCNA.set_index("Hugo_Symbol").T
  dataCNA.reset_index(inplace=True)
  dataCNA["SAMPLE_ID"] = dataCNA["index"]
  dataCNA.drop(columns=["index"], inplace=True)

  # Clinical data
  dataSample = pd.read_csv(data_dir + "data_clinical_sample.txt", \
    sep="\t", comment="#")
  dataSample = dataSample[["SAMPLE_ID", "PATIENT_ID", "CANCER_TYPE"]]
  dataSample = dataSample.dropna(subset=['CANCER_TYPE'])

  # Merge and Group by Patient ID
  merged = pd.merge(dataCNA, dataSample, on="SAMPLE_ID")
  grouped = merged.groupby("PATIENT_ID", as_index=False).sum()
  grouped = pd.merge(grouped,
                     dataSample[["PATIENT_ID", "CANCER_TYPE"]].drop_duplicates("PATIENT_ID"))

  # Re-arrange columns
  cols = grouped.columns.tolist()
  cols = cols[-1:] + cols[1:-1]
  grouped[cols[1:]] = grouped[cols[1:]].astype('int16')
  grouped = grouped[cols]
  print(grouped)

  # Save merged dataframe into a CSV file in data_dir
  grouped.to_csv(data_dir + save_fname, index=False, sep="\t")


if __name__ == "__main__":
  filename = "msk_processed.tsv"

  if len(sys.argv) > 1:
    filename =  sys.argv[1]

  patient_matrix(filename)
