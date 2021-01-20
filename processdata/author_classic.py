f1 = open('./classic1/2015db.txt','r',encoding='utf-8')
f3 = open('./classic2/a2015db.txt','w',encoding='utf-8')
paper_cit = {}
author_cit = {}
for line in f1:
    line = line.strip().split(',')
    paper_cit.setdefault(line[0], line[1])
f1.close()
with open('./paper_list.txt','r',encoding='utf-8')as f:
    for line in f:
        line = line.strip().split(',')
        pid = line[0]
        if pid in paper_cit.keys():
            i = 2
            while i < len(line):
                aid = line[i]
                print(aid+'\n')
                if aid in author_cit.keys():
                    author_cit[aid] += int(paper_cit[pid])
                    print(author_cit[aid])
                elif aid not in author_cit.keys():
                    author_cit[aid] = int(paper_cit[pid])
                    print(author_cit[aid])
                i += 1
for key in author_cit:
    outline = str(key) + ',' + str(author_cit[key]) + '\n'
    print(outline)
    f3.write(outline)