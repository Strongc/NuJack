import math
import matplotlib.pyplot as plt

# Generate cords. Pass number of points and period	multiplier.
# So for a regular sine wave (period 2*pi) with 50 points, call gendCords2(50, 1).
# To double the period, call genCords2(50,2)
def genCords2(num_points, period):
    points = reversed(range(0, num_points+1))    
    xs = map (lambda x: 1*math.pi * x/num_points, points)
    ys = map (lambda x: -5000*math.sin(2 * period * x), xs)
    return zip (xs, ys)

def accum(bits):
    cords = []
    for bit in bits:
        if bit == 0:
            cords = cords + genCords2(50,1)
        else:
            cords = cords + genCords2(100,1)
    return cords

def writeToFile(data):
    open('data.txt', 'w').write('')
    for _,y in data:
        f = open('data.txt', 'a')
        f.write(str(int(y)) + '\n')
        

writeToFile(accum([0,0,0,0,1,1,1,1,1,1]))
plt.plot(accum([0,0,0,0,1,1,1,1,1,1]))
plt.show()

