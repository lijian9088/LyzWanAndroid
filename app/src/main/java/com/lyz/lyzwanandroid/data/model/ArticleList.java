package com.lyz.lyzwanandroid.data.model;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public class ArticleList {

    public int curPage;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
    public List<Article> datas;

}
