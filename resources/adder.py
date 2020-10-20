import argparse

parser = argparse.ArgumentParser(description='Process some integers.')
parser.add_argument('integers', metavar='N', type=int, nargs='+',
                    help='an integer for the accumulator')
parser.add_argument('--sum', dest='accumulate', action='store_const',
                    const=sum, default=max,
                    help='sum the integers (default: find the max)')

args = parser.parse_args()
sum=args.accumulate(args.integers)
print("sum=" + str(sum))
f = open("../resources/sum.txt", "w")
#f.truncate(0)
f.write("The sum of the two numbers you provided is: "+str(sum)+"\n")
f.close()