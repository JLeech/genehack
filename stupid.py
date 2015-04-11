#!/usr/bin/python

import sys

for filename in sys.argv[1:]:
	with open(filename) as file:
		for line in file:
			if line.startswith('#'): continue
		        pos, _, ref, alt = line.split('\t')[1:5]
		        if len(ref) == 1 and len(alt) == 1:
            			print pos, ref, alt

