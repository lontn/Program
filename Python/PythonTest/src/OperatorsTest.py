#Python算术运算符
a = 10;
b = 20;

print(a + b);
print(a - b);
print(a * b);
print(a / b); #取模 - 返回除法的餘數
print(b % a); #幂 - 返回x的y次幂
print(a ** b);
print(9 // 2);#取整除 - 返回商的整数部分

a = 21
b = 10
c = 0

c = a + b
print("Line 1 - Value of c is ", c)

c = a - b
print("Line 2 - Value of c is ", c)

c = a * b
print("Line 3 - Value of c is ", c) 

c = a / b
print("Line 4 - Value of c is ", c) 

c = a % b
print("Line 5 - Value of c is ", c)

a = 2
b = 3
c = a**b 
print("Line 6 - Value of c is ", c)

a = 10
b = 5
c = a//b 
print("Line 7 - Value of c is ", c)

#Python比较运算符
a = 21
b = 10
c = 0

if(a == b):
    print("Line 1 - a is equal to b")
else:
    print("Line 1 - a is not equal to b")
