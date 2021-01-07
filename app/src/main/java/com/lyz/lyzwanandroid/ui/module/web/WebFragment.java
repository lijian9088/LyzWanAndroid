package com.lyz.lyzwanandroid.ui.module.web;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lyz.lyzwanandroid.databinding.ActivityWebBinding;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.widget.MarkdownView;
import com.orhanobut.logger.Logger;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class WebFragment extends BaseMvpFragment<WebPresenter, ActivityWebBinding> implements WebContract.View {

    MarkdownView markdownView;
    ProgressBar progressBar;

    public static WebFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ActivityWebBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return ActivityWebBinding.inflate(inflater, container, false);
    }

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter();
    }

    @Override
    protected void initView() {
        markdownView = viewBinding.markdownView;
        progressBar = viewBinding.progressBar;

        markdownView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (markdownView == null) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (markdownView == null) {
                    Logger.d("markdownView为空");
                    return;
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected boolean canSwipeBack() {
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String url = arguments.getString("url");
        presenter.loadUrl(url);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadUrl(String url) {
        markdownView.loadUrl(url);
    }

    @Override
    public void onDestroy() {
        //销毁Webview
        if (markdownView != null) {
            markdownView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            markdownView.clearHistory();
            ((ViewGroup) markdownView.getParent()).removeView(markdownView);
            markdownView.destroy();
            markdownView = null;
        }
        super.onDestroy();
        Logger.d("web.onDestroy");
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
//        Bundle arguments = getArguments();
//        String url = arguments.getString("url");
//        presenter.loadUrl(url);
    }
}
