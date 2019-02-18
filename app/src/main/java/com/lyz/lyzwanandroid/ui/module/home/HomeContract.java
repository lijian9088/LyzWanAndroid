package com.lyz.lyzwanandroid.ui.module.home;

import com.lyz.lyzwanandroid.data.model.Article;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/02/15
 * @Describe
 */
public interface HomeContract {
    interface View extends IView {
        void showLoading();

        void hideLoading();

        void setBannerData(List<Banner> data);

        void setItemData(List<Article> data);

        void appendItemData(List<Article> data);

        void clearData();

        void setFooterState(int footerState);

        void onInitAllDataFinish();

    }

    interface Presenter extends IPresenter<HomeContract.View> {

        void getBanner();

        void getArticle(int page);

        void initAllData();
    }
}
