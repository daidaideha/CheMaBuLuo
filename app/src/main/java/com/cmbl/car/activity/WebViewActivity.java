package com.cmbl.car.activity;

import android.os.Bundle;
import android.os.Message;
import android.webkit.WebView;

import com.cmbl.car.R;
import com.cmbl.car.base.BaseActivity;

/**
 * Created by Administrator on 2015/7/17 0017.
 */
public class WebViewActivity extends BaseActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContextView(R.layout.webview_activity);
    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected void initBodyer() {
        mWebView = (WebView) findViewById(R.id.webview);
    }

    @Override
    protected void CallBackListener(Message msg) {

    }
}
