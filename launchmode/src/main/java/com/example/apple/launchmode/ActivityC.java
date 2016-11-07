package com.example.apple.launchmode;

import android.os.Bundle;
import android.view.View;

public class ActivityC extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    @Override
    public void doAction3(View v) {
        startActC();
    }

}
