package com.lyz.lyzwanandroid.ui.module.project;

import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/02/15
 * @Describe
 */
public interface ProjectContract {
    interface View extends IView {

        void setTreeTitleData(List<TreeData> data);

    }

    interface Presenter extends IPresenter<ProjectContract.View> {

        void getProjectTitle();

    }
}
