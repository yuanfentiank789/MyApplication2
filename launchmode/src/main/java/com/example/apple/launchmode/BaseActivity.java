package com.example.apple.launchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by apple on 15-12-15.
 * <p/>
 * （1）"standard"（默认模式）:
 * 默认。系统在启动 Activity 的任务中创建 Activity 的新实例并向其传送 Intent。Activity 可以多次实例化，而每个实例均可属于不同的任务，并且一个任务可以拥有多个实例。
 * （2）这就是singleTask模式，如果发现有对应的Activity实例，则使此Activity实例之上的其他Activity实例统统出栈，使此Activity实例成为栈顶对象，显示到幕前。
 */
public abstract class BaseActivity extends Activity {

    private final String TAG = "BaseActivity";
    private final String actName = getClass().getSimpleName();
    public String info;
    private TextView infoTv;
    private TextView nameTv;
    private Button actionBtn;
    private Button actionBtn2;
    private Button actionBtn3;
    private Button actionBtn4;

    Handler mHadnler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = actName + ":pid=" + android.os.Process.myPid() + "\ntaskId=" + getTaskId() + "\ninstance:" + toString();
        Log.d(TAG, info);
    }

    protected void initView() {
        infoTv = (TextView) findViewById(R.id.infotv);
        infoTv.setText(info);
        mHadnler=infoTv.getHandler();
        nameTv = (TextView) findViewById(R.id.actname);
        nameTv.setText(getClass().getSimpleName());
        actionBtn = (Button) findViewById(R.id.doAction);
        actionBtn.setText("startActA:standard");
        actionBtn2 = (Button) findViewById(R.id.doAction2);
        actionBtn2.setText("startActB:singletop");
        actionBtn3 = (Button) findViewById(R.id.doAction3);
        actionBtn3.setText("startActC:singletask");
        actionBtn4 = (Button) findViewById(R.id.doAction4);
        actionBtn4.setText("startActD:singleinstance");
    }

    public abstract void doAction(View v);

    public abstract void doAction2(View v);

    public abstract void doAction3(View v);

    public void doAction4(View v) {
        startActD();
    }


    public void startActA() {
        Intent intent = new Intent(this, ActivityA.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//效果相当于launchmode中的singletop,优先级高于launchmode
        startActivity(intent);
    }

    public void startActB() {
        Intent intent = new Intent(this, ActivityB.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//两个flag一起使用，效果相当于launchmode中的singletask，而且flag优先级高于launchmode
        startActivity(intent);
    }

    public void startActC() {
        Intent intent = new Intent(this, ActivityC.class);
        startActivity(intent);
    }

    public void startActD() {
        Intent intent = new Intent(this, ActivityD.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, actName + ":onNewIntent: ");
    }

    public void offsetVertical(View v) {
//        Toast.makeText(this, "tt", Toast.LENGTH_SHORT).show();
        ViewGroup parent = (ViewGroup) v.getParent();
    }
}
