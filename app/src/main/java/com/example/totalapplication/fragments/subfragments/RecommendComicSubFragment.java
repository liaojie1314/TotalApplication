package com.example.totalapplication.fragments.subfragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.totalapplication.R;
import com.example.totalapplication.activities.cimoc.CimocMainActivity;
import com.example.totalapplication.adapters.cimoc.ChineseCimocListAdapter;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.controllers.APKUpdateController;
import com.example.totalapplication.domain.entities.APKUpdate;
import com.example.totalapplication.domain.entities.Comic;
import com.example.totalapplication.interfaces.DataLoadInterface;
import com.example.totalapplication.interfaces.MyTouchInterface;
import com.example.totalapplication.loaders.ChineseComicLoader;
import com.example.totalapplication.loaders.RecommendationComicLoader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import java.util.List;


public class RecommendComicSubFragment extends Fragment {
    private RecyclerView recyclerView;
    private TwinklingRefreshLayout mRefreshLayout;
    private ChineseComicLoader chineseComicLoader;
    private RecommendationComicLoader comicLoader;
    private MyTouchInterface mMyTouchInterface;
    private ChineseCimocListAdapter comicListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subfragment_recommend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        chineseComicLoader.destroyLoader();
        ((CimocMainActivity)this.getActivity()).unRegisterMyTouchListener(mMyTouchInterface);
    }

    @Override
    public void onStop() {
        super.onStop();
        chineseComicLoader.pause();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        checkUpdate();
        comicLoader = new RecommendationComicLoader(this);
        chineseComicLoader = new ChineseComicLoader(this);
        recyclerView = getActivity().findViewById(R.id.sub_fg_recommend_ry);
        mRefreshLayout = getActivity().findViewById(R.id.sub_fg_recommend_refresh);
        initRefreshLayout();
        loadData();
        initTouchListener();
        mRefreshLayout.startRefresh();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        chineseComicLoader.loadComicList("https://www.manhuatai.com/all.html", new DataLoadInterface() {
            @Override
            public void onLoadFinished(Object o, Exception e) {
                if (e == null) {
                    showComicItems((List<Comic>) o);
                    mRefreshLayout.finishRefreshing();
                }
            }
        });
        comicLoader.loadRecommendationComics("https://www.50mh.com/", new DataLoadInterface() {
            @Override
            public void onLoadFinished(Object o, Exception e) {
                if (comicListAdapter != null) {
                    comicListAdapter.updateBannerImgList((List<Comic>) o);
                }
            }
        });
    }

    /**
     * 显示recyclerView
     */
    private void showComicItems(List<Comic> comicList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        comicListAdapter = new ChineseCimocListAdapter(comicList, R.layout.item_home, getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(comicListAdapter);
    }

    /**
     * 检查APP更新
     */
    private void checkUpdate() {
        final APKUpdateController updateController = new APKUpdateController(getActivity());
        updateController.checkNewVersion(new APKUpdateController.OnAPKUpdateListener() {
            @Override
            public void onQueryDone(APKUpdate update, boolean newVersion) {
                if (newVersion && update != null) {
                    updateController.showNewVersionDialog(update);
                }
            }
            @Override
            public void onQueryError(Exception e) {
                Log.d(BasicConfig.TAG, "onQueryError: " + e.toString());
            }
        });
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableLoadmore(false);
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        headerView.setArrowResource(R.drawable.ic_my_arrow);
        headerView.setTextColor(Color.parseColor("#7C7676"));
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }, 800);
            }
        });
    }

    private void initTouchListener() {
        mMyTouchInterface = new MyTouchInterface() {
            float xDown = -1, yDown = -1;
            @Override
            public void onTouchEvent(MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = ev.getX();
                        yDown = ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(ev.getX() - xDown) < Math.abs(ev.getY() - yDown) && (ev.getY() - yDown) > 0) {
                            // ptrFrame.setEnabled(true);
                        } else {
                            // ptrFrame.setEnabled(false);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            }
        };
        ((CimocMainActivity) getActivity()).registerMyTouchListener(mMyTouchInterface);
    }
}
