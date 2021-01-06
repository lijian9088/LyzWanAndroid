package com.lyz.lyzwanandroid.ui.module.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.databinding.FragmentUserBinding;
import com.lyz.lyzwanandroid.databinding.IncludeBtnBinding;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;

import skin.support.SkinCompatManager;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class UserFragment extends BaseMvpFragment<UserPresenter, FragmentUserBinding> implements UserContract.View, View.OnClickListener {

    private IncludeBtnBinding btnUser;
    private IncludeBtnBinding btnSetting;

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FragmentUserBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentUserBinding.inflate(inflater, container, false);
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    protected void initView() {
        btnUser = viewBinding.btnUser;
        btnSetting = viewBinding.btnSetting;

        btnUser.tv.setText(R.string.login);
        btnSetting.tv.setText(R.string.setting);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        btnUser.getRoot().setOnClickListener(this);
        btnSetting.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnUser.getRoot())) {
            //后缀加载
            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
        }

        if (v.equals(btnSetting.getRoot())) {
            //恢复
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
    }

}
