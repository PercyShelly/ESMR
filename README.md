# ESMR
Heterogeneous Academic Network Embedding Based Multivariate Random-Walk Model for

Predicting Scientifific Impact

## Introduce

In this paper, we propose a novel model called ESMR, which adds the learned Embeddings and global Structure information to Multivariate Random-walk, to predict the future scientific influence of papers and authors.

## Step 1：Dataset Preparation
---
We used a total of two datasets, ACL Anthology Network (ANN) and Academic Social Network of AMiner Dataset.  And there are two versions of Aminer Dataset.

You can download the dataset from these place: https://clair.eecs.umich.edu/aan/index.ph

​                                                                              https://www.aminer.cn/aminernetwork

​                                                                              https://originalstatic.aminer.cn/misc/dblp.v12.7z

Enviorment Requirement for data processing :

install nltk,pyyaml,numpy,tqdm,gensim(the latest version)

nltk_data preparing

> import nltk
>
> nltk.download()

At this time, a graphical interface will pop up, and several pieces of data will be displayed for you to download. It is best to choose to download. This process will take a while. After the corpus is downloaded, NLTK is really available on your computer.

## Step 2: Data processing

**Raw data processing**

Remove the citation relationship of papers published after 2015 (the number of citations in the future will only consider the articles to be ranked, that is, those published before 2015)

Remove the cited information after 2015

Remove papers that have no references or are not cited by other articles

Remove papers without a title or abstract

 ```python
 processdata.py
 ```
You can get the weight between author and author,between paper and paper,and between author and paper.

**Document processing**

```
get_wordtopic.py
```

You can get the weight between paper and word,between paper and topic, and between word and topic

**Classify processing**

```
pclassci.py
author_classic.py
find_FirstPub.py
```

If you want to conduct comparative experiments in your own way, you can refer to it.

## Step 3: train and test

**(1) get all network embbeding**

```
PaperAuthorEmbedding.java
```

You can directly set the embedded dimension

**(2)calculate similarity**

```
Similarity.java
```

**(3)get rank results**

```
CoRank.java
```

**(4)testing and evaluation by RI and NDCG**

```
MetricRI.java
MetricNDCG.java
```

