# ESMR
Heterogeneous Academic Network Embedding Based Multivariate Random-Walk Model for

Predicting Scientifific Impact

## Introduce

In this paper, we propose a novel model called ESMR, which adds the learned Embeddings and global Structure information to Multivariate Random-walk, to predict the future scientific influence of papers and authors.

## Step 1：Dataset Preparation
---
We used a total of two datasets, ACL Anthology Network (AAN) and Academic Social Network of AMiner Dataset.  And there are two versions of Aminer Dataset.

You can download the dataset from these place: https://clair.eecs.umich.edu/aan  (AAN)

​                                                                              https://www.aminer.cn/aminernetwork (Aminer-2014)

​                                                                              https://originalstatic.aminer.cn/misc/dblp.v12.7z (Aminer-2020)

Enviorment Requirement for data processing :

install nltk,pyyaml,numpy,tqdm,gensim(the latest version)

nltk_data preparing

> import nltk
>
> nltk.download()

At this time, a graphical interface will pop up, and several pieces of data will be displayed for you to download. It is best to choose to download. This process will take a while. After the corpus is downloaded, NLTK is really available on your computer.

## Step 2: Data processing

**Raw data processing**

Remove papers that have no references or are not cited by other articles

Remove papers without a title or abstract

In Aminer-2020, We removed the citation relationship of papers published after 2015. 
In Aminer-2014 and AAN, we removed the citation relationship of papers published after 2009.

 ```python
 processdata.py
 ```
You can get the weight between authors and authors,between papers and papers,and between authors and papers.

**Document processing**

```
get_wordtopic.py
```

You can get the weight between papers and words,between papers and topics, and between words and topics

**Classify processing**

```
pclassci.py
author_classic.py
find_FirstPub.py
```

If you want to conduct comparative experiments in your own way, you can refer to it.

## Step 3: training and testing

**(1)get embedding of all networks**

```
PaperAuthorEmbedding.java
```

You can directly set the dimension of embeddings

**(2)calculate similarity**

```
Similarity.java
```

**(3)get ranking results**

```
CoRank.java
```

**(4)test and evaluation by RI and NDCG**

```
MetricRI.java
MetricNDCG.java
```

