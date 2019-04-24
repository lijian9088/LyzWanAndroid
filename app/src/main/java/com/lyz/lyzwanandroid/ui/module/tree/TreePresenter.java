package com.lyz.lyzwanandroid.ui.module.tree;

import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.data.source.NetworkManager;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author liyanze
 * @create 2019/04/16
 * @Describe
 */
public class TreePresenter extends BasePresenter<TreeContract.View> implements TreeContract.Presenter {

    /**
     * CompositeDisposable在切断后就不能再重用，所以每次都要new一个新的
     */
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void getTreeData() {
        NetworkManager.getInstance()
                .getTree()
                .subscribe(new Observer<BaseResponse<List<TreeData>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<TreeData>> treeBaseResponse) {
                        Logger.d("treeBaseResponse:" + treeBaseResponse);
                        if (treeBaseResponse.errorCode == 0) {
                            List<TreeData> data = treeBaseResponse.data;
                            view.setTreeData(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("tree,onError:" + e.getMessage());
                        view.setTreeData(null);
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
