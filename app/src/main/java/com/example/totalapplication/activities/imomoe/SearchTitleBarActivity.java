package com.example.totalapplication.activities.imomoe;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.totalapplication.R;
import com.example.totalapplication.Utils.ResourceAttributesUtils;
import com.example.totalapplication.adapters.imomoe.SearchTitleBarAdapter;
import com.example.totalapplication.api.imomoeAPI.ImomoeSearch;
import com.example.totalapplication.base.MyImomoeApplication;
import com.example.totalapplication.customviews.imomoe.SearchTitleBar;
import com.tencent.bugly.Bugly;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchTitleBarActivity extends AppCompatActivity
        implements SearchTitleBar.OnSearchTitleBarListener {

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_title_bar)
    SearchTitleBar titleBar;

    private SearchTitleBarAdapter adapter = null;
    private ArrayList<ImomoeSearch> dataList = null;

    private int tagetHeight = 0;
    private int resetTagetHeight = 0;
    private View firstVisibleView = null;
    private int searchBarState = 0;// 0初始状态  1展开状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_title_bar);

        ButterKnife.bind(this);

        tagetHeight = (int) (ResourceAttributesUtils.getResourcesDimension(R.dimen.search_target_height) * 0.45f);
        resetTagetHeight = (int) (ResourceAttributesUtils.getResourcesDimension(R.dimen.search_target_height) * 0.15f);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        registerListener();

        // dataList = getIntent().getSerializableExtra("bangumi_data");
        dataList = MyImomoeApplication.arrayList;
        // initDatas();
        initAdapter();
    }

    private void registerListener() {
        titleBar.setOnSearchTitleBarListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                setupTitleBar();
            }
        });
    }

    public void setupTitleBar() {
        if (recyclerView == null || titleBar == null) {
            return;
        }

        if (firstVisibleView == null) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            firstVisibleView = layoutManager.getChildAt(0);
        }

        if (firstVisibleView == null) {
            return;
        }

        int top = firstVisibleView.getTop();

        if (top > (-resetTagetHeight)
                && searchBarState == 1) {
            searchBarState = 0;
            titleBar.performAnimate(false);
        } else if (top <= (-resetTagetHeight)
                && top >= (-tagetHeight)) {
            titleBar.hideDivide();
        } else if (top < (-tagetHeight)
                && searchBarState == 0) {
            searchBarState = 1;
            titleBar.performAnimate(true);
        }

        if (Math.abs(top) <= tagetHeight) {
            float precent = (Math.abs(top) * 1.0f / tagetHeight);
            int alpha = (int) (precent * 255);
            titleBar.setBackgroundColor(Color.argb(alpha, 66, 66, 66));
        } else {
            titleBar.setBackgroundColor(Color.argb(255, 255, 255, 255));
        }
    }

    private void initAdapter() {
        adapter = new SearchTitleBarAdapter(this, dataList);
        adapter.setOnItemClickListener(new SearchTitleBarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchTitleBarActivity.this, PlayerActivity.class);
                intent.putExtra("bangumi_data", dataList.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickSearch() {
        Intent intent = new Intent(this, SearchDetailActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                    Pair.create(titleBar.getSearchLayout()
                            , ResourceAttributesUtils.getResourcesString(R.string.trans_anim_search_layout)),
                    Pair.create(titleBar.getSearchTv()
                            , ResourceAttributesUtils.getResourcesString(R.string.trans_anim_search_tv)),
                    Pair.create(titleBar.getSearchIv()
                            , ResourceAttributesUtils.getResourcesString(R.string.trans_anim_search_iv)),
                    Pair.create(titleBar.getSearchEt()
                            , ResourceAttributesUtils.getResourcesString(R.string.trans_anim_search_edit))).toBundle());
        } else {
            startActivity(intent);
        }
    }
}
