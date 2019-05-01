package com.lyz.lyzwanandroid.data.source;

import com.lyz.lyzwanandroid.BuildConfig;
import com.lyz.lyzwanandroid.data.model.ArticleList;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.data.model.ProjectTitle;
import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.data.source.remote.WanAndroidService;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public class NetworkManager {

    private static final NetworkManager ourInstance = new NetworkManager();
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private WanAndroidService service;

    private NetworkManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.t("NetworkManager")
                        .w(String.format("okHttp: %s", message));
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(WanAndroidService.class);
    }

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    /**
     * 获取banner数据
     *
     * @return
     */
    public Observable<BaseResponse<List<Banner>>> getBanner() {
        return service.requestBanner()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取page页的文章列表
     *
     * @param page
     * @return
     */
    public Observable<BaseResponse<ArticleList>> getArticle(int page) {
        return service.requestArticleList(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取project的标题
     *
     * @return
     */
    public Observable<BaseResponse<List<ProjectTitle>>> getProjectTitle() {
        return service.requestProjectTitle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取page页的project列表
     *
     * @param page
     * @return
     */
    public Observable<BaseResponse<ArticleList>> getArticleWithCidList(int page, int cid) {
        return service.requestArticleWithCidList(page, cid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取navigation数据
     *
     * @return
     */
    public Observable<BaseResponse<List<Navigation>>> getNavigation() {
        return service.requestNavigation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取navigation数据
     *
     * @return
     */
    public Observable<BaseResponse<List<TreeData>>> getTree() {
        return service.requestTree()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
