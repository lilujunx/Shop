package com.admin.exe.shop.ui.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.bumptech.glide.Glide;

public class WebViewActivity extends SCBaseActivity {
    private WebView mWeb;
    private WebSettings mSettings;


    @Override
    public int initRootLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initViews() {
        int position = getIntent().getIntExtra("position", 0);
       
        mWeb = (WebView) findViewById(R.id.web);
        mSettings = mWeb.getSettings();

        mWeb.loadUrl(Constant.URLS[position]);
        //设置在当前APP打卡url指向的网页
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                mWeb.loadUrl(url);
                return true;
            }
        });
        mWeb.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);


            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
//                System.out.println("icon  "+icon);

            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                super.onReceivedTouchIconUrl(view, url, precomposed);
//                System.out.println("url  "+url);


            }
        });

        mSettings.setJavaScriptEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        WebSettings.LOAD_CACHE_ONLY
//        WebSettings.LOAD_NO_CACHE
//        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        mSettings.setLoadsImagesAutomatically();
//        mSettings.setSupportZoom();
//        mSettings.setDisplayZoomControls();

        //当有表单需要处理时记得写上这2句
        mWeb.requestFocus();
        mWeb.requestFocusFromTouch();
    }

    @Override
    public void onBackPressed() {
        if (mWeb.canGoBack()){
            mWeb.goBack();
            //后退，前进后标题栏更新
            WebBackForwardList mWebBackForwardList = mWeb.copyBackForwardList();

            return;
        }

        super.onBackPressed();
    }

    @Override
    public void initDatas() {
        setTitleCenter("品牌推荐");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
    }

    @Override
    public void initOthers() {

    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }
}
