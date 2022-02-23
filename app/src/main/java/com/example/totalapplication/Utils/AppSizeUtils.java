package com.example.totalapplication.Utils;

import com.example.totalapplication.base.App;

public class AppSizeUtils {
    public static int dip2px(float dpValue) {
        float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
