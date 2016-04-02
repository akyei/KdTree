# KdTree

Simple K-d tree implementation (k=3). Tracks mobile phones of a set of users on a 1024x1024 grid. Required a data set with the following schema (MID, XLOC, YLOC, TIME) where:
1. MID is a mobile phone ID – an integer in the closed interval [0,
9999999999]
2. XLOC and YLOC are integers in the [0,1023] closed interval.
3. TIME is an integer in the closed interval [0,1023]

For example, a record of the form (2818191901,60,70,33) says that during
hour 33, the mobile phone 2818191901 was at location (60,70).

Supports insertion and range queries (triangle, time-int, time-all-int):
1. triangle - Find all mobile phones that are within a triangle specified by vertices p1, p2, p3
2. time-int - Find all mobile phones that are inside a rectangle whose lower left corner is (x,y) and upper right corner is (x', y') at SOME time during the time interval [s,e].
3. time-all-int - Find all mobile phones that are inside the rectangle whose lower left corner is (x,y) and upper right corner is (x', y') at ALL times during the time interval [s,e]

Majority of the code is implemented in **Node.java**
