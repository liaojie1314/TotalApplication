package com.example.totalapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.totalapplication.R;
import com.example.totalapplication.adapters.RecyclerGridAdapter;
import com.example.totalapplication.adapters.RecyclerLineAdapter;
import com.example.totalapplication.adapters.RecyclerStaggeredAdapter;
import com.example.totalapplication.adapters.ViewPageAdapter;
import com.example.totalapplication.domain.Data;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    List<View> views;
    List<String> titles;
    List<Map<String, Object>> lineData = new ArrayList<>();
    List<Map<String, Object>> staggeredData = new ArrayList<>();
    List<Map<String, Object>> gridData = new ArrayList<>();
    RecyclerView lineRecyclerView;
    RecyclerView gridRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    /**
     * tabLayout
     * app:tabBackground 标签布局的背景色
     * app:tabIndicatorColor 指示器的颜色
     * app:tabIndicatorHeight 指示器的高度（如果不需要指示器可以设置为0dp）
     * app:tabMode 显示模式：默认 fixed（固定），scrollable（可横向滚动）
     * app:tabPadding 标签内边距
     * app:tabSelectedTextColor 选中的颜色
     * app:tabTextAppearance 标签文本样式
     * app:tabTextColor 未选中的颜色
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.one_view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        View fragmentHomeOne = LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_home_one, null);
        lineRecyclerView = fragmentHomeOne.findViewById(R.id.line_recy_view);
        // 设置为线性布局
        lineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 设置适配器
        lineRecyclerView.setAdapter(new RecyclerLineAdapter(getContext(), lineData));
        addData();
        View fragmentHomeTwo = LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_home_two, null);
        gridRecyclerView = fragmentHomeTwo.findViewById(R.id.grid_recy_view);
//        View footer = LayoutInflater.from(view.getContext()).inflate(R.layout.footer, null);
//        ContentLoadingProgressBar progressBar = footer.findViewById(R.id.pb_progress);
        //改变mCachedViews的缓存
        gridRecyclerView.setItemViewCacheSize(20);
        gridRecyclerView.setDrawingCacheEnabled(true);//保存绘图，提高速度
        //*public static final int DRAWING_CACHE_QUALITY_HIGH = 1048576;
        gridRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        swipeRefreshLayout = fragmentHomeTwo.findViewById(R.id.swipe_refresh);

        final RecyclerGridAdapter gridAdapter = new RecyclerGridAdapter(getContext(), gridData);
        addGridData();
         /*
                处理刷新闪烁问题
                notifyDataSetChanged + setHasStableIds(true) + 复写getItemId() 方法
                 */
        gridAdapter.setHasStableIds(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //开始刷新，false 取消刷新
            //swipeRefreshLayout.setRefreshing(true);
            //判断是否正在刷新
            //swipeRefreshLayout.isRefreshing();
            @Override
            public void onRefresh() {
                gridData.clear();
                addGridData();
                gridAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
        //为下拉刷新，设置一组颜色
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
        //设置触发刷新的距离
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        //设置滑动的距离
        swipeRefreshLayout.setSlingshotDistance(400);

        gridRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 这里在加入判断，判断是否滑动到底部
                if (isSlideToBottom(gridRecyclerView)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            if(progressBar.getVisibility()==View.GONE) {
//                                //设置为可见的状态
//                                progressBar.setVisibility(View.VISIBLE);
//                            }
                        }
                    }, 1500);
                    //设置为不可见的状态，且不占用任何空间位置
//                    progressBar.setVisibility(View.GONE);
                    addGridData();
                }
                gridAdapter.notifyDataSetChanged();
            }
        });

        /*
        gridRecyclerView.addOnScrollListener(new onLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addGridData();
                    }
                }, 1000);
            }
        });*/

        //设置网格布局样式，2为列数
        gridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        gridRecyclerView.setAdapter(gridAdapter);

        View fragmentHomeThree = LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_home_three, null);
        RecyclerView staggerRecycler = fragmentHomeThree.findViewById(R.id.stagger_recy_view);

        addStaggeredData();
        // 设置瀑布流形式，2为两列
        staggerRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // 设置适配器
        staggerRecycler.setAdapter(new RecyclerStaggeredAdapter(getContext(), staggeredData));

        View fragmentHomeFour = LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_home_four, null);

        views = new ArrayList<>();
        views.add(fragmentHomeOne);
        views.add(fragmentHomeTwo);
        views.add(fragmentHomeThree);
        views.add(fragmentHomeFour);
        titles = new ArrayList<>();
        titles.add("线性布局");
        titles.add("网格布局");
        titles.add("瀑布流");
        titles.add("xxx");

        ViewPageAdapter adapter = new ViewPageAdapter(views, titles);

        for (String title : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }

    private void addData() {
        Map<String, Object> map = null;
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int n = random.nextInt(Data.pics.length);
            map = new HashMap<>();
            map.put("pic", Data.pics[n]);
            map.put("name", Data.names[n]);
            map.put("desc", "我是一只" + Data.names[n]);
            lineData.add(map);
        }
    }

    private void addStaggeredData() {
        Map<String, Object> map = null;
        Random random = new Random();
        String[] str = {
                "瀑布流\n",
                "瀑布流\n瀑布流\n",
                "瀑布流\n瀑布流\n瀑布流\n",
                "瀑布流\n瀑布流\n瀑布流\n瀑布流\n",
        };

        for (int i = 0; i < 30; i++) {
            int n = random.nextInt(Data.pics.length);
            map = new HashMap<>();
            map.put("pic", Data.pics[n]);
            map.put("name", Data.names[n] + "\n" + str[random.nextInt(str.length)]);
            staggeredData.add(map);
        }
    }

    private void addGridData() {
        Map<String, Object> map = null;
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int n = random.nextInt(Data.pics.length);
            map = new HashMap<>();
            map.put("pic", Data.pics[n]);
            map.put("name", Data.names[n]);
            gridData.add(map);
        }
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange();
    }

    /*
    public abstract class onLoadMoreListener extends RecyclerView.OnScrollListener {
        private int countItem;
        private int lastItem;
        private boolean isScolled = false;//是否可以滑动
        private RecyclerView.LayoutManager layoutManager;


       //加载回调方法
       //@param countItem 总数量
       // @param lastItem  最后显示的position
        protected abstract void onLoading(int countItem, int lastItem);

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//      测试这三个参数的作用
//        if (newState==SCROLL_STATE_IDLE){
//            Log.d("test","SCROLL_STATE_IDLE,空闲");
//        }
//        else if (newState==SCROLL_STATE_DRAGGING){
//            Log.d("test","SCROLL_STATE_DRAGGING,拖拽");
//        }
//        else if (newState==SCROLL_STATE_SETTLING){
//            Log.d("test","SCROLL_STATE_SETTLING,固定");
//        }
//        else{
//            Log.d("test","其它");
//        }
            //拖拽或者惯性滑动时isScolled设置为true
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                isScolled = true;
            } else {
                isScolled = false;
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                layoutManager = recyclerView.getLayoutManager();
                countItem = layoutManager.getItemCount();
                lastItem = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }
            if (isScolled && countItem != lastItem && lastItem == countItem - 1) {
                onLoading(countItem, lastItem);
            }
        }
    }*/
}