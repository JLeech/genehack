#!/usr/bin/python

import os

for filename in os.listdir("."):
	n = len(filename)
	if filename[n-4: n] == '.bz2':
		print "extracting file", filename
		os.system("bzip2 -dk " + filename)
