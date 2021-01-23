import nltk
import numpy
from nltk.corpus import brown
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem.lancaster import LancasterStemmer
from gensim import corpora, models, similarities
from tqdm import tqdm
import logging

logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

def get_wt(input_path):
    documents = []
    paper_id = []
    #num = 10000
    with open(input_path, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.split(';')
            # print('id:'+line[0], 'co:'+line[1].strip())
            paper_id.append(line[0])
            documents.append(line[1])
    f.close()
   
    # texts_tokennized = []  # 单词最小化及分离标点
    print('单词最小化及分离标点')
    for index, document in enumerate(documents):
        words = []
        for word in word_tokenize(document):
            words.append(word.lower())
        # texts_tokennized.append(words)
        documents[index] = words
    # print(texts_tokennized[0])
    print('去停用词')
    english_stopwords = stopwords.words('english')  # 去停用词
    # print(english_stopwords)
    texts_filtered_stopwords = []
    for index, document in enumerate(documents):
        words = []
        for word in document:
            if word not in english_stopwords:
                words.append(word)
        documents[index] = words
    # print(texts_filtered_stopwords[0])
    # texts_filtered = []  # 去标点
    print('去标点')
    english_punctuations = [',', '.', ':', ';', '?', '(', ')', '[', ']', '&', '!', '*', '@', '#', '$', '%', '\'', '{',
                            '}']
    for index, document in enumerate(documents):
        words = []
        for word in document:
            if word not in english_punctuations:
                if word.find('.') >= 0:
                    word = word.replace('.', '')  # 经过上面的步骤似乎并没有去掉
                elif word.find(',') >= 0:
                    word = word.replace(',', '')
                words.append(word)
        documents[index] = words
    # print(texts_filtered[0])
    texts_stemmed = []  # 英语单词干化
    print('英语单词干化')
    st = LancasterStemmer()
    for index, document in enumerate(documents):
        words = []
        for word in document:
            words.append(st.stem(word))
        documents[index] = words
    print('去低频词')
    all_stems = sum(documents, [])  # 去掉整个语料库出现次数为1的低频词
    stems_once = []
    num = 0
    for stem in tqdm(set(all_stems)):
        if all_stems.count(stem) == 1:
            stems_once.append(stem)
            print('第一步'+str(num)+'次\n')
            num += 1
    texts = []
    num = 0
    for index, text in tqdm(enumerate(documents)):
        stems = []
        for stem in text:
            if stem not in stems_once:
                stems.append(stem)
                print('第二步' + str(num) + '次\n')
                num += 1
        documents[index] = stems
        # print(texts)

    dictionary = corpora.Dictionary(documents)
    print(dictionary)
    wdic = dictionary.token2id

    with open('wlist.txt', 'w', encoding='utf-8') as f:  #生成wlist.txt
        for k, v in wdic.items():
            outline = 'w' + str(v) + ',' + k + '\n'
            f.write(outline)
    # f.close()

    corpus = []
    for text in documents:
        corpus.append(dictionary.doc2bow(text))
    # print(corpus[0])
    # TF-IDF
    tfidf = models.TfidfModel(corpus)
    corpus_tfidf = tfidf[corpus]
    pnum = 0
    with open('p-w.txt', 'w', encoding='utf-8') as f:      #生成p-w.txt
        for doc in corpus_tfidf:
            for tu in doc:
                outline = paper_id[pnum] + ',' + 'w' + str(tu[0]) + ',' + str(tu[1]) + '\n'
                f.write(outline)
            pnum += 1
    # f.close()

    lda = models.LdaModel(corpus_tfidf, id2word=dictionary, num_topics=100)  # 调整主题数

    print(lda.num_terms, lda.num_topics, lda.num_updates)

    wn = 0
    with open('w-t.txt', 'w', encoding='utf-8') as f:  # 生成w-t.txt
        for v in wdic.values():
            tw = lda.get_term_topics(int(v), minimum_probability=0.0005)  # 调整概率(w-t)
            if len(tw) > 0:
                print(tw)
                for tu in tw:
                    outline = 'w' + str(wn) + ',' + 't' + str(tu[0]) + ',' + str(tu[1]) + '\n'
                    f.write(outline)
            wn += 1
    # f.close()

    topic = lda.get_document_topics(corpus, minimum_probability=0.1, per_word_topics=False)  # 调整概率(p-t)
    num = 0
    with open('p-t.txt', 'w', encoding='utf-8') as f:  # 生成p-t.txt
        for pt in topic:
            if len(pt) > 0:
                for t in pt:
                    outline = paper_id[num] + ',' + 't' + str(t[0]) + ',' + str(t[1]) + '\n'
                    f.write(outline)
            num += 1
    # f.close()


if __name__ == '__main__':
    get_wt('title_zhaiyao.txt')
