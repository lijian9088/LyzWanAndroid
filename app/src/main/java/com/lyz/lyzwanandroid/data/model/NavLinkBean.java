package com.lyz.lyzwanandroid.data.model;


import com.lyz.lyzwanandroid.widget.TagTextView;

/**
 * @author liyanze
 * @create 2019/03/25
 * @Describe
 */
public class NavLinkBean extends TagTextView.AbsTagData {
    public String chapterName;
    public String link;
    public String title;

    @Override
    public String toString() {
        return "NavLinkBean{" +
                "chapterName='" + chapterName + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
