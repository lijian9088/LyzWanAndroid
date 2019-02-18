package com.lyz.lyzwanandroid.ui.base.mvp;

/**
 * @author liyanze
 * @create 2019/02/15
 * @Describe
 */
public abstract class BasePresenter<T extends IView> implements IPresenter<T> {
    protected T view;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }
}
