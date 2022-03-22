package com.example.totalapplication.activities.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.example.totalapplication.Utils.ToastUtils;

public class LocalJavaScript {

    Context context;

    public LocalJavaScript(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public String callFromJS(String str) {
        ToastUtils.shortToast(context, "调用了android当中的方法");
        return "abc";
    }
}
