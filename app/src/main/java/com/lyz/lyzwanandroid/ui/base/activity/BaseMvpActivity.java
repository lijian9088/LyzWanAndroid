package com.lyz.lyzwanandroid.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import butterknife.ButterKnife;

/**
 * @author liyanze
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseSwipeBackActivity implements IView {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initPresenter();
        initView();
        initData();
    }

    protected abstract int getLayoutRes();

    private void initPresenter() {
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showToast(String format, Object... args) {
        ToastUtils.showShort(format, args);
    }
}