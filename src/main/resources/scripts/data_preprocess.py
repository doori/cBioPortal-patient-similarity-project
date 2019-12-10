import pandas as pd
import sys
import argparse

data_dir = "../../../../data/"

"""
Save features from clinical information from data_clinical_sample.txt 
and genomic alterations information from data_CNA.txt.
"""
def patient_matrix(save_fname, exclude_unknowns, exclude_zeros, include_clinical_data):
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

  if exclude_unknowns:
    print(dataSample)
    # Remove records with Unknown Cancer Type
    dataSample = dataSample[dataSample.CANCER_TYPE != 'Cancer of Unknown Primary']
    print(dataSample)

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

  if include_clinical_data:
    dataPatient = pd.read_csv(data_dir + "data_clinical_patient.txt", \
                              sep="\t", comment="#")

    dataPatient = dataPatient.replace({"SEX": {"NA":0, "Female":0, "Male":1}})
    dataPatient = dataPatient.replace({"VITAL_STATUS": {"NA":0, "DECEASED":0, "ALIVE":1}})
    grouped["VITAL_STATUS"] = dataPatient["VITAL_STATUS"]
    grouped["SEX"] = dataPatient["SEX"]
    print(grouped)

  if exclude_zeros:
    # Exclude all-zero rows
    print(grouped)
    grouped = grouped.loc[grouped[grouped.columns.difference(['CANCER_TYPE'])].sum(axis=1) != 0.0]
    print(grouped) # 10335 -> 4787
  # Save merged dataframe into a CSV file in data_dir
  grouped.to_csv(data_dir + save_fname, index=False, sep="\t")


if __name__ == "__main__":
  parser = argparse.ArgumentParser(description='sample command:  python data_preprocess.py -f msk_processed.tsv -u true')
  parser.add_argument("-f", "--file", dest="filename",
                      help="specify the filename to be saved in data folder. Default: msk_processed.tsv",
                      default='msk_processed.tsv', type=str)

  parser.add_argument("-u", "--unknown", dest="exclude_unknown_cancer",
                      help="'true' or 'false' whether to exclude unknown cancer types or not. Default: False",
                      default='False', type=str)

  parser.add_argument("-z", "--zero", dest="exclude_all_zeros",
                      help="'true' or 'false' whether to exclude all zero rows or not. Default: False",
                      default='False', type=str)

  parser.add_argument("-c", "--clinical", dest="include_clinical_data",
                      help="'true' or 'false' whether to include clinical data or not. Default: False",
                      default='False', type=str)

  args = parser.parse_args()

  exclude_zeros = False
  exclude_unknowns = False
  include_clinical_data = True

  if args.exclude_unknown_cancer.lower() == 'true':
    exclude_unknowns = True
  if args.exclude_all_zeros.lower() == 'true':
    exclude_zeros = True
  if args.include_clinical_data.lower() == 'false':
    include_clinical_data = False

  patient_matrix(args.filename, exclude_unknowns, exclude_zeros, include_clinical_data)

