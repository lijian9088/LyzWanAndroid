package com.lyz.lyzwanandroid.ui.module.web;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.ui.base.BaseAppCompatActivity;
import com.lyz.lyzwanandroid.widget.MarkdownView;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/03/29
 * @Describe
 */
public class WebActivity extends BaseAppCompatActivity<WebPresenter> implements WebContract.View {

    @BindView(R.id.markdownView)
    MarkdownView markdownView;

    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static void goActivity(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
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
//        markdownView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                tv.setText(String.valueOf(newProgress + "%"));
//            }
//        });
//
//        //声明WebSettings子类
//        WebSettings webSettings = markdownView.getSettings();
//
//        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
//        webSettings.setJavaScriptEnabled(true);
//        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
//        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
//
//        //设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
//        //缩放操作
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
//        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        presenter.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
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

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_web;
    }

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter();
    }

    /**
     * 点击返回上一页面而不是退出浏览器
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && markdownView.canGoBack()) {
            markdownView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
