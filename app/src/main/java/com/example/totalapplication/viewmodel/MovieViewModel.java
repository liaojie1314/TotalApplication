package com.example.totalapplication.viewmodel;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.totalapplication.Utils.HttpUtils;
import com.example.totalapplication.adapters.VideoAdapter;
import com.example.totalapplication.api.Api;
import com.example.totalapplication.base.LoadState;
import com.example.totalapplication.domain.VideoBean;
import com.google.gson.Gson;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<LoadState> loadState = new MutableLiveData<LoadState>();
    private VideoAdapter mVideoAdapter;
    private List<VideoBean.ItemListBean> mDatas;
    private boolean isLoadMore = false;

    public void loadMore() {
        // 加载更多
        isLoadMore = true;
        loadState.setValue(LoadState.LOAD_MORE_LOADING);
    }


    public void loadData() {
        isLoadMore = false;
        loadState.setValue(LoadState.LOADING);
    }
}
