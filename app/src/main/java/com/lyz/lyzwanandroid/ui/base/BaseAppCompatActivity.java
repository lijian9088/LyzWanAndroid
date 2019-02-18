package com.lyz.lyzwanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import butterknife.ButterKnife;

public abstract class BaseAppCompatActivity<T extends BasePresenter> extends AppCompatActivity implements IView {

    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    protected abstract int getLayoutRes();

    private void initPresenter() {
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    protected abstract T createPresenter();

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showToast(String format, Object... args) {
        ToastUtils.showShort(format, args);
    }
}
