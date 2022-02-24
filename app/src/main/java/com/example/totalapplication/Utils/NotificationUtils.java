package com.example.totalapplication.Utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {
    private static NotificationManager notificationManager;

    /**
     * 初始化NotificationManager
     *
     * @param context     上下文
     * @param channelId   通知渠道ID
     * @param channelName 通知渠道名称
     * @param importance  通知渠道重要性
     */
    public static void initNotificationManager(Context context, String channelId, String channelName, int importance) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        //判断是否为8.0以上：Build.VERSION_CODES.O为26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context, channelId, channelName, importance);
        }
    }

    //创建通知渠道
    @TargetApi(Build.VERSION_CODES.O)
    private static void createNotificationChannel(Context context, String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //channel有很多set方法
        //为NotificationManager设置通知渠道
        notificationManager.createNotificationChannel(channel);
    }
}
