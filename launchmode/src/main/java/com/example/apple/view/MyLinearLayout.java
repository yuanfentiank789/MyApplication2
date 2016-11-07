package com.example.apple.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by apple on 15-12-23.
 */
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
        init();
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ttt", Toast.LENGTH_SHORT).show();
//                offsetTopAndBottom(10);//偏移自己在parent的竖直位置
//                offsetLeftAndRight(10);//偏移自己在parent的水平位置
//                offsetChildrenTopAndBottom(10);//hide
            }
        });
    }
}
