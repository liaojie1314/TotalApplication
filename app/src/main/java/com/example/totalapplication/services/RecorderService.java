package com.example.totalapplication.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.example.totalapplication.R;
import com.example.totalapplication.activities.recoder.RecorderActivity;
import com.example.totalapplication.base.Contacts;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecorderService extends Service {
    private MediaRecorder mRecorder;
    private boolean isAlive = false;
    private String recorderDirPath;//存放录音文件的公共目录
    private SimpleDateFormat sdf, calSdf;
    private int time = 0;
    private RemoteViews mRemoteView;
    private NotificationManager mManager;
    private Notification mNotification;
    private int NOTIFY_ID_RECORDER=102;

    @Override
    public void onCreate() {
        super.onCreate();
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        calSdf = new SimpleDateFormat("HH:mm:ss");
        recorderDirPath = Contacts.PATH_FETCH_DIR_RECORD;
        initRemoteView();
        initNotification();
    }

    /**
     * 初始化通知对象
     */
    private void initNotification() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.icon_voice)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_voice))
                .setContent(mRemoteView)
                .setAutoCancel(false)
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setPriority(Notification.PRIORITY_HIGH);
        mNotification = builder.build();
    }

    /**
     * 更新发送通知的函数
     */

    private void updateNotification(String calTime) {
        mRemoteView.setTextViewText(R.id.my_time, calTime);
        mManager.notify(NOTIFY_ID_RECORDER,mNotification);
    }

    /**
     * 关闭通知
     */

    private void closeNotification(){
        mManager.cancel(NOTIFY_ID_RECORDER);
    }

    /**
     * 初始化通知当中的远程View
     */
    private void initRemoteView() {
        mRemoteView = new RemoteViews(getPackageName(), R.layout.notify_recorder);
        Intent intent = new Intent(this, RecorderActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteView.setOnClickPendingIntent(R.id.my_layout, pi);
    }

    /**
     * 设置更新Activity的UI界面的更新回调接口
     */
    public interface OnRefreshUIThreadListener {
        void OnRefresh(int fb, String time);
    }

    private OnRefreshUIThreadListener mOnRefreshUIThreadListener;

    public void setOnRefreshUIThreadListener(OnRefreshUIThreadListener onRefreshUIThreadListener) {
        this.mOnRefreshUIThreadListener = onRefreshUIThreadListener;
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (mRecorder == null) return false;
            double ratio = (double) mRecorder.getMaxAmplitude() / 100;
            double db = 0;// 分贝
            //默认的最大音量是100,可以修改，但其实默认的，在测试过程中就有不错的表现
            //你可以传自定义的数字进去，但需要在一定的范围内，比如0-200，就需要在xml文件中配置maxVolume
            //同时，也可以配置灵敏度sensibility
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
            }
            time += 1000;
            if (mOnRefreshUIThreadListener != null) {
                String timeString = calTime(time);
                mOnRefreshUIThreadListener.OnRefresh((int) db, timeString);
                updateNotification(timeString);
            }
            return false;
        }
    });

    /**
     * 计算时间为指定格式
     *
     * @param second 时间
     * @return 指定格式的时间字符串
     */
    private String calTime(int second) {
        second -= 8 * 60 * 60 * 1000;
        String format = calSdf.format(new Date(second));
        return format;
    }

    /**
     * 开启子线程，实时获取音量以及当前录制的时间 反馈给主线程
     */

    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isAlive) {
                mHandler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public RecorderService() {
    }

    /**
     * 开启录音
     */
    public void startRecorder() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        isAlive = true;
        mRecorder.reset();
        //设置录音对象的参数
        setRecorder();
        try {
            mRecorder.prepare();
            mRecorder.start();
            mThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录音
     */
    public void stopRecorder() {
        if (mRecorder != null) {
            try {
                mRecorder.stop();
            } catch (IllegalStateException e) {
                // TODO 如果当前java状态和jni里面的状态不一致，
                //e.printStackTrace();
                mRecorder = null;
                mRecorder = new MediaRecorder();
            }
            mRecorder.release();
            mRecorder = null;
            time = 0;
            closeNotification();
            isAlive = false;//停止线程,避免内存泄漏
        }
    }

    /**
     * 设置录音对象的参数
     */
    private void setRecorder() {
        //设置获取麦克风声音
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置输出格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
        //设置编码格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        //设置输出文件
        String time = sdf.format(new Date());
        File file = new File(recorderDirPath, time + ".amr");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mRecorder.setOutputFile(file.getAbsolutePath());
        //设置最多录音的时间,最多录制10分钟
        mRecorder.setMaxDuration(10 * 60 * 1000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new RecorderBinder();
    }

    public class RecorderBinder extends Binder {
        public RecorderService getService() {
            return RecorderService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecorder();
    }
}