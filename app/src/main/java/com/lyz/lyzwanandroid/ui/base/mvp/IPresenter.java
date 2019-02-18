package com.lyz.lyzwanandroid.ui.base.mvp;

/**
 * @author liyanze
 * @create 2019/02/13
 * @Describe
 */
public interface IPresenter<T extends IView> {

    void attachView(T view);

    void detachView();

    boolean isViewAttached();
}
