# coding=utf8
import json
import math
import time


def del_no_year_title(ori_path, output_path):
    fp = open(ori_path, 'r', encoding='utf8')
    fp_real = open(output_path, 'w', encoding='utf8')
    num = 0
    while True:
        line = fp.readline()
        # print(line)
        if not line:
            break
        line_dict = eval(line)
        if 'authors' not in line_dict.keys() or 'title' not in line_dict.keys():
            # if not line_dict['authors'] or line_dict['title']=='':
            continue
        # 数据保存
        fp_real.write(line)
        num += 1
    print(num)
    fp.close()
    fp_real.close()


def capture_paper_info(ori_data, output_txt, all_info_path):
    ori_file = open(ori_data, 'r', encoding='utf8')
    # out_file = open(output_txt, 'w', encoding='utf8')
    all_message = open(all_info_path, 'w', encoding='utf8')

    num = 0
    while True:
        if not ori_data:
            break
        line = ori_file.readline()
        # print(line)
        raw_data = line
        line = eval(line)
        outline_index = '#index ' + str(line['id']) + '\n'
        outline_title = '#* ' + line['title'] + '\n'
        authors = line['authors']
        num_authors = len(authors)
        # author_info = ['name', 'org']
        # print(authors)
        authors_list = []
        org_list = []
        for author in authors:
            if 'name' not in author.keys() or 'org' not in author.keys():
                break
            name = author['name']
            org = author['org']
            authors_list.extend([name])
            org_list.extend([org])
        if len(authors_list) != num_authors and len(org_list) != num_authors:
            continue
        # print(authors_list)
        outline_author = '#@ ' + ';'.join(authors_list) + '\n'
        outline_org = '#o ' + ';'.join(org_list) + '\n'
        outline_year = '#t ' + str(line['year']) + '\n'
        if 'venue' not in line.keys() or 'raw' not in line['venue'].keys():
            break
        outline_venue = '#c ' + line['venue']['raw'] + '\n'
        if 'references' not in line.keys():
            # print(line)
            continue
        outline_reference = '#% ' + ';'.join([str(ref) for ref in line['references']]) + '\n'
        # outline_ref = line['references']

        outline = outline_index + outline_title + outline_author + outline_org + outline_year + outline_venue + outline_reference + '\n'
        # out_file.write(outline)
        all_message.write(raw_data)
        num += 1
        print(num)
        # break
    ori_file.close()
    # out_file.close()
    all_message.close()


def get_paper_relations(input_path):
    # 读取文件
    input_file = open(input_path, 'r', encoding='utf8')
    # paper5_file = open('paper5.txt', 'w', encoding='utf8')
    # paper_cition = open('paper-cition.txt', 'w', encoding='utf8')
    # text_info = open('text.txt', 'w', encoding='utf8')
    year_ref_author_title_abstract = open('year_ref_author_title_abstract', 'w', encoding='utf8')
    num = 0
    while True:
        if not input_file.readline():
            break
        line = input_file.readline()
        raw_line = line
        line = eval(line)
        outline_index = '#index ' + str(line['id']) + ';'
        authors = line['authors']
        authors_list = []
        for author in authors:
            name = author['name']
            authors_list.extend([name])
        outline_author = '#@ ' + ';'.join(authors_list) + ';'
        outline_title = '#* ' + line['title'] + ';'
        outline_year = '#t ' + str(line['year']) + ';'
        outline_venue = '#c ' + line['venue']['raw'] + ';'
        paper5_outline = outline_index + outline_author + outline_year + outline_venue + '\n'

        references = line['references']  # 返回的是一个列表
        refer_outline = outline_index + ';'.join(['#% ' + str(i) for i in references]) + '\n'
        # 摘要的复原
        if 'indexed_abstract' not in line.keys():
            continue
        abstract_len = line['indexed_abstract']['IndexLength']
        abstract_content = line['indexed_abstract']['InvertedIndex']
        abstract_list = ['0'] * abstract_len
        for i in abstract_content.keys():
            words = abstract_content[i]
            for word in words:
                abstract_list[word] = i
        abstract_outline = outline_index + outline_title + '#! ' + ' '.join(abstract_list) + '\n'

        # paper5_file.write(paper5_outline)
        # paper_cition.write(refer_outline)
        # text_info.write(abstract_outline)
        year_ref_author_title_abstract.write(raw_line)
        num += 1
        print(num)
        # break
    # paper5_file.close()
    # paper_cition.close()
    # text_info.close()
    year_ref_author_title_abstract.close()


