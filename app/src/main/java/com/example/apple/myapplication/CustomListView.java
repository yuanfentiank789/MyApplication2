package com.example.apple.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by apple on 15-12-9.
 */
public class CustomListView extends ListView {
    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
