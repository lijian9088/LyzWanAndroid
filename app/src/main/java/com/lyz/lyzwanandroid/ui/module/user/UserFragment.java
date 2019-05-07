package com.lyz.lyzwanandroid.ui.module.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import skin.support.SkinCompatManager;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class UserFragment extends BaseMvpFragment<UserPresenter> implements UserContract.View, View.OnClickListener {

    @BindView(R.id.btnUser)
    View btnUser;

    @BindView(R.id.btnSetting)
    View btnSetting;

    private BtnViewHolder btnUserHolder;
    private BtnViewHolder btnSettingHolder;

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user;
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    protected void initView(View view) {
        btnUserHolder = new BtnViewHolder(btnUser);
        btnSettingHolder = new BtnViewHolder(btnSetting);
        btnUserHolder.tv.setText(R.string.login);
        btnSettingHolder.tv.setText(R.string.setting);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        btnUser.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUser:
                //后缀加载
                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                break;
            case R.id.btnSetting:
                //后缀加载
                SkinCompatManager.getInstance().restoreDefaultTheme();
                break;
            default:
                break;
        }
    }

    class BtnViewHolder {

        @BindView(R.id.tv)
        TextView tv;

        BtnViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