def get_paperlist(input_path):
    data = open(input_path, 'r', encoding='utf8')
    paperlists = open('paperlist.txt', 'w', encoding='utf8')
    paperlist = {}  # 包含每个文章以及每篇文章的被引用的次数以及出版的年份
    while True:
        if not data.readline():
            break
        line = data.readline()
        line = eval(line)
        # paper = {}
        paper_id = line['index']  # 这个paper的id可能出现过，只需要更新year就行
        year = line['year']
        # 更新数据
        if paper_id not in paperlist:
            paperlist.update({paper_id: {'num': 0, 'year': year}})
        else:
            paperlist[paper_id]['year'] = year
        references = line['references']
        for paper in references:
            if paper not in paperlist:
                paperlist.update({paper_id: {'num': 0, 'year': year}})
            else:
                paperlist[paper]['num'] += 1
    # 数据输出
    num_paper = len(paperlist.keys())
    for item, value in paperlist.items():
        id = item
        citation_num = value['num']
        pub_year = value['year']
        outline = 'p' + id + ',' + str(pub_year) + ',' + citation_num + ',' + str(citation_num / num_paper) + '\n'
        paperlists.write(outline)


def data_process(input_path, xiaoyu_2000):
    fp = open(input_path, 'r', encoding='utf8')
    xiaoyu_2000_file = open(xiaoyu_2000, 'r', encoding='utf8')
    data = open('data.txt', 'w', encoding='utf8')
    xiaoyu_2000_list_id = []
    while True:
        if not xiaoyu_2000_file.readline():
            break
        paper_id = xiaoyu_2000_file.readline().strip().split(' ')[0]
        xiaoyu_2000_list_id.append(paper_id)
    num = 0
    while True:
        line = fp.readline()
        # print(line)
        if not line:  # 是不是读到了最后一行
            break
        # raw_line = line
        line = eval(line)
        paper_year = line['year']
        # 删掉没有作者和文章名字的
        if paper_year < 2000:
            continue
        if 'authors' not in line.keys() or 'title' not in line.keys():
            # if not line_dict['authors'] or line_dict['title']=='':
            continue
        authors = line['authors']
        # 过滤掉作者里面没有name和id属性的数据
        for author in authors:
            if 'name' not in author.keys() or 'id' not in author.keys():
                break

        # 过滤掉没有摘要的
        if 'indexed_abstract' not in line.keys():
            continue
        # 过滤掉没有fos的
        if 'fos' not in line.keys():
            continue
            # 没有参考文献的过滤掉
        if 'references' not in line.keys():
            continue
        references = line['references']
        for i in references:
            if i in xiaoyu_2000_list_id:
                references.remove(i)
        line['references'] = references
        # 数据保存
        data.write(str(line) + '\n')
        # data.write(raw_line)
        num += 1
    print(num)
    data.close()


# def get_paperlist():
def read_raw_data(input_path):
    fp = open(input_path, 'r', encoding='utf8')
    fp_w = open('author_relations01.txt', 'w', encoding='utf8')
    fp_xiaoyu2000 = open('xiaoyu_2000.txt', 'w', encoding='utf8')
    years = []
    num = 0
    while fp.readline():
        line = fp.readline()  #

        if line[0] == '[' or line[0] == ']':
            continue
        if line[0] != '{':
            line = line[1:]
        line_dict = eval(line)
        # 删掉没有发表年份的数据
        if 'year' not in line_dict.keys():
            continue
        paper_index = line_dict['id']
        year = line_dict['year']
        # 2000 以下的文章
        if int(year) < 2000:
            outline = str(paper_index) + ' ' + str(year) + '\n'
            fp_xiaoyu2000.write(outline)
            continue
        # if year < 2000:

        fp_w.write(line)
        # break
    print(num)
    fp.close()
    fp_w.close()
    fp_xiaoyu2000.close()


