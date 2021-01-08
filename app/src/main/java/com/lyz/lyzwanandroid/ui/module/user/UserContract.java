package com.lyz.lyzwanandroid.ui.module.user;

import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public interface UserContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<UserContract.View> {
        void login(String username, String password);

        void cleanCache();

        void changeSkin();
    }
}
