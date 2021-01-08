package com.lyz.lyzwanandroid.ui.module.user;

import android.text.TextUtils;

import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;

import skin.support.SkinCompatManager;
import skin.support.utils.SkinPreference;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {


    @Override
    public void login(String username, String password) {

    }

    @Override
    public void cleanCache() {

    }

    @Override
    public void changeSkin() {
        String skinName = SkinPreference.getInstance().getSkinName();
        if(!TextUtils.isEmpty(skinName) && "night".equals(skinName)){
            //恢复
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }else{
            //后缀加载
            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
        }
    }
}
