package com.example.totalapplication.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    /**
     * 长消息
     *
     * @param context 上下文参数
     * @param content 内容
     */
    public static void longToast(Context context, CharSequence content) {
        Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    /**
     * 短消息
     *
     * @param context 上下文参数
     * @param content 内容
     */
    public static void shortToast(Context context, CharSequence content) {
        Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }
}


