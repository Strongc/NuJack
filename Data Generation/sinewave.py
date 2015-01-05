import math
import matplotlib.pyplot as plt

# Generate xCords. Pass num of points and period
def generateXCords(num_points, period):
    points = reversed(range(1, num_points))
    xs = map (lambda x: 2*math.pi / x, points)
    print '\n\nxs:\n' + str(xs)
    ys = map (lambda x: 5000*math.sin(period * x), xs)
    return zip (xs, ys)
	
def genCords(num_points):
    points = reversed(range(2, num_points+1))
    xs = map (lambda x: 2*math.pi * x/num_points, points)
    print '\n\nxs:\n' + str(xs)
    ys = map (lambda x: 5000*math.sin(1 * x), xs)
    return zip (xs, ys)
	
def genCords2(num_points, period):
    points = reversed(range(1, num_points+1))
    xs = map (lambda x: period/2*math.pi * x/num_points, points)
    print '\n\nxs:\n' + str(xs)
    ys = map (lambda x: 5000*math.sin(period * x), xs)
    return zip (xs, ys)

# Pass period as 1/2 for long wave.
def getYCord( x, period ):
   return 5000*math.sin(math.pi * period * x)
   
#print '\n\nres:\n' + str(generateXCords(100, 1))
plt.plot(genCords2(100, 2))
plt.show()