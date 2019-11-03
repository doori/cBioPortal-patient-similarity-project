import matplotlib
import matplotlib.pyplot as plt
import numpy as np
import sys

# Performance of KNN with different k values
def knn_hp_tuning_bar():
    labels = ['Accuracy', 'F1 score', 'Error Rate']
    k5 = [0.9722560877277857, 0.027743912272214153, 0.05696712135068837]
    k10 = [0.9728721174004192, 0.02712788259958071, 0.06146930184145845]
    k50 = [0.9738074504112239, 0.026192549588776, 0.04442810281507143]
    k100 = [0.9732301241735203, 0.026769875826479595, 0.03429469870807979]

    x = np.arange(len(labels))
    width = 0.15

    fig, ax = plt.subplots()
    ax.bar(x - width*3/2, k5, width, label='5')
    ax.bar(x - width/2, k10, width, label='10')
    ax.bar(x + width/2, k50, width, label='50')
    ax.bar(x + width*3/2, k100, width, label='100')

    ax.set_ylabel('Performance')
    ax.set_title('k-Nearest Neighbors Performance')
    ax.set_xticks(x)
    ax.set_xticklabels(labels)
    ax.legend()

    fig.tight_layout()

    plt.savefig('knn_hp_tuning_bar.png')


def knn_hp_tuning():
    fscore = [0.05415105539255187, 0.05696712135068837, 0.06146930184145845, 0.05339633113397584, 0.04914621852129556, 0.040626845107043404, 0.0374919307663356, 0.03429469870807979]
    error = [0.02831478793742944, 0.027743912272214153, 0.02712788259958071, 0.026798903402676988, 0.026956942428640537, 0.026376390904692786, 0.02658925979680696, 0.026769875826479595]
    fpr = [0.014784792963570072, 0.014539404212185088, 0.014282876860788302, 0.01430606098124899, 0.014430903016393298, 0.014951752584144525, 0.01507596795875631, 0.015179804836445513]
    x = [3, 5, 10, 20,40,60,80,100]

    plt.plot(x, fscore)
    plt.plot(x, error)
    plt.plot(x, fpr)
    plt.xticks(x)

    plt.title("k-Nearest Neighbors Performance")
    plt.xlabel("k")
    plt.ylabel("Performance")
    plt.legend(['F-score','Error', 'FPR'])
    plt.savefig('knn_hp_tuning.png')


def rf_hp_tuning():
    fscore = [0.2928046453719586, 0.30667985401951847, 0.3370549918146893, 0.34641085010558226, 0.3428833652426275, 0.3356221678217629]
    error = [0.019219480728914686, 0.019035639412997896, 0.018155136268343808, 0.01806160296726334, 0.018264796000645057, 0.018158361554587974]
    fpr = [0.011238039346002692, 0.011134158975015997, 0.010567554698167763, 0.010548068403556583, 0.010631012559310173, 0.010572420976344688]
    # (maxDepth, numFeatures, numTrees)
    x = ["(50,50,10)", "(50,50,50)", "(100,14,10)", "(100,14,50)", "(100,100,10)", "(100,100,50)"]

    plt.plot(x, fscore)
    plt.plot(x, error)
    plt.plot(x, fpr)
    plt.xticks(x)

    plt.title("RandomForest Performance")
    plt.xlabel("(MaxDepth, NumFeatures, NumTrees)")
    plt.ylabel("Performance")
    plt.legend(['F-score','Error', 'FPR'])
    plt.savefig('rf_hp_tuning.png')

# How to run:
#   python experiment_graphs.py knn
#   python experiment_graphs.py rf

if __name__ == "__main__":
    if sys.argv[1] == 'knn':
        knn_hp_tuning()
    if sys.argv[1] == 'rf':
        rf_hp_tuning()