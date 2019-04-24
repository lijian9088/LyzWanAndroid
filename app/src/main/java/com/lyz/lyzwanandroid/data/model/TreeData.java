package com.lyz.lyzwanandroid.data.model;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/04/16
 * @Describe
 */
public class TreeData {

    public int courseId;
    public int id;
    public String name;
    public int order;
    public int parentChapterId;
    public boolean userControlSetTop;
    public int visible;
    public List<TreeData> children;
}
