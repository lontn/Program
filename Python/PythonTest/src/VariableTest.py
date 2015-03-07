counter = 10 #賦值整型變量
miles = 1000.0 #浮點型
name = "Lon" #字串
unitPrice = 3.99
shipping =1.99

print(counter);
print(miles);
print(name);
print(unitPrice + shipping)
print(unitPrice / 3)
print(unitPrice / 4)
print(1 / 3)
print("\r")
# Python字符串
str = 'Hello World!'
print(str) # 輸出完整字符串
print(str[0]) # 輸出字符串中的第一個字符
print(str[2:5]) # 輸出字符串中第三個至第五個之間的字符串
print(str[2:]) # 輸出從第三個字符開始的字符串
print(str * 3) # 輸出字符串三次
print(str + "TEST") # 輸出連接的字符串

# Python列表
list = [ 'abcd', 786 , 2.23, 'john', 70.2 ]
tinylist = [123, 'john']
print(list) # 輸出完整列表
print(list[0]) # 輸出列表的第一個元素
print(list[1:3]) # 輸出第二個至第三個的元素
print(list[2:]) # 輸出從第三個開始至列表末尾的所有元素
print(tinylist * 2) # 輸出列表兩次
print(list + tinylist) # 打印組合的列表
print(tinylist * 2 + list)
print(tinylist + list * 2)

# Python元组
tuple = ( 'abcd', 786 , 2.23, 'john', 70.2 )
tinytuple = (123, 'john')
print("tuple:", tuple)
print(tuple) # 輸出完整元組
print(tuple[0]) # 輸出元組的第一個元素
print(tuple[1:3]) # 輸出第二個至第三個的元素
print(tuple[2:]) # 輸出從第三個開始至列表末尾的所有元素
print(tinytuple * 2) # 輸出元組兩次
print(tuple + tinytuple) # 打印組合的元組

tuple = ( 'abcd', 786 , 2.23, 'john', 70.2 )
list = [ 'abcd', 786 , 2.23, 'john', 70.2 ]
#tuple[2] = 1000 # 元組中是非法應用
#list[2] = 1000 # 列表中是合法應用

print("\n")
#Python元字典(dictionary)
dict = {}
dict['one'] = "This is one"
dict[2] = "This is two"
tinydict = {'name': 'john','code':6734, 'dept': 'sales'}
print(dict['one']) # 輸出鍵為'one' 的值
print(dict[2]) # 輸出鍵為 2 的值
print(tinydict) # 輸出完整的字典
print(tinydict.keys()) # 輸出所有鍵
print(tinydict.values()) # 輸出所有值
