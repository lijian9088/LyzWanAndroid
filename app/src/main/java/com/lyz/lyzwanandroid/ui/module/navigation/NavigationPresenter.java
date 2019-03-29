package com.lyz.lyzwanandroid.ui.module.navigation;

import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.data.source.NetworkManager;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    /**
     * CompositeDisposable在切断后就不能再重用，所以每次都要new一个新的
     */
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void getNavigationData() {
        NetworkManager.getInstance()
                .getNavigation()
                .subscribe(new Observer<BaseResponse<List<Navigation>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<Navigation>> navigationBaseResponse) {
                        Logger.d("navigationBaseResponse:" + navigationBaseResponse);
                        if (navigationBaseResponse.errorCode == 0) {
                            List<Navigation> data = navigationBaseResponse.data;
                            view.setNavigationData(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("nav,onError:" + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
