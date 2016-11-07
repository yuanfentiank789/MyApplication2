package com.example.apple.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by apple on 15-12-15.
 */
public class BaseActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    public String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = "pid=" + android.os.Process.myPid() + "\ntaskId=" + getTaskId() + "\ninstance:" + toString();
        Log.d(TAG, info);
    }

}
