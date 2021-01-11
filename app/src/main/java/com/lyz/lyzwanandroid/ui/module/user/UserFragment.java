package com.lyz.lyzwanandroid.ui.module.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.LoginData;
import com.lyz.lyzwanandroid.databinding.FragmentUserBinding;
import com.lyz.lyzwanandroid.databinding.IncludeBtnBinding;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.utils.CleanDataUtils;
import com.lyz.lyzwanandroid.widget.customxpopup.LoginPopup;
import com.orhanobut.logger.Logger;

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
    private BasePopupView loginPopupView;
    private BasePopupView loadingDialog;
    private LoginPopup loginPopup;
    private BasePopupView logoutPopupView;

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

        updateCacheSize();
    }

    private void updateCacheSize() {
        String totalCacheSize = null;
        try {
            totalCacheSize = CleanDataUtils.getTotalCacheSize(getContext());
            btnCache.tv.setText("缓存:" + totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnUser.getRoot())) {
            if (btnUser.tv.getText().toString().contains("已登录")) {
                showLogoutDialog();
            } else {
                showLoginDialog();
            }
        }

        if (v.equals(btnCache.getRoot())) {
            showCleanDataDialog();
        }

        if (v.equals(btnSkin.getRoot())) {
            presenter.changeSkin();
        }
    }

    private void showCleanDataDialog() {
        String size = null;
        try {
            size = CleanDataUtils.getTotalCacheSize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(size)){
            return;
        }
        if(size.contains("0.00")){
            ToastUtils.showShort("无需清理");
            return;
        }
        String content = String.format("是否清理缓存(%s)？",size);
        new XPopup.Builder(getContext())
                    //.dismissOnBackPressed(false)
                    .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                    .autoOpenSoftInput(true)
                    .isRequestFocus(false)
                    .asConfirm("提示", content, new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            presenter.cleanCache(getContext());
                            updateCacheSize();
                        }
                    }).show();
    }

    private void showLogoutDialog() {
        if (logoutPopupView == null) {
            logoutPopupView = new XPopup.Builder(getContext())
                    //.dismissOnBackPressed(false)
//                    .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                    .autoOpenSoftInput(true)
                    .isRequestFocus(false)
                    .asConfirm("提示", "是否退出登录？", new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            btnUser.tv.setText(R.string.login);
                        }
                    });
        }
        logoutPopupView.show();
    }

    @Override
    public void showLoginDialog() {
        if (loginPopup == null) {
            loginPopup = new LoginPopup(getContext());
            loginPopup.setListener(new LoginPopup.OnClickListener() {
                @Override
                public void onCancel() {
                    clearText();
                }

                @Override
                public void onConfirm() {
                    showLoading("登录中...");
                    String username = getEditTextString(loginPopup.getEtUsername());
                    String password = getEditTextString(loginPopup.getEtPassword());
                    Logger.d("username:" + username + ",password:" + password);
                    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                        presenter.login(username, password);
                    } else {
//                    showLoading("登录失败");
                        dismissLoading(0);
                        showToast("用户名或者密码不能为空");
                    }
                    clearText();
                }

                private void clearText() {
                    loginPopup.getEtUsername().setText("");
                    loginPopup.getEtPassword().setText("");
                }
            });
        }
        if (loginPopupView == null) {
            loginPopupView = new XPopup.Builder(getContext())
                    //.dismissOnBackPressed(false)
//                    .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                    .autoOpenSoftInput(true)
//                    .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
//                    .isRequestFocus(false)
                    //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
//                    .autoDismiss(false)
                    .asCustom(loginPopup);
        }
        loginPopupView.show();
    }

    @Override
    public void loginComplete(boolean success, Object object) {
        dismissLoading(0);
        if (object != null) {
            if (object instanceof LoginData) {
                LoginData data = (LoginData) object;
                String username = data.username;
                btnUser.tv.setText(String.format("已登录(%s)", username));
            }

            if (object instanceof String) {
                String message = (String) object;
                showToast("登录失败:" + message);
            }

        } else {
            showToast("登录失败");
        }
    }

    private String getEditTextString(AppCompatEditText editText) {
        if (editText != null && !TextUtils.isEmpty(editText.getText())) {
            return editText.getText().toString().trim();
        }
        return null;
    }

    private void showLoading(String title) {
        loadingDialog = new XPopup.Builder(getContext())
                .asLoading(title)
                .show();
    }

    private void dismissLoading(long delay) {
        if (loadingDialog != null) {
            loadingDialog.delayDismiss(delay);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        updateCacheSize();
    }
}
