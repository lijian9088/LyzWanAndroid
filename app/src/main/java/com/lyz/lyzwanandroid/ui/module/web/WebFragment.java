package com.lyz.lyzwanandroid.ui.module.web;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.widget.MarkdownView;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class WebFragment extends BaseMvpFragment<WebPresenter> implements WebContract.View {

    @BindView(R.id.markdownView)
    MarkdownView markdownView;

    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static WebFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected WebPresenter createPresenter() {
        WebPresenter presenter = new WebPresenter();
        presenter.attachView(this);
        return presenter;

    }

    @Override
    protected void initView(View view) {
        enableSwipeBackLeft(true);
        markdownView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                tv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                tv.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
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
    }
}
