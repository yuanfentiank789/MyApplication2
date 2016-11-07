package com.example.apple.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * CoordinatorLayout作为“super-powered FrameLayout”基本实现两个功能：
 * 1、作为顶层布局
 * 2、调度协调子布局
 * <p>
 * CoordinatorLayout使用新的思路通过协调调度子布局的形式实现触摸影响布局的形式产生动画效果。CoordinatorLayout通过设置子View的 Behaviors来调度子View。系统（Support V7）提供了AppBarLayout.Behavior, AppBarLayout.ScrollingViewBehavior, FloatingActionButton.Behavior, SwipeDismissBehavior<V extends View> 等。
 * <p>
 * 使用CoordinatorLayout需要在Gradle加入Support Design Library：
 * <p>
 * compile 'com.android.support:design:22.2.1'
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = R.layout.activity_main;
        layoutId = R.layout.activity_fab;
        setContentView(layoutId);//CoordinatorLayout与fab

        switch (layoutId) {
            case R.layout.activity_fab:
                fabClick();
                break;
            case R.layout.activity_main:

                break;
        }

    }

    private void fabClick() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "FAB", Snackbar.LENGTH_LONG)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件

                            }
                        })
                        .show();
            }
        });
    }

    /**
     * （1）Action:如果filter中指定了action，则intent的action必须在filter的action列表才能通过action这层匹配；
     * （2）Category:只有通过action匹配才会进行category匹配，category只要匹配一个就能通过匹配；
     */
    public void openSetting(View v) throws URISyntaxException {
        watchURI();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
//        intent.setAction(Intent.ACTION_EDIT);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        startActivity(intent);
    }

    private void watchURI() throws URISyntaxException {

        URI uri = new URI("lwy://cn.com.zjseek.lwy/news?id=2&path=3");
        System.out.println(uri.getScheme()); // lwy
        System.out.println(uri.getHost());  // cn.com.zjseek.lwy
        System.out.println(uri.getAuthority());  // cn.com.zjseek.lwy
        System.out.println(uri.getRawAuthority());  // cn.com.zjseek.lwy
        System.out.println(uri.getPath());  // /news/sports
        System.out.println(uri.getQuery()); // id=2&path=3
    }
}
