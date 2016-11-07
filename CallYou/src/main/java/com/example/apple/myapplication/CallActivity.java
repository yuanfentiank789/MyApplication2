package com.example.apple.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

/**
 * 本Activity有两个功能：
 * 1 呼死你拨号器
 * 2 动态修改app图标
 */
public class CallActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    public static void launch(Context context) {
        Intent intent = new Intent(context, CallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }

    public void call(View v) {
        callIt(this);
//        setIcon(ACTIVITY_ALIAS_1);

    }

    public void setAlias1(View v) {
        setIcon(ACTIVITY_ALIAS_1);

    }

    public void setAlias2(View v) {
        setIcon(ACTIVITY_ALIAS_2);

    }

    public void callIt(Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL);

//        Uri data = Uri.parse("tel:" + " 18101175570");
        Uri data = Uri.parse("tel:" + " 13910424016");

        intent.setData(data);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        callIt(this);
    }

    private String ACTIVITY_ALIAS_1 = "com.example.call.alias1";
    private String ACTIVITY_ALIAS_2 = "com.example.call.alias2";

    private void setIcon(String activity_alias) {
        Context ctx = this;
        PackageManager pm = ctx.getPackageManager();
        ActivityManager am = (ActivityManager) ctx.getSystemService(Activity.ACTIVITY_SERVICE);

        // Enable/disable activity-aliases
        pm.setComponentEnabledSetting(
                new ComponentName(ctx, ACTIVITY_ALIAS_1),
                ACTIVITY_ALIAS_1.equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(
                new ComponentName(ctx, ACTIVITY_ALIAS_2),
                ACTIVITY_ALIAS_2.equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        // Find launcher and kill it
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = pm.queryIntentActivities(i, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }
}

