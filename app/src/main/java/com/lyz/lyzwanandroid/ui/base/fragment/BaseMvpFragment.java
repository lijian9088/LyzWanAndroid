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
import com.lyz.lyzwanandroid.ui.module.main.MainFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

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
            presenter.attachView(this);
        }
        initView(view);
        if (canSwipeBack()) {
            return attachToSwipeBack(view);
        }
        return view;
    }

    protected abstract int getLayout();

    protected abstract T createPresenter();

    protected abstract void initView(View view);

    protected boolean canSwipeBack() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract void initData(Bundle savedInstanceState);

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

    public void startViaParent(ISupportFragment toFragment) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null) {
            ((SupportFragment) parentFragment).start(toFragment);
        }else if(this instanceof MainFragment){
            this.start(toFragment);
        }
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
