package com.lyz.lyzwanandroid.ui.module.project.tabpage;

import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.model.Project;
import com.lyz.lyzwanandroid.data.model.ProjectList;
import com.lyz.lyzwanandroid.data.source.NetworkManager;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author liyanze
 * @create 2019/02/19
 * @Describe
 */
public class ProjectTabPagePresenter extends BasePresenter<ProjectTabPageContract.View> implements ProjectTabPageContract.Presenter {

    /**
     * CompositeDisposable在切断后就不能再重用，所以每次都要new一个新的
     */
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void getProject(int page, int cid) {
        NetworkManager.getInstance()
                .getProjectList(page, cid)
                .subscribe(new Observer<BaseResponse<ProjectList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<ProjectList> projectListBaseResponse) {
                        if (projectListBaseResponse.errorCode == 0) {
                            ProjectList projectList = projectListBaseResponse.data;
                            List<Project> datas = projectList.datas;
                            if (page > 1) {
                                view.appendItemData(datas);
                            } else {
                                view.setItemData(datas);
                            }
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

