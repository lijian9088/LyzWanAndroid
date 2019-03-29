package com.lyz.lyzwanandroid.ui.module.web;

import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;

/**
 * @author liyanze
 * @create 2019/03/29
 * @Describe
 */
public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {
    @Override
    public void loadUrl(String url) {
        view.loadUrl(url);
    }
}
