package com.example.totalapplication.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.HttpUtils;
import com.example.totalapplication.adapters.VideoAdapter;
import com.example.totalapplication.api.Api;
import com.example.totalapplication.base.LoadState;
import com.example.totalapplication.domain.VideoBean;
import com.example.totalapplication.viewmodel.MovieViewModel;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;

public class MovieFragment extends Fragment {
    private ListView mMovieList;
    private TwinklingRefreshLayout mContentRefreshView;
    private MutableLiveData<LoadState> mLoadState = new MutableLiveData<LoadState>();
    private View mEmptyView;
    private View mLoadingView;
    private View mErrorView;
    private MovieViewModel mMovieViewModel;
    private ArrayList<String> mUrl = new ArrayList<String>();
    private int mCurrentUrl = 0;
    private boolean isLoadMore = false;
    private LinearLayout mReload;
    private VideoAdapter mVideoAdapter;
    private List<VideoBean.ItemListBean> mDatas;
    private String mNextPageUrl = null;
    private Boolean isRefresh = true;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                String json = (String) msg.obj;
                //解析数据
                try {
                    VideoBean videoBean = new Gson().fromJson(json, VideoBean.class);
                    //过滤不要的数据
                    mNextPageUrl = videoBean.getNextPageUrl();
                    System.out.println("mNextPageUrl======>" + mNextPageUrl);
                    List<VideoBean.ItemListBean> itemList = videoBean.getItemList();
                    for (int i = 0; i < itemList.size(); i++) {
                        VideoBean.ItemListBean listBean = itemList.get(i);
                        if ("video".equals(listBean.getType())) {
                            mDatas.add(listBean);
                        }
                    }
                    mLoadState.postValue(LoadState.SUCCESS);
                    //提示适配器更新数据
                    mVideoAdapter.notifyDataSetChanged();
                    isRefresh = true;
                } catch (RuntimeException e) {
                    mLoadState.postValue(isLoadMore ? LoadState.LOAD_MORE_ERROR : LoadState.ERROR);
                    if (mNextPageUrl == null) {
                        mLoadState.postValue(isLoadMore ? LoadState.LOAD_MORE_EMPTY : LoadState.EMPTY);
                    }
                    e.printStackTrace();
                    mCurrentUrl--;
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        注意，要让Fragment中的菜单项显示出来，还需要在Fragment中调用setHasOptionsMenu(true)方法。
//        传入true作为参数表明Fragment需要加载菜单项。建议在Fragment的onCreate方法中调用这个方法
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        mUrl.add(Api.MOVIE_URL);
        initView(view);
        //加载网络数据
        loadContent();
        initListener();
        initObserver();
    }

    private void loadData(final String url) {
        /*
        创建新线程，完成数据的获取
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonContent = HttpUtils.getJsonContent(url);
                    System.out.println("jsonContent======>" + jsonContent);
                    //主线程不能更新UI，需要通过handler发送数据回到主线程
                    Message message = new Message();//发送的消息对象
                    message.what = 1;//设置消息编号
                    message.obj = jsonContent;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    mLoadState.postValue(isLoadMore ? LoadState.LOAD_MORE_ERROR : LoadState.ERROR);
                    if (mNextPageUrl == null) {
                        mLoadState.postValue(isLoadMore ? LoadState.LOAD_MORE_EMPTY : LoadState.ERROR);
                    }
                    e.printStackTrace();
                    mCurrentUrl--;
                }
            }
        }).start();
    }

    private void initListener() {
        //数据源
        mDatas = new ArrayList<>();
        //创建适配器
        mVideoAdapter = new VideoAdapter(getContext(), mDatas);
        //设置适配器
        mMovieList.setAdapter(mVideoAdapter);
        mContentRefreshView.setEnableLoadmore(true);
        mContentRefreshView.setEnableRefresh(false);
        mContentRefreshView.setEnableOverScroll(true);
        mContentRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //加载更多
                if (isRefresh) {
                    loadMore();
                }
            }
        });
        mReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //重新加载数据
                loadData(mUrl.get(mCurrentUrl));
            }
        });
        //为ListView注册上下文浮动菜单
        registerForContextMenu(mMovieList);
    }

    private void loadMore() {
        isRefresh = false;
        isLoadMore = true;
        mLoadState.setValue(LoadState.LOAD_MORE_LOADING);
        mUrl.add(mNextPageUrl);
        mCurrentUrl++;
        loadData(mUrl.get(mCurrentUrl));
    }

    private void loadContent() {
        isLoadMore = false;
        mLoadState.setValue(LoadState.LOADING);
        loadData(mUrl.get(mCurrentUrl));
    }

    private void initObserver() {
        System.out.println("load state ====>" + mLoadState.getValue());
        mLoadState.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                hideAll();
                if (mLoadState.getValue() == LoadState.EMPTY) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else if (mLoadState.getValue() == LoadState.ERROR) {
                    mErrorView.setVisibility(View.VISIBLE);
                } else if (mLoadState.getValue() == LoadState.LOADING) {
                    mLoadingView.setVisibility(View.VISIBLE);
                } else {
                    mContentRefreshView.setVisibility(View.VISIBLE);
                    mContentRefreshView.finishLoadmore();
                }
                if (mLoadState.getValue() == LoadState.LOAD_MORE_ERROR) {
                    Toast.makeText(getContext(), "网络不佳,请稍后重试", Toast.LENGTH_SHORT).show();
                } else if (mLoadState.getValue() == LoadState.LOAD_MORE_EMPTY) {
                    Toast.makeText(getContext(), "已加载全部内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView(@NonNull View view) {
        mMovieList = view.findViewById(R.id.movie_lv);
        mContentRefreshView = view.findViewById(R.id.contentRefreshView);
        mEmptyView = view.findViewById(R.id.emptyView);
        mErrorView = view.findViewById(R.id.errorView);
        mLoadingView = view.findViewById(R.id.loadingView);
        View error = LayoutInflater.from(view.getContext()).inflate(R.layout.load_on_error, null);
        mReload = error.findViewById(R.id.reloadLL);
    }

    private void hideAll() {
        mContentRefreshView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JzvdStd.releaseAllVideos();//释放正在播放的视频信息
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=new MenuInflater(getContext());
        inflater.inflate(R.menu.menu_movie_list,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_option_add:
                Toast.makeText(getContext(), "添加", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.context_option_save:
                Toast.makeText(getContext(), "保存", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.context_option_delete:
                Toast.makeText(getContext(), "删除", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}