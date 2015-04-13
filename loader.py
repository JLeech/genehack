def loadRealData(filename):
    x = {}
    count = 0
    with open(filename) as file:
        for line in file:
            if count % 1000000 == 0:
                print count, "ololo"
            index, arr = line.split(' ')
            #arr = eval(arr)
            #s = float(sum(arr))
            #for i in range(len(arr)):
                #arr[i] /= s
            x[int(index)] = arr
            count += 1
        file.close()
    return x


data = loadRealData('collected2')

print "load comlete!"

keys = sorted(data.keys())

print "sort coplete!"

file = open("out_sorted.vcf", "w")
file.write("POS\tA\tT\tC\tG\tN\t.\n")
for pos in keys:
	arr = eval(data[pos])
	s = float(sum(arr))
	#print arr, s
	#A, T, C, G, N, D = arr
	file.write(str(pos) + "\t" + str(arr[0]/s) + "\t" + str(arr[1]/s) + "\t" + str(arr[2]/s) + "\t" + str(arr[3]/s) + "\t" + str(arr[4]/s) + "\t" + str(arr[5]/s) + "\n")
file.close()
