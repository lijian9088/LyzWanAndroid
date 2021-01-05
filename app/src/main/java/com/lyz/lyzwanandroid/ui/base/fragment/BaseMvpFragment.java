package com.lyz.lyzwanandroid.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.blankj.utilcode.util.ToastUtils;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.base.mvp.IView;
import com.lyz.lyzwanandroid.ui.module.main.MainFragment;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author liyanze
 */
public abstract class BaseMvpFragment<T extends BasePresenter,V extends ViewBinding> extends BaseSwipeBackFragment implements IView {

    protected T presenter;
    protected V viewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = createViewBinding(inflater, container);
        View view = viewBinding.getRoot();
        if (presenter == null) {
            presenter = createPresenter();
            presenter.attachView(this);
        }
        initView();
        if (canSwipeBack()) {
            return attachToSwipeBack(view);
        }
        return view;
    }

    protected abstract V createViewBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract T createPresenter();

    protected abstract void initView();

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
