package com.example.apple.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class ExternalCallActivity extends AppCompatActivity {

    private WebView webView;
    private static final String url = "file:///android_asset/callApp.html";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_call);

        webView = (WebView) findViewById(R.id.browser);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebViewCromeClient());
        webView.loadUrl(url);
        Log.d(TAG, "onCreate: pid=" + android.os.Process.myPid() + ",taskId=" + getTaskId());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyWebViewCromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(ExternalCallActivity.this, "messsage:" + message, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
