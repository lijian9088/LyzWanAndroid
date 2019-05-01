package com.lyz.lyzwanandroid.ui.module.project.tabpage;

import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/02/19
 * @Describe
 */
public interface ProjectTabPageContract {
    interface View extends IView {
        void showLoading();

        void hideLoading(boolean success);

        void hideLoadMore(boolean success);

        void setItemData(List<WanAndroidData> data);

        void appendItemData(List<WanAndroidData> data);

        void clearData();

    }

    interface Presenter extends IPresenter<ProjectTabPageContract.View> {
        void getProject(int page, int cid);
        void getArticle(int page, int cid);
    }
}
