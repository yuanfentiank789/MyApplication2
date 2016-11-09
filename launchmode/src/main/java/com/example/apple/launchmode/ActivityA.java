package com.example.apple.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import model.HomeListener;

public class ActivityA extends BaseActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void doAction(View v) {
        startActA();
    }

    @Override
    public void doAction2(View v) {
        startActB();
        finish();
    }

    @Override
    public void doAction3(View v) {
        startActC();
    }

    /**
     * 如果launcher Activity的launchmode为singletask，则启动该应用到任何界面，按home键，然后点击icon启动该应用，则不会回到原界面，而是调用该Activity的onNewIntent方法，清除其上的activity
     */

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: " + toString());
        super.onNewIntent(intent);
    }


    HomeListener mHomeWatcher;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeWatcher.stopWatch();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
