package com.example.totalapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.totalapplication.R;
import com.example.totalapplication.activities.LocalMusicActivity;
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

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MusicFragment extends Fragment {
    private static final String TAG = "MusicFragment";
    private LooperPager mLooperPager;
    private List<PagerItem> mData = new ArrayList<>();
    private LinearLayout mLocalMusicLL;
    private ApiService mApiService;

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
