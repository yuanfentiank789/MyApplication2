package ndk.com.example.apple.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LooperTestAct extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_test);
        new Thread() {

            @Override
            public void run() {
                Log.d(TAG, "run: in :" + Thread.currentThread().getName());
                Looper.prepare();
                mHandler = new Handler() {//默认取当前线程的Looper
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 100:
                                Log.d(TAG, "run: in handleMessage :" + Thread.currentThread().getName() + ",what=" + msg.what);
                                break;
                            case 404:
                                getLooper().quit();
//                                getMainLooper().quit();//java.lang.IllegalStateException: Main thread not allowed to quit.
                                break;
                        }
                    }
                };
                Looper.loop();//loop执行后，在looper执行quit之前不会执行其后代码
                Log.d(TAG, "run: after loop() called!");
            }
        }.start();
        handlerThread = new HandlerThread("myhandlerthread");
        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {//callback优先于handler的handleMessage方法执行
                switch (msg.what) {
                    case 200:
                        Log.d(TAG, "run: in callback handleMessage :" + Thread.currentThread().getName() + ",what=" + msg.what);
                        break;
                }
                return true;
            }

        }) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 200:
                        Log.d(TAG, "run: in handleMessage :" + Thread.currentThread().getName() + ",what=" + msg.what);
                        break;
                }
            }
        };
    }

    public void startThread(View view) {
        mHandler.sendEmptyMessage(404);
    }

    public void startHandlerThread(View view) {
        threadHandler.sendEmptyMessage(200);
    }

    private HandlerThread handlerThread;
    Handler threadHandler;


    List<String> list ;

    public void allocationtrack(View view) {
        if(list==null){
            list=new ArrayList<>(100);
        }
        for (int i = 0; i < 10; i++) {
            list.add("index:" + i);
        }
    }
}