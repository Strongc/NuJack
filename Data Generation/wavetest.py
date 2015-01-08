import matplotlib.pyplot as plt
import scipy.io.wavfile as wavfile

print("test")
print("Yay capstone")
print(0b10101)


#rate, data2 = wavfile.read('0100100110001110.wav')
rate, data2 = wavfile.read('wavTones.com.unregistred.sin_1000Hz_-6dBFS_1s.wav')
print("Rate",rate)
print("Data2",data2)
print(data2.dtype)
print("Length", len(data2))
print("Length in Seconds",len(data2)/rate)
plt.plot(data2)
plt.show()

checkflag = 0
prev = 0
highcount = 0
lowcount = 0
bitlist = []

f1 = open('data.txt', 'w')
f1.write('')

# Write data to file

#for i in range(0, len(data2)):
 #   f = open('data.txt', 'a')
  #  f.write(str(data2[i][0]) + '\n')

for i in range(0,len(data2)):
    current = data2[i][0]
    previous = data2[i-5][0]
    if checkflag == 0:  # Only check every 5 iterations
        if previous > (current + 3000): # if current is 3000 less than previous. so if 5 data points past is 3000 lower
##            print("LOW",previous,current,i)
            t = i-prev  # t is the difference between this and last point checked
            print(t)
            checkflag = 5 # reset check flag

            if t > 170 and t  < 190: #if the difference is in this range, we are in LOW
                print("LOW")
                highcount = 0
                lowcount = lowcount + 1
                if lowcount > 3: #if this range is found
                    print("LOW: 0")
                    lowcount = 0
                    bitlist.append(0)
                    
            elif t > 115 and t < 125:
                print("HIGH")
                lowcount = 0
                highcount = highcount+1
                if highcount > 6:
                    print("HIGH: 1")
                    highcount = 0
                    bitlist.append(1)
            
            prev = i
##        if previous < (current-3000):
##            print("High",previous,current,i)
##            print(i-prev)
##            checkflag = 5
##            prev = i
    else:
        checkflag = checkflag - 1
print (bitlist)
        
