package com.netproject.web;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.just.agentweb.IWebLayout;
import com.netproject.R;

public class WebLayout implements IWebLayout {

    private WebView mWebView;
    private LinearLayout webViewLl;

    public WebLayout(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.activity_web_view, null);
        webViewLl = view.findViewById(R.id.web_view_ll);
        mWebView = view.findViewById(R.id.webView);
    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return webViewLl;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }


}
