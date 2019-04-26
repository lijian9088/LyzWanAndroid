package com.lyz.lyzwanandroid.ui.module.home;

import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.data.model.ArticleList;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.source.NetworkManager;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author liyanze
 * @create 2019/02/15
 * @Describe
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    /**
     * CompositeDisposable在切断后就不能再重用，所以每次都要new一个新的
     */
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void getBanner() {
        NetworkManager.getInstance()
                .getBanner()
                .subscribe(new Observer<BaseResponse<List<Banner>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<Banner>> listBaseResponse) {
                        if (listBaseResponse.errorCode == 0) {
                            List<Banner> data = listBaseResponse.data;
                            view.setBannerData(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getArticle(int page) {
        NetworkManager.getInstance()
                .getArticle(page)
                .subscribe(new Observer<BaseResponse<ArticleList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<ArticleList> articleListBaseResponse) {
                        boolean isSuccess = articleListBaseResponse.errorCode == 0;
                        if (isSuccess) {
                            List<WanAndroidData> datas = articleListBaseResponse.data.datas;

                            if (page == 0) {
                                view.setItemData(datas);
                            } else {
                                view.appendItemData(datas);
                            }

                        }

                        if (page == 0) {
                            view.hideLoading(isSuccess);
                        } else {
                            view.hideLoadMore(isSuccess);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (page == 0) {
                            view.hideLoading(false);
                        } else {
                            view.hideLoadMore(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initAllData() {
        Observable.concat(
                NetworkManager.getInstance().getBanner(),
                NetworkManager.getInstance().getArticle(0))
                .subscribe(new Observer<BaseResponse<? extends Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<?> baseResponse) {
                        Logger.d("onNext");
                        boolean isSuccess = baseResponse.errorCode == 0;
                        if (isSuccess) {
                            if (baseResponse.data instanceof List) {
                                view.setBannerData((List<Banner>) baseResponse.data);
                            } else if (baseResponse.data instanceof ArticleList) {
                                List<WanAndroidData> datas = ((ArticleList) baseResponse.data).datas;
                                view.setItemData(datas);
                            }

                        }

                        view.hideLoading(isSuccess);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError");
                        e.printStackTrace();
                        view.hideLoading(false);
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
