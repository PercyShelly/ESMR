list2000ai = []
list2000db = []
list2001ai = []
list2001db = []
list2002ai = []
list2002db = []
list2003ai = []
list2003db = []
list2004ai = []
list2004db = []
list2005ai = []
list2005db = []
list2006ai = []
list2006db = []
list2007ai = []
list2007db = []
list2008ai = []
list2008db = []
list2009ai = []
list2009db = []
list2010ai = []
list2010db = []
list2011ai = []
list2011db = []
list2012ai = []
list2012db = []
list2013ai = []
list2013db = []
list2014ai = []
list2014db = []
list2015ai = []
list2015db = []
f0 = open('./classic1/2000ai.txt','w',encoding='utf-8')
f1 = open('./classic1/2001ai.txt','w',encoding='utf-8')
f2 = open('./classic1/2002ai.txt','w',encoding='utf-8')
f3 = open('./classic1/2003ai.txt','w',encoding='utf-8')
f4 = open('./classic1/2004ai.txt','w',encoding='utf-8')
f5 = open('./classic1/2005ai.txt','w',encoding='utf-8')
f6 = open('./classic1/2006ai.txt','w',encoding='utf-8')
f7 = open('./classic1/2007ai.txt','w',encoding='utf-8')
f8 = open('./classic1/2008ai.txt','w',encoding='utf-8')
f9 = open('./classic1/2009ai.txt','w',encoding='utf-8')
f10 = open('./classic1/2010ai.txt','w',encoding='utf-8')
f11 = open('./classic1/2011ai.txt','w',encoding='utf-8')
f12 = open('./classic1/2012ai.txt','w',encoding='utf-8')
f13 = open('./classic1/2013ai.txt','w',encoding='utf-8')
f14 = open('./classic1/2014ai.txt','w',encoding='utf-8')
f15 = open('./classic1/2015ai.txt','w',encoding='utf-8')
f20 = open('./classic1/2000db.txt','w',encoding='utf-8')
f21 = open('./classic1/2001db.txt','w',encoding='utf-8')
f22 = open('./classic1/2002db.txt','w',encoding='utf-8')
f23 = open('./classic1/2003db.txt','w',encoding='utf-8')
f24 = open('./classic1/2004db.txt','w',encoding='utf-8')
f25 = open('./classic1/2005db.txt','w',encoding='utf-8')
f26 = open('./classic1/2006db.txt','w',encoding='utf-8')
f27 = open('./classic1/2007db.txt','w',encoding='utf-8')
f28 = open('./classic1/2008db.txt','w',encoding='utf-8')
f29 = open('./classic1/2009db.txt','w',encoding='utf-8')
f30 = open('./classic1/2010db.txt','w',encoding='utf-8')
f31 = open('./classic1/2011db.txt','w',encoding='utf-8')
f32 = open('./classic1/2012db.txt','w',encoding='utf-8')
f33 = open('./classic1/2013db.txt','w',encoding='utf-8')
f34 = open('./classic1/2014db.txt','w',encoding='utf-8')
f35 = open('./classic1/2015db.txt','w',encoding='utf-8')







with open('paper_class.txt', 'r', encoding='utf-8') as f:
    for line in f:
        str1 = line.split(',')
        if int(str1[2]) == 2000 and int(str1[3]) == 1:
            f0.write(line)
        elif int(str1[2]) == 2000 and int(str1[4]) == 1:
            f20.write(line)
        elif int(str1[2]) == 2001 and int(str1[3]) == 1:
            f1.write(line)
        elif int(str1[2]) == 2001 and int(str1[4]) == 1:
            f21.write(line)
        elif int(str1[2]) == 2002 and int(str1[3]) == 1:
            f2.write(line)
        elif int(str1[2]) == 2002 and int(str1[4]) == 1:
            f22.write(line)
        elif int(str1[2]) == 2003 and int(str1[3]) == 1:
            f3.write(line)
        elif int(str1[2]) == 2003 and int(str1[4]) == 1:
            f23.write(line)
        elif int(str1[2]) == 2004 and int(str1[3]) == 1:
            f4.write(line)
        elif int(str1[2]) == 2004 and int(str1[4]) == 1:
            f24.write(line)
        elif int(str1[2]) == 2005 and int(str1[3]) == 1:
            f5.write(line)
        elif int(str1[2]) == 2005 and int(str1[4]) == 1:
            f25.write(line)
        elif int(str1[2]) == 2006 and int(str1[3]) == 1:
            f6.write(line)
        elif int(str1[2]) == 2006 and int(str1[4]) == 1:
            f26.write(line)
        elif int(str1[2]) == 2007 and int(str1[3]) == 1:
            f7.write(line)
        elif int(str1[2]) == 2007 and int(str1[4]) == 1:
            f27.write(line)
        elif int(str1[2]) == 2008 and int(str1[3]) == 1:
            f8.write(line)
        elif int(str1[2]) == 2008 and int(str1[4]) == 1:
            f28.write(line)
        elif int(str1[2]) == 2009 and int(str1[3]) == 1:
            f9.write(line)
        elif int(str1[2]) == 2009 and int(str1[4]) == 1:
            f29.write(line)
        elif int(str1[2]) == 2010 and int(str1[3]) == 1:
            f10.write(line)
        elif int(str1[2]) == 2010 and int(str1[4]) == 1:
            f30.write(line)
        elif int(str1[2]) == 2011 and int(str1[3]) == 1:
            f11.write(line)
        elif int(str1[2]) == 2011 and int(str1[4]) == 1:
            f31.write(line)
        elif int(str1[2]) == 2012 and int(str1[3]) == 1:
            f12.write(line)
        elif int(str1[2]) == 2012 and int(str1[4]) == 1:
            f32.write(line)
        elif int(str1[2]) == 2013 and int(str1[3]) == 1:
            f13.write(line)
        elif int(str1[2]) == 2013 and int(str1[4]) == 1:
            f33.write(line)
        elif int(str1[2]) == 2014 and int(str1[3]) == 1:
            f14.write(line)
        elif int(str1[2]) == 2014 and int(str1[4]) == 1:
            f34.write(line)
        elif int(str1[2]) == 2015 and int(str1[3]) == 1:
            f15.write(line)
        elif int(str1[2]) == 2015 and int(str1[4]) == 1:
            f35.write(line)
f0.close()
f1.close()
f2.close()
f3.close()
f4.close()
f5.close()
f6.close()
f7.close()
f8.close()
f9.close()
f10.close()
f11.close()
f12.close()
f13.close()
f14.close()
f15.close()
f20.close()
f21.close()
f22.close()
f23.close()
f24.close()
f25.close()
f26.close()
f27.close()
f28.close()
f29.close()
f30.close()
f31.close()
f32.close()
f33.close()
f34.close()
f35.close()
