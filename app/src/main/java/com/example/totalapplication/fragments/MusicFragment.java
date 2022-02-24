package com.example.totalapplication.fragments;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.NotificationUtils;
import com.example.totalapplication.activities.LocalMusicActivity;
import com.example.totalapplication.activities.MainActivity;
import com.example.totalapplication.api.AndroidScheduler;
import com.example.totalapplication.api.Api;
import com.example.totalapplication.api.ApiService;
import com.example.totalapplication.api.NetWorkModule;
import com.example.totalapplication.api.exception.ApiException;
import com.example.totalapplication.api.exception.ErrorConsumer;
import com.example.totalapplication.customviews.looper.LooperPager;
import com.example.totalapplication.customviews.looper.PagerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MusicFragment extends Fragment {
    private static final String TAG = "MusicFragment";
    private LooperPager mLooperPager;
    private List<PagerItem> mData = new ArrayList<>();
    private LinearLayout mLocalMusicLL;
    private ApiService mApiService;
    private NotificationManager mManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        initListener();
        initEvent();

        mManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }

        /*
        //跳转
        //Intent intent=new Intent(getContext(),NotificationActivity.class);
        //PendingIntent pendingIntent=PendingIntent.getActivity(getContext(),0,intent,0);
        NotificationManager notificationManager = (NotificationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(Objects.requireNonNull(getContext()), "1")
                .setContentTitle("这是测试通知标题")  //设置标题
                .setContentText("这是测试通知内容") //设置内容
                .setWhen(System.currentTimeMillis())  //设置时间
                .setSmallIcon(R.mipmap.ic_launcher)  //设置小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))   //设置大图标
                //.setContentIntent(pendingIntent)
                //.setAutoCancel(true)//设置为自动取消
                //.setAutoCancel(1)//在跳转后的Activity中 1，就是我们创建Notification中指定的通知的ID
                //.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg"))) //设置通知提示音
                //.setVibrate(new long[]{0,1000,1000,1000}) //设置振动， 需要添加权限  <uses-permission android:name="android.permission.VIBRATE"/>
                //.setLights(Color.GREEN,1000,1000)//设置前置LED灯进行闪烁， 第一个为颜色值  第二个为亮的时长  第三个为暗的时长
                //.setDefaults(NotificationCompat.DEFAULT_ALL)  //使用默认效果， 会根据手机当前环境播放铃声， 是否振动
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("这是一段很长很长很长很长很长很长很长很长很长很长"))//长文字
                //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)))//大图片
                //.setPriority(NotificationCompat.PRIORITY_MAX)//设置通知重要程度
                //PRIORITY_DEFAULT：表示默认重要程度，和不设置效果一样
                //PRIORITY_MIN：表示最低的重要程度。系统只会在用户下拉状态栏的时候才会显示
                //PRIORITY_LOW：表示较低的重要性，系统会将这类通知缩小，或者改变显示的顺序，将排在更重要的通知之后。
                //PRIORITY_HIGH：表示较高的重要程度，系统可能会将这类通知方法，或改变显示顺序，比较靠前
                //PRIORITY_MAX：最重要的程度， 会弹出一个单独消息框，让用户做出相应。
                .build();
        notificationManager.notify(1,notification);
         */
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) Objects.requireNonNull(getActivity()).getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    private void initListener() {
        mLooperPager.setData(mInnerAdapter, new LooperPager.BindTitleListener() {
            @Override
            public String getTitle(int position) {
                return mData.get(position).getTitle();
            }
        });
        mLocalMusicLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LocalMusicActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initEvent() {
        if (mLooperPager != null) {
            mLooperPager.setOnItemClickListener(new LooperPager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getContext(), "点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
                    //根据交互业务实现具体逻辑
                    if (position == 0) {
                        Notification notification = new NotificationCompat.Builder(Objects.requireNonNull(getContext()), "chat")
                                .setAutoCancel(true)
                                .setContentTitle("收到聊天消息")
                                .setContentText("今天晚上吃什么")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                //设置红色
                                .setColor(Color.parseColor("#F00606"))
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .build();
                        mManager.notify(1, notification);
                    }
                    if (position==1){
                        Notification notificationGet = new NotificationCompat.Builder(getActivity(), "subscribe")
                                .setAutoCancel(true)
                                .setContentTitle("收到订阅消息")
                                .setContentText("新闻消息")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .build();
                        mManager.notify(2, notificationGet);
                    }
                }
            });
        }
    }

    private void initData() {
        NetWorkModule netWorkModule = new NetWorkModule();
        OkHttpClient okHttpClient = netWorkModule.providerOkHttpClient();
        Retrofit retrofit = netWorkModule.providerRetrofit(okHttpClient, Api.OPEN_API_BASE_URL);
        mApiService = netWorkModule.providerApiService(retrofit);
        mApiService.getPic(1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "data=======>" + s);
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {
                        Log.i(TAG, "load error.....");
                    }
                });
        mData.add(new PagerItem("第一张图片", R.mipmap.pic1));
        mData.add(new PagerItem("第2张图片", R.mipmap.pic2));
        mData.add(new PagerItem("第三张图片", R.mipmap.pic3));
        mData.add(new PagerItem("第4张图片", R.mipmap.pic4));
    }

    private LooperPager.InnerAdapter mInnerAdapter = new LooperPager.InnerAdapter() {
        @Override
        protected int getDataSize() {
            return mData.size();
        }

        @Override
        protected View getLooperView(ViewGroup container, int position) {
            ImageView iv = new ImageView(container.getContext());
            iv.setImageResource(mData.get(position).getPicResId());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }
    };

    private void initView(View view) {
        mLooperPager = view.findViewById(R.id.looperPager);
        mLocalMusicLL = view.findViewById(R.id.localMusicLL);
    }
}