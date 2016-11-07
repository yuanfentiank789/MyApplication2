package com.example.apple.launchmode;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import model.HomeListener;

public class ActivityA extends BaseActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        registerHomeListener();
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

    /**
     * 注册Home键的监听
     */
    private void registerHomeListener() {
        mHomeWatcher = new HomeListener(this);
        mHomeWatcher.setOnHomePressedListener(new HomeListener.OnHomePressedListener() {

            @Override
            public void onHomePressed() {
                //TODO 进行点击Home键的处理
                Toast.makeText(ActivityA.this, "short press", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHomeLongPressed() {
                //TODO 进行长按Home键的处理
                Toast.makeText(ActivityA.this, "long press", Toast.LENGTH_SHORT).show();
            }
        });
        mHomeWatcher.startWatch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeWatcher.stopWatch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHadnler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityA.this, "" + isAppOnForeground(), Toast.LENGTH_SHORT).show();

            }
        }, 10);
    }

    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
