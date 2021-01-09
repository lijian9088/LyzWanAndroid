package com.lyz.lyzwanandroid.ui.module.user;

import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.model.LoginData;
import com.lyz.lyzwanandroid.data.source.NetworkManager;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import skin.support.SkinCompatManager;
import skin.support.utils.SkinPreference;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {

    /**
     * CompositeDisposable在切断后就不能再重用，所以每次都要new一个新的
     */
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void login(String username, String password) {
        NetworkManager.getInstance()
                .login(username, password)
                .subscribe(new Observer<BaseResponse<LoginData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<LoginData> response) {
                        Logger.d("login,onNext:" + response);
                        if (response.errorCode == 0) {
                            view.loginComplete(true, response.data);
                        } else {
                            view.loginComplete(false, response.errorMsg);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.loginComplete(false, null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void cleanCache() {

    }

    @Override
    public void changeSkin() {
        String skinName = SkinPreference.getInstance().getSkinName();
        if (!TextUtils.isEmpty(skinName) && "night".equals(skinName)) {
            //恢复
            SkinCompatManager.getInstance().restoreDefaultTheme();
        } else {
            //后缀加载
            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
