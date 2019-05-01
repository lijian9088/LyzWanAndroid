package com.lyz.lyzwanandroid.ui.module.main;

import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

/**
 * @author liyanze
 * @create 2019/04/28
 * @Describe
 */
public interface MainContract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<MainContract.View> {

    }
}
