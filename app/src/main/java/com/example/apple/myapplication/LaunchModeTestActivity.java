package com.example.apple.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LaunchModeTestActivity extends BaseActivity {

    private TextView infoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode_test);
        infoTv = (TextView) findViewById(R.id.infotv);
        infoTv.setText(info);
        Person person=new Person();
        person.setName("xx");
    }

    public void startActivityAExt(View v) {//standard
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.apple.launchmode", "com.example.apple.launchmode.ActivityA"));
        startActivity(intent);
    }

    public void startActivityBExt(View v) {//singletop
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.apple.launchmode", "com.example.apple.launchmode.ActivityB"));
        startActivity(intent);
    }

    public void startActivityCExt(View v) {//singletask
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.apple.launchmode", "com.example.apple.launchmode.ActivityC"));
        startActivity(intent);
    }

    public void startActivityDExt(View v) {//singleinstance
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.apple.launchmode", "com.example.apple.launchmode.ActivityD"));
        startActivity(intent);
    }

}
