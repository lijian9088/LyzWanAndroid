package com.lyz.lyzwanandroid.data.model;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public class Banner extends SimpleBannerInfo {

    /**
     * desc : 一起来做个App吧
     * id : 10
     * imagePath : http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png
     * isVisible : 1
     * order : 3
     * title : 一起来做个App吧
     * type : 0
     * url : http://www.wanandroid.com/blog/show/2
     */

    public String desc;
    public int id;
    public String imagePath;
    public int isVisible;
    public int order;
    public String title;
    public int type;
    public String url;

    @Override
    public String getXBannerUrl() {
        return imagePath;
    }

    @Override
    public String getXBannerTitle() {
        return title;
    }
}
