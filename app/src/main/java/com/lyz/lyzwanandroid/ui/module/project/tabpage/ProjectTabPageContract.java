package com.lyz.lyzwanandroid.ui.module.project.tabpage;

import com.lyz.lyzwanandroid.data.model.Project;
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

        void hideLoading();

        void setItemData(List<Project> data);

        void appendItemData(List<Project> data);

        void clearData();

        void setFooterState(int footerState);
    }

    interface Presenter extends IPresenter<ProjectTabPageContract.View> {
        void getProject(int page, int cid);
    }
}
