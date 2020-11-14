package com.netproject.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.netproject.BaseActivity;
import com.netproject.MainActivity;
import com.netproject.MainPresenter;
import com.netproject.R;
import com.netproject.repository.common.NetConstants;

import org.jetbrains.annotations.NotNull;

public class WebActivity extends BaseActivity<MainPresenter> {
    private LinearLayout webLl;
    private AgentWeb agentWeb;
        String url = NetConstants.BASE_URL;

    @Override
    protected void initThings() {
        webLl = findViewById(R.id.web_ll);
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webLl, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @NotNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_web;
    }

    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().contains("automatic.html")) {
                startActivityForResult(new Intent(context, MainActivity.class), 1111);
                agentWeb.getUrlLoader().stopLoading();
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            if (resultCode == 1112) {
                agentWeb.getUrlLoader().loadUrl(data.getStringExtra("url"));
            }
        }
    }

    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    @Override
    public void initListeners() {
    }
}