def get_pp_score(input_path, dacay=0.89):
    current_time = time.localtime(time.time()).tm_year
    data = open(input_path, 'r', encoding='utf8')
    #     数据保存
    pp_file = open('p_p.txt', 'w', encoding='utf8')
    #     需要先将
    while True:
        if not data.readline():
            break
        # 读取每条数据并计算权重
        line = data.readline()
        print('line:', line)
        line = eval(line)

        paper_id = line['id']
        references = line['references']
        year = line['year']
        for id in references:
            citition_id = id
            weight_paper_citation = math.exp((-dacay) * (current_time - year))
            outline = 'p' + str(paper_id) + ',' + 'p' + str(citition_id) + ',' + str(weight_paper_citation) + '\n'
            pp_file.write(outline)

    data.close()
    pp_file.close()


def get_aa_score(input_path, dacay=0.89):
    current_time = time.localtime(time.time()).tm_year
    data = open(input_path, 'r', encoding='utf8')
    aa_file = open('a_a.txt', 'w', encoding='utf8')
    a_a_w = {}  # 'p-p:计算的权重相加'
    while True:
        if not data.readline():
            break
        line = data.readline()
        line = eval(line)
        year = line['year']
        authors = line['authors']
        for id, author in enumerate(authors):

            for item in authors[id + 1:]:
                author_id = str(author['id'])
                collbor_id = str(item['id'])
                aa_key = author_id + '-' + collbor_id
                aa_weight = math.exp((-dacay) * (current_time - year))
                # 确定两个作者以前没有合作
                if author_id + '-' + collbor_id not in a_a_w.keys() and collbor_id + '-' + author_id in a_a_w.keys():
                    a_a_w.update({author_id + '-' + collbor_id: aa_weight})
                elif author_id + '-' + collbor_id in a_a_w.keys():  # 如果是有合作的，找到之前存的是a-b还是b-a
                    a_a_w[author_id + '-' + collbor_id] = aa_weight + a_a_w[author_id + '-' + collbor_id]
                else:
                    a_a_w[collbor_id + '-' + author_id] = aa_weight + a_a_w[collbor_id + '-' + author_id]
    # 数据处理完之后
    for key, value in a_a_w:
        author_id_1, collbor_id_1 = key.split('-')[0], key.split('-')[1]
        outline = 'A' + str(author_id_1) + ',' + 'A' + str(collbor_id_1) + ',' + value + '\n'
        aa_file.write(outline)
    data.close()
    aa_file.close()


# paper, author,weight
def get_ap_file(input_path):
    data = open(input_path, 'r', encoding='utf8')
    ap_file = open('a_p.txt', 'w', encoding='utf8')
    #
    while True:
        if not data.readline():
            break
        line = eval(data.readline())
        paper_id = line['id']
        authors = line['authors']
        num_a = len(authors)
        for i in range(num_a):
            author = authors[i]
            author_id = author['id']
            outline = 'p' + paper_id + ',' + author_id + ',' + str(1 / (i + 1)) + '\n'
            ap_file.write(ap_file)
    data.close()
    ap_file.close()


def title_zhaiyao_file(input_path):
    data = open(input_path, 'r', encoding='utf8')
    pw_file = open('title_zhaiyao.txt', 'w', encoding='utf8')
    while True:
        if not data.readline():
            break
        line = eval(data.readline())
        paper_id = line['id']
        title = line['title']
        abstract_len = line['indexed_abstract']['IndexLength']
        abstract_content = line['indexed_abstract']['InvertedIndex']
        abstract_list = ['0'] * abstract_len
        for i in abstract_content.keys():
            words = abstract_content[i]
            for word in words:
                abstract_list[word] = i
        abstract_outline = 'p' + str(paper_id) + title + ';'.join(abstract_list) + '\n'
        pw_file.write(abstract_outline)


def GT_paper(input_path):
    data = open(input_path,'r',encoding='utf-8')
    pgt_file = open('paper_GT.txt','w',encoding='utf-8')
    paper_cit = {}
    while True:
        if not data.readline():
            break
        line = data.readline().strip()
        try:
            line =eval(line)
            id = line['id']
            paper_year = line['year']
            if paper_year <= 2015:
                paper_cit.setdefault(id,[]).append(0)
                paper_cit.setdefault(id,[]).append(paper_year)
                print(paper_cit[id][0],paper_cit[id][1])
        except:
            print(line)
    data.close()
    data = open(input_path, 'r', encoding='utf-8')
    while True:
        if not data.readline():
            break
        line = data.readline().strip()
        try:
            line = eval(line)
            paper_year = line['year']
            references = line['references']
            if paper_year > 2015:
                for id in references:
                    if paper_cit[id][1] <=2015:
                        paper_cit[id][0] = paper_cit[id][0] + 1
        except:
            print(line)
    for key in paper_cit:
        outline = 'p' + str(key) + ',' + str(paper_cit[key]) + '\n'
        print(outline)
        pgt_file.write(outline)
    data.close()

