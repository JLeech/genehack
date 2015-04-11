filename = 'collected'

index = 0

with open(filename) as file:
        for line in file:
                arr = line.split(' ')
                arr[6] = arr[6][:len(arr[6])-1]
                print arr[0], ''.join(arr[1:])

