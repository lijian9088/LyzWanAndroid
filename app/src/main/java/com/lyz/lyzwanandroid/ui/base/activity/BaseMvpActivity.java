package com.lyz.lyzwanandroid.ui.base.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import com.blankj.utilcode.util.ToastUtils;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author liyanze
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends SupportActivity implements IView {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
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

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置无动画
        // return new DefaultNoAnimator();
        // 设置自定义动画
        // return new FragmentAnimator(enter,exit,popEnter,popExit);

        // 默认竖向(和安卓5.0以上的动画相同)
//        return super.onCreateFragmentAnimator();
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
//        return super.getDelegate();
        return SkinAppCompatDelegateImpl.get(this, this);
    }
}
