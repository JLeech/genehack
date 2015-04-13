#!/usr/bin/python

import os, sys

#current directory
path = "."

if len(sys.argv) > 1:
	 path = sys.argv[1]

#print os.listdir(path)
for filename in os.listdir(path):
	n = len(filename)
	if filename[n-4: n] == '.vcf':
		print 'parsing ' + filename
		out = open(filename[:n-4], 'w')
		with open(path+filename) as file:
                	for line in file:
                        	if line.startswith('#'): continue
	                        pos, _, ref, alt = line.split('\t')[1:5]
        	                if len(ref) == 1 and len(alt) == 1:
                	                out.write(pos + ' ' + ref.lower() + ' ' + alt.lower() + '\n')
		out.close()

