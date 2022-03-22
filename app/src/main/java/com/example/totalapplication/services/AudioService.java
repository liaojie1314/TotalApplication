package com.example.totalapplication.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.example.totalapplication.R;
import com.example.totalapplication.base.Contacts;
import com.example.totalapplication.domain.AudioBean;

import java.util.List;

public class AudioService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer = null;
    private List<AudioBean> mList;
    private int playPosition = -1;//记录当前播放位置
    private RemoteViews mRemoteView;//通知对应的布局生成View对象
    private NotificationManager mManager;
    private AudioReceiver mReceiver;
    private final int NOTIFY_ID_MUSIC = 101;//发送通知的ID

    /**
     * 接收通知发出的广播action
     */
    private final String PRE_ACTION_LAST = "com.totalapplication.last";
    private final String PRE_ACTION_PLAY = "com.totalapplication.play";
    private final String PRE_ACTION_NEXT = "com.totalapplication.next";
    private final String PRE_ACTION_CLOSE = "com.totalapplication.close";
    private Notification mNotification;

    //创建通知对象和远程View对象
    @Override
    public void onCreate() {
        super.onCreate();
        initRegisterReceiver();
        initRemoteView();
        initNotification();
    }

    /**
     * 创建广播接收者
     */
    class AudioReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            notifyUIControl(action);
        }
    }

    private void notifyUIControl(String action) {
        switch (action) {
            case PRE_ACTION_LAST:
                previousMusic();
                break;
            case PRE_ACTION_PLAY:
                pauseOrContinueMusic();
                break;
            case PRE_ACTION_NEXT:
                nextMusic();
                break;
            case PRE_ACTION_CLOSE:
                closeNotification();
                break;
        }
    }

    /**
     * 关闭通知
     */
    private void closeNotification() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mList.get(playPosition).setPlaying(false);
        }
        notifyActivityRefreshUI();
        mManager.cancel(NOTIFY_ID_MUSIC);
    }

    /**
     * 注册广播接受者 用于接收用户点击通知栏按钮发出的信息
     */
    private void initRegisterReceiver() {
        mReceiver = new AudioReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PRE_ACTION_LAST);
        filter.addAction(PRE_ACTION_PLAY);
        filter.addAction(PRE_ACTION_NEXT);
        filter.addAction(PRE_ACTION_CLOSE);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 设置通知栏显示效果 以及 图片点击事件
     */
    private void initRemoteView() {
        mRemoteView = new RemoteViews(getPackageName(), R.layout.notify_audio);
        PendingIntent lastPI = PendingIntent
                .getBroadcast(this, 1, new Intent(PRE_ACTION_LAST),
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteView.setOnClickPendingIntent(R.id.my_iv_last, lastPI);

        PendingIntent nextPI = PendingIntent
                .getBroadcast(this, 1, new Intent(PRE_ACTION_NEXT),
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteView.setOnClickPendingIntent(R.id.my_iv_next, nextPI);

        PendingIntent playPI = PendingIntent
                .getBroadcast(this, 1, new Intent(PRE_ACTION_PLAY),
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteView.setOnClickPendingIntent(R.id.my_iv_play, playPI);

        PendingIntent closePI = PendingIntent
                .getBroadcast(this, 1, new Intent(PRE_ACTION_CLOSE),
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteView.setOnClickPendingIntent(R.id.my_iv_close, closePI);
    }

    /**
     * 初始化通知栏
     */
    private void initNotification() {
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.icon_app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_app_logo))
                .setContent(mRemoteView)
                .setAutoCancel(false)
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setPriority(Notification.PRIORITY_HIGH);
        mNotification = builder.build();
    }

    /**
     * 更新通知栏信息
     */
    private void updateNotification(int position) {
        //根据多媒体播放状态判断显示图片
        if (mMediaPlayer.isPlaying()) {
            mRemoteView.setImageViewResource(R.id.my_iv_play, R.mipmap.red_pause);
        } else {
            mRemoteView.setImageViewResource(R.id.my_iv_play, R.mipmap.red_play);
        }
        mRemoteView.setTextViewText(R.id.my_tv_title, mList.get(position).getTitle());
        mRemoteView.setTextViewText(R.id.my_tv_duration, mList.get(position).getDuration());
        //发送通知
        mManager.notify(NOTIFY_ID_MUSIC, mNotification);
    }

    public AudioService() {
    }

    public interface OnPlayChangeListener {
        public void PlayChange(int changePosition);
    }

    private OnPlayChangeListener mOnPlayChangeListener;

    public void setOnPlayChangeListener(OnPlayChangeListener onPlayChangeListener) {
        this.mOnPlayChangeListener = onPlayChangeListener;
    }

    /**
     * 多媒体服务发生变化 提示Activity刷新UI
     */
    public void notifyActivityRefreshUI() {
        if (mOnPlayChangeListener != null) {
            mOnPlayChangeListener.PlayChange(playPosition);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextMusic();//播放完成 直接播放下一个
    }

    public class AudioBinder extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new AudioBinder();
    }

    /**
     * 播放下一曲
     */
    private void nextMusic() {
        mList.get(playPosition).setPlaying(false);
        if (playPosition >= (mList.size() - 1)) {
            playPosition = 0;
        } else {
            playPosition++;
        }
        mList.get(playPosition).setPlaying(true);
        play(playPosition);
    }

    /**
     * 播放上一曲
     */
    private void previousMusic() {
        mList.get(playPosition).setPlaying(false);
        if (playPosition == 0) {
            playPosition = mList.size() - 1;
        } else {
            playPosition--;
        }
        mList.get(playPosition).setPlaying(true);
        play(playPosition);
    }

    /**
     * 停止音乐
     */

    public void closeMusic(){
        if (mMediaPlayer!=null) {
            setFlagControlThread(false);
            closeNotification();
            mMediaPlayer.stop();
            playPosition=-1;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver!=null) {
            unregisterReceiver(mReceiver);
        }
        closeMusic();
    }

    /**
     * 更新播放进度
     */
    private boolean flag = false;
    private final int PROGRESS_ID = 1;
    private final int INIERMINATE_TIME = 1000;

    public void setFlagControlThread(boolean flag) {
        this.flag = flag;
    }

    public void updateProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    //获取总时长
                    long total = mList.get(playPosition).getDurationLong();
                    //获取当前播放位置
                    int currentPosition = mMediaPlayer.getCurrentPosition();
                    //计算播放进度
                    int progress = (int) (currentPosition * 100 / total);
                    mList.get(playPosition).setCurrentProgress(progress);
                    mHandler.sendEmptyMessageDelayed(PROGRESS_ID, INIERMINATE_TIME);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == PROGRESS_ID) {
                notifyActivityRefreshUI();
            }
            return true;
        }
    });

    /**
     * 判断播放按钮点击位置
     * 1.不是当前播放位置被点击 切歌
     * 2.当前播放位置被点击 暂停/继续
     */

    public void CutMusicOrPause(int position) {
        int playPosition = this.playPosition;
        if (position != playPosition) {
            //判断是否正在播放 切歌后将原来歌曲改为false
            if (playPosition != -1) {
                mList.get(playPosition).setPlaying(false);
            }
            play(position);
            return;
        }
        //执行暂停/继续
        pauseOrContinueMusic();
    }

    /**
     * 播放音乐 点击切歌
     */

    public void play(int position) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            //设置监听事件
            mMediaPlayer.setOnCompletionListener(this);
        }
        //播放时 获取当前播放列表
        mList = Contacts.getAudioBeanList();
        if (mList.size() <= 0) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        //切歌之前先重置 释放之前的资源
        try {
            mMediaPlayer.reset();
            playPosition = position;
            //设置播放音频的资源路径
            mMediaPlayer.setDataSource(mList.get(position).getPath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            //设置正在播放
            mList.get(position).setPlaying(true);
            notifyActivityRefreshUI();
            setFlagControlThread(true);
            updateProgress();
            updateNotification(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 暂停/继续播放音乐
     */
    public void pauseOrContinueMusic() {
        int playPosition = this.playPosition;
        AudioBean audioBean = mList.get(playPosition);
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            audioBean.setPlaying(false);
        } else {
            mMediaPlayer.start();
            audioBean.setPlaying(true);
        }
        notifyActivityRefreshUI();
        updateNotification(playPosition);
    }
}