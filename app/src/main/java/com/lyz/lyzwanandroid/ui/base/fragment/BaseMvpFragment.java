package com.lyz.lyzwanandroid.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author liyanze
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseSwipeBackFragment implements IView {

    protected T presenter;

    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        bind = ButterKnife.bind(this, view);
        if (presenter == null) {
            presenter = createPresenter();
        }
        initView(view);
        initData(savedInstanceState);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //一行代码解决fragment重叠问题
        outState.putParcelable("android:support:fragments", null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    protected abstract int getLayout();

    protected abstract T createPresenter();

    protected abstract void initView(View view);

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showToast(String format, Object... args) {
        ToastUtils.showShort(format, args);
    }
}