def GT_author(input_path1, input_path2):
    gt_paper = open(input_path1, 'r', encoding='utf-8')
    agt_file = open('author_GT.txt', 'w', encoding='utf-8')
    paper_cit = {}
    author_cit = {}
    while True:
        if not gt_paper.readline():
            break
        line = gt_paper.readline().strip()
        line = line.split(',')
        # print(line)
        #   print(line[0],line[1])
        paper_cit.setdefault(line[0], line[1])
    gt_paper.close()
    data = open(input_path2, 'r', encoding='utf-8')
    while True:
        if not data.readline():
            break
        line = data.readline().strip()
        try:
            line = eval(line)
            authors = line['authors']
            id = line['id']
            pid = 'p' + str(id)
            if pid in paper_cit.keys():
                for i in range(len(authors)):
                    aid = authors[i]['id']
                    if aid in author_cit.keys():
                        author_cit[aid] += int(paper_cit[pid])
                        print(author_cit[aid])
                    elif aid not in author_cit.keys():
                        author_cit[aid] = int(paper_cit[pid])
                        print(author_cit[aid])
        except:
            print(line)
    for key in author_cit:
        outline = 'A' + str(key) + ',' + str(author_cit[key]) + '\n'
        print(outline)
        agt_file.write(outline)
    data.close()

def get_fos(input_path):
    data = open(input_path, 'r', encoding='utf-8')
    fos_file = open('paper_fos.txt', 'w', encoding='utf-8')
    paper_fos = []
    num = 0
    while True:
        if not data.readline():
            break
        line = data.readline().strip()
        try:
            line = eval(line)
            fos = line['fos']
            id = line['id']
            year = line['year']
            if year <= 2015:
                a = [id, year, 0, 0, 0]
                for i in range(len(fos)):
                    if fos[i]['name'].find('Artificial intelligence') != -1 and fos[i]['w'] >= 0.4:   #分类 第一列人工智能 第二列 数据库 第三列其它
                        a[2] = 1
                        print('a1:', fos[i]['name'], fos[i]['w'])
                    elif fos[i]['name'].find('Database') != -1 and fos[i]['w'] >= 0.4:
                        a[3] = 1
                        print('a2:', fos[i]['name'], fos[i]['w'])
                    else:
                        a[4] = 1
                        print('a3:', fos[i]['name'], fos[i]['w'])
                paper_fos.append(a)
                #print(paper_fos[num])
                #num += 1
        except:
            print(line)
    for i in range(len(paper_fos)):
        outline = 'p' + str(paper_fos[i][0]) + ',' + str(paper_fos[i][1]) + ',' + str(paper_fos[i][2]) + ',' + str(
            paper_fos[i][3]) + ',' + str(paper_fos[i][4]) + '\n'
        print(outline)
        fos_file.write(outline)
    data.close()

if __name__ == '__main__':
    # 删除没有年份和题目的文章
    # author_relation = 'author_relations.txt'
    # output_path = './id_author_inference.txt'
    # del_no_year_title(author_relation, output_path)
    # capture_paper_info('./id_author_inference.txt', 'ori_data.txt', 'year_ref_author_title.txt')
    # get_paper_relations('year_ref_author_title.txt')
    # 数据筛选,将年限是2000年之前的paper读取到了
    '''
    分步执行：
    1. 执行 read_raw_data('dblp.v12.json')提取2000年之前的文章
    2. 执行 data_process('author_relations01.txt', 'xiaoyu_2000.txt')筛选数据，去掉参考文献中2000年之前的文章
    '''

    # read_raw_data('dblp.v12.json')
    # data_process('author_relations01.txt', 'xiaoyu_2000.txt')
    # get_pp_score('data.txt')
    # get_aa_score('data.txt')
    # get_ap_file('data.txt')
    # title_zhaiyao_file('data.txt')
    # GT_paper('data.txt')
    # Gt_author('data.txt')
    # get_fos('data.txt')
