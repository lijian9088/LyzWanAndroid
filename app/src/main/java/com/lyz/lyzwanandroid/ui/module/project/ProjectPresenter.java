package com.lyz.lyzwanandroid.ui.module.project;

import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.model.ProjectTitle;
import com.lyz.lyzwanandroid.data.source.NetworkManager;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author liyanze
 * @create 2019/02/15
 * @Describe
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    /**
     * CompositeDisposable在切断后就不能再重用，所以每次都要new一个新的
     */
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void getProjectTitle() {
        NetworkManager.getInstance()
                .getProjectTitle()
                .subscribe(new Observer<BaseResponse<List<ProjectTitle>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<ProjectTitle>> listBaseResponse) {
                        if (listBaseResponse.errorCode == 0) {
                            List<ProjectTitle> data = listBaseResponse.data;
                            view.setTitleData(data);
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
    public void detachView() {
        super.detachView();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
