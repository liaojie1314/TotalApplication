package com.example.totalapplication.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class StartSystemPageUtils {

    /**
     * 跳转到系统当前引用设置界面
     */
    public static void goToAppSetting(Activity activity){
        Intent intent=new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri=Uri.fromParts("package",activity.getPackageName(),null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    /**
     * 跳转到手机的Home页面
     */
    public static void goToHomePage(Activity activity){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);
    }
}
