package com.lyz.lyzwanandroid.data.source.remote;

import com.lyz.lyzwanandroid.data.model.ArticleList;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.data.model.BaseResponse;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.data.model.ProjectTitle;
import com.lyz.lyzwanandroid.data.model.TreeData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public interface WanAndroidService {

    @GET("banner/json")
    Observable<BaseResponse<List<Banner>>> requestBanner();

    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticleList>> requestArticleList(@Path("page") int page);

    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticleList>> requestArticleListWithCid(@Path("page") int page, @Query("cid") int cid);

    @GET("project/tree/json")
    Observable<BaseResponse<List<TreeData>>> requestProjectDatas();

    @GET("project/list/{page}/json")
    Observable<BaseResponse<ArticleList>> requestProjectArticleWithCid(@Path("page") int page, @Query("cid") int cid);

    @GET("navi/json")
    Observable<BaseResponse<List<Navigation>>> requestNavigation();

    @GET("tree/json")
    Observable<BaseResponse<List<TreeData>>> requestTree();
}
