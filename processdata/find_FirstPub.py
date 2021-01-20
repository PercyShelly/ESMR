list2000ai = {}
list2000db = {}
list2001ai = {}
list2001db = {}
list2002ai = {}
list2002db = {}
list2003ai = {}
list2003db = {}
list2004ai = {}
list2004db = {}
list2005ai = {}
list2005db = {}
list2006ai = {}
list2006db = {}
list2007ai = {}
list2007db = {}
list2008ai = {}
list2008db = {}
list2009ai = {}
list2009db = {}
list2010ai = {}
list2010db = {}
list2011ai = {}
list2011db = {}
list2012ai = {}
list2012db = {}
list2013ai = {}
list2013db = {}
list2014ai = {}
list2014db = {}
list2015ai = {}
list2015db = {}
f0 = open('./classic2/a2000ai.txt','r',encoding='utf-8')
f1 = open('./classic2/a2001ai.txt','r',encoding='utf-8')
f2 = open('./classic2/a2002ai.txt','r',encoding='utf-8')
f3 = open('./classic2/a2003ai.txt','r',encoding='utf-8')
f4 = open('./classic2/a2004ai.txt','r',encoding='utf-8')
f5 = open('./classic2/a2005ai.txt','r',encoding='utf-8')
f6 = open('./classic2/a2006ai.txt','r',encoding='utf-8')
f7 = open('./classic2/a2007ai.txt','r',encoding='utf-8')
f8 = open('./classic2/a2008ai.txt','r',encoding='utf-8')
f9 = open('./classic2/a2009ai.txt','r',encoding='utf-8')
f10 = open('./classic2/a2010ai.txt','r',encoding='utf-8')
f11 = open('./classic2/a2011ai.txt','r',encoding='utf-8')
f12 = open('./classic2/a2012ai.txt','r',encoding='utf-8')
f13 = open('./classic2/a2013ai.txt','r',encoding='utf-8')
f14 = open('./classic2/a2014ai.txt','r',encoding='utf-8')
f15 = open('./classic2/a2015ai.txt','r',encoding='utf-8')
f20 = open('./classic2/a2000db.txt','r',encoding='utf-8')
f21 = open('./classic2/a2001db.txt','r',encoding='utf-8')
f22 = open('./classic2/a2002db.txt','r',encoding='utf-8')
f23 = open('./classic2/a2003db.txt','r',encoding='utf-8')
f24 = open('./classic2/a2004db.txt','r',encoding='utf-8')
f25 = open('./classic2/a2005db.txt','r',encoding='utf-8')
f26 = open('./classic2/a2006db.txt','r',encoding='utf-8')
f27 = open('./classic2/a2007db.txt','r',encoding='utf-8')
f28 = open('./classic2/a2008db.txt','r',encoding='utf-8')
f29 = open('./classic2/a2009db.txt','r',encoding='utf-8')
f30 = open('./classic2/a2010db.txt','r',encoding='utf-8')
f31 = open('./classic2/a2011db.txt','r',encoding='utf-8')
f32 = open('./classic2/a2012db.txt','r',encoding='utf-8')
f33 = open('./classic2/a2013db.txt','r',encoding='utf-8')
f34 = open('./classic2/a2014db.txt','r',encoding='utf-8')
f35 = open('./classic2/a2015db.txt','r',encoding='utf-8')

a_name = open('./a_name.txt','r',encoding='utf-8')
author_name = {}
for line in a_name:
    line = line.strip().split(',')
    author_name.setdefault(line[0], line[1])
a_name.close()
f2015ai = open('./classic3/a2015ai.txt', 'w', encoding='utf-8')
early_au = {}
for line in f15:
    line = line.strip().split(',')
    list2015ai.setdefault(line[0], line[1])
f15.close()
for line in f0:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f0.close()
for line in f1:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f1.close()
for line in f2:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f2.close()
for line in f3:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f3.close()
for line in f4:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f4.close()
for line in f5:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f5.close()
for line in f6:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f6.close()
for line in f7:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f7.close()
for line in f8:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f8.close()
for line in f9:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f9.close()
for line in f10:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f10.close()
for line in f11:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f11.close()
for line in f12:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f12.close()
for line in f13:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f13.close()
for line in f14:
    line = line.strip().split(',')
    if line[0] not in early_au.keys():
        early_au[line[0]] = line[1]
f14.close()
#筛除

for key in list2015ai:
    outline = str(key) + ',' + str(list2015ai[key]) + ',' + author_name[key] + '\n'
    print(outline)
    if key not in early_au.keys():
        f2015ai.write(outline)
f2015ai.close()

