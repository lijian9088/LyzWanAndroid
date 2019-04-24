package com.lyz.lyzwanandroid.ui.module.tree;

import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.ui.base.mvp.IPresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/04/16
 * @Describe
 */
public class TreeContract {

    interface View extends IView {
        void setTreeData(List<TreeData> data);

        void showLoading();

        void hideLoading();

        void clearData();
    }

    interface Presenter extends IPresenter<TreeContract.View> {
        void getTreeData();
    }
}
