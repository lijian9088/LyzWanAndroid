package com.lyz.lyzwanandroid.ui.module.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.databinding.FragmentUserBinding;
import com.lyz.lyzwanandroid.databinding.IncludeBtnBinding;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.widget.customxpopup.LoginPopup;
import com.orhanobut.logger.Logger;

import skin.support.SkinCompatManager;
import skin.support.utils.SkinPreference;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class UserFragment extends BaseMvpFragment<UserPresenter, FragmentUserBinding> implements UserContract.View, View.OnClickListener {

    private IncludeBtnBinding btnUser;
    private IncludeBtnBinding btnCache;
    private IncludeBtnBinding btnSkin;

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
        btnCache = viewBinding.btnCache;
        btnSkin = viewBinding.btnSkin;

        btnUser.tv.setText(R.string.login);
        btnCache.tv.setText(R.string.cache);
        btnSkin.tv.setText(R.string.skin);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        btnUser.getRoot().setOnClickListener(this);
        btnCache.getRoot().setOnClickListener(this);
        btnSkin.getRoot().setOnClickListener(this);

        String skinName = SkinPreference.getInstance().getSkinName();
        Logger.d("skinName:" + skinName);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnUser.getRoot())) {
            showLoginDialog();
            //登录
//            presenter.login();
        }

        if (v.equals(btnCache.getRoot())) {
            presenter.cleanCache();
        }

        if (v.equals(btnSkin.getRoot())) {
            presenter.changeSkin();
        }
    }

    private void showLoginDialog(){
        new XPopup.Builder(getContext())
                //.dismissOnBackPressed(false)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .autoOpenSoftInput(true)
//                        .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
                .isRequestFocus(false)
                //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
                .asCustom(new LoginPopup(getContext()))
                .show();
    }

}
