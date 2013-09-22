from itertools import *
import math

# a + b + sqrt(a^2 + b^2) = 1000
possibilities = permutations(range(1, 400), 2)
a_b = list(
        ifilter(lambda x: x[0] == 1000, 
            imap(lambda x: [x[0] + x[1] + math.sqrt(pow(x[0], 2) + pow(x[1], 2)), x], 
                ifilter(lambda x: x[0] < x[1], possibilities))))[0][1]

print a_b[0] * a_b[1] * (1000 - a_b[0] - a_b[1])    

