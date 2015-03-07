#Python位运算符
a = 60            # 60 = 0011 1100 
b = 13            # 13 = 0000 1101 
c = 0

c = a & b;        # 12 = 0000 1100
print("Line 1 - Value of c is ", c)

c = a | b;        # 61 = 0011 1101 
print("Line 2 - Value of c is ", c)

c = a ^ b;        # 49 = 0011 0001
print("Line 3 - Value of c is ", c)

c = ~a;           # -61 = 1100 0011
print("Line 4 - Value of c is ", c)

c = a << 2;       # 240 = 1111 0000
print("Line 5 - Value of c is ", c)

c = a >> 2;       # 15 = 0000 1111
print("Line 6 - Value of c is ", c)

#Python逻辑运算符
a = 10
b = 20
c = 0

if ( a and b ):
   print("Line 1 - a and b are true")
else:
   print ("Line 1 - Either a is not true or b is not true")

if ( a or b ):
   print("Line 2 - Either a is true or b is true or both are true")
else:
   print("Line 2 - Neither a is true nor b is true")


a = 0
if ( a and b ):
   print("Line 3 - a and b are true")
else:
   print ("Line 3 - Either a is not true or b is not true")

if ( a or b ):
   print("Line 4 - Either a is true or b is true or both are true")
else:
   print("Line 4 - Neither a is true nor b is true")

if not( a and b ):
   print("Line 5 - Either a is not true or b is  not true or both are not true")
else:
   print("Line 5 - a and b are true")