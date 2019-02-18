package com.lyz.lyzwanandroid.data.source.remote;

import com.lyz.lyzwanandroid.data.model.ArticleList;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.data.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public interface WanAndroidService {

    @GET("/banner/json")
    Observable<BaseResponse<List<Banner>>> requestBanner();

    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticleList>> requestArticle(@Path("page") int page);
}
