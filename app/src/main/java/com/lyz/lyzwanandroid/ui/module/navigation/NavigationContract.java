package com.lyz.lyzwanandroid.ui.module.navigation;

import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public interface NavigationContract {

    interface View extends IView {
        void setNavigationData(List<Navigation> data);

        void showLoading();

        void hideLoading();

        void clearData();
    }

    interface Presenter extends IPresenter<NavigationContract.View> {
        void getNavigationData();
    }
}
