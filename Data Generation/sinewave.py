import math
import matplotlib.pyplot as plt

# Generate cords. Pass num of points and period	
def genCords2(num_points, period):
    points = reversed(range(0, num_points+1))
    #xs = map (lambda x: period*2*math.pi * x/num_points, points)
    xs = map (lambda x: 1*math.pi * x/num_points, points)
    #print '\n\nxs:\n' + str(xs)
    ys = map (lambda x: -5000*math.sin(2 * period * x), xs)
    return zip (xs, ys)

# Pass period as 1/2 for long wave.
def getYCord( x, period ):
   return 5000*math.sin(math.pi * period * x)
   
#print '\n\nres:\n' + str(generateXCords(100, 1))
#plt.plot(genCords2(100, 2) + genCords2(100,1) + genCords2(100, 2))
#plt.show()

def accum(bits):
    cords = []
    for bit in bits:
        if bit == 0:
            cords = cords + genCords2(100,1)
        else:
            cords = cords + genCords2(100,2)
    return cords

#freq = accum([1,0,1])
#print freq
plt.plot(accum([1,0,1]))
plt.show()

