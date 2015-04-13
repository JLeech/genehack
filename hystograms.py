import numpy as np
import pandas as pd
import seaborn as sns
from matplotlib import pylab
import matplotlib.pyplot as plt

from random import randint

def loadRealData(filename, max_count):
    x = {}
    count = 0
    with open(filename) as file:
        for line in file:
            if count % 1000000 == 0:
		if count == 0:
			count += 1
			continue
                print count, "ololo"
	    if max_count < count: break
            smth = line.split('\t')
            x[int(smth[0])] = smth[1:]
            count += 1
        file.close()
    return x

def plotData(x, left, right, fname):
    y = {}
    for index in range(left, right):
        if x.get(index) != None:
            y[int(index)] = map(lambda x: float(x), x[index])
    print "plot"
    df = pd.DataFrame(data=y.values(), index=map(lambda x: x+1,sorted(y.keys())), columns=['A', 'C', 'G', 'T', 'N', '-'])
    df.plot(kind='bar',stacked=True, legend=True, sort_columns=True, mark_right=True)
    print "save"
    fig = plt.gcf()
    plt.savefig(fname+".png")

print "loading..."
data = loadRealData("out_sorted.vcf", 2000000)
print "load conplete!"
#plotData(data, 0, 1000)
for i in range(130, 140):
	plotData(data, i*100, i*100+50, str(i))

def calcDistribution(data):
	x = range(0, 2000)
	y = np.zeros(2000)#sum(data.values)
	print len(x), len(y)
	for i in range(0, 2000):
		count = 0
		for key in range(i*1000, (i+1)*1000):
			if data.get(key) != None:
				count += 1
		y[i] = count
	pylab.plot(x, y)
	pylab.show()
	pylab.savefig("all.png")

#calcDistribution(data)


def plotGoodPoint(x, left, right, fname):
    y = {}
    for index in range(left, right):
        if x.get(index) != None:
	    z_count = 0
	    for elem in x[index]:
		if elem == 0. : z_count += 1
	    if z_count > 1:
		y['A'] = x[index][0]
		y['C'] = x[index][1]
		y['G'] = x[index][2]
		y['T'] = x[index][3]
		y['N'] = x[index][4]
		y['-'] = x[index][5]
            	break
    print "plot"
    
    df = pd.DataFrame(data=y.values(), index=y.keys())
    df.plot(kind='bar',stacked=False, legend=False, rot=3.14/2) 
    
    print "save"
    fig = plt.gcf()
    plt.savefig(fname+".png")

#plotGoodPoint(data, 0, 2000000, "point.png")
