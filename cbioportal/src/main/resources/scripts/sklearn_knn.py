import pandas as pd
import numpy as np
from sklearn.neighbors import KNeighborsClassifier
from experiment_graphs import plot_confusion_matrix
import sys

def loadData():
  data = pd.read_csv('../../../../data/msk_processed_without_zeros.tsv', sep="\t")
  y = np.array(data['CANCER_TYPE'].values)
  data = data.drop('CANCER_TYPE', axis=1)
  x = np.array(data.values)
  return x, y

def knn(x,y,algo):
  knn = KNeighborsClassifier(n_neighbors=10, algorithm=algo).fit(x,y)
  
  top15 = ["Breast Cancer", "Non-Small Cell Lung Cancer", "Colorectal Cancer", "Prostrate Cancer",
  "Glioma", "Esphagogastric Cancer", "Soft Tissue Sarcoma", "Hepatobilary Cancer",
  "Germ Cell Tumor", "Ovarian Cancer", "Thyroid Cancer", "Bladder Cancer",
  "Endometrial Cancer", "Melanoma", "Cancer of Unknown Primary"]
  y = y.tolist()

  yTrue, yPred = [], []
  for i in range(x.shape[0]):
  	p = knn.predict([x[i]])
  	if p[0] in top15 and y[i] in top15:
  		yPred.append(p[0])
  		yTrue.append(y[i])
  return yTrue, yPred

def confusion_matrix_knn(x,y,algo):
  yTrue, yPred = knn(x,y,algo)

  plot_confusion_matrix(yTrue, yPred, filename='skl_knn_'+algo+'.png', title='KNN Confusion Matrix '+algo, normalize=True, show_text=True)


# python sklearn_knn.py auto
# python sklearn_knn.py ball_tree
# python sklearn_knn.py kd_tree
# python sklearn_knn.py brute

if __name__ == "__main__":
  x, y = loadData()

  if sys.argv[1] == 'auto': # default
  	confusion_matrix_knn(x,y, 'auto')
  if sys.argv[1] == 'ball_tree': 
  	confusion_matrix_knn(x,y, 'ball_tree')
  if sys.argv[1] == 'kd_tree': 
  	confusion_matrix_knn(x,y, 'kd_tree')
  if sys.argv[1] == 'brute': 
  	confusion_matrix_knn(x,y, 'brute')