package com.lyz.lyzwanandroid.ui.module.web;

import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

/**
 * @author liyanze
 * @create 2019/03/29
 * @Describe
 */
public interface WebContract {

    interface View extends IView {
        void showLoading();

        void hideLoading();

        void loadUrl(String url);
    }

    interface Presenter extends IPresenter<View> {
        void loadUrl(String url);
    }

}
