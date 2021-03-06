package com.example.totalapplication.activities.cimoc;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.totalapplication.R;
import com.example.totalapplication.Utils.ActivityUtil;
import com.example.totalapplication.Utils.ComicUtil;
import com.example.totalapplication.Utils.ToastUtils;
import com.example.totalapplication.adapters.cimoc.CimocDetailAdapter;
import com.example.totalapplication.base.BaseActivity;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.domain.entities.Chapter;
import com.example.totalapplication.interfaces.DataLoadInterface;
import com.example.totalapplication.loaders.BaseComicLoader;
import com.example.totalapplication.loaders.ChineseComicLoader;
import com.example.totalapplication.loaders.JapaneseComicLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CimocDetailActivity extends BaseActivity {
    private Chapter chapter;
    private ActionBar actionBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private BaseComicLoader comicLoader;
    private boolean isRenewAdapter = false;
    private CimocDetailAdapter mCimocDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        comicLoader.destroyLoader();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            default:
                break;
        }
        return true;
    }

    private void initData() {
        // ????????????ID
        recyclerView = findViewById(R.id.a_comic_detail_ry);
        swipeRefreshLayout = findViewById(R.id.a_comic_detail_swipeRefreshLayout);
        floatingActionButton = findViewById(R.id.a_comic_detail_fb);
        chapter = getIntent().getParcelableExtra(BasicConfig.INTENT_DATA_NAME_CHAPTER);
        // ??????toolbar
        actionBar = setActivityToolbar(R.id.fg_bs_toolbar, true, true);
        // ?????????swipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refreshColor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRenewAdapter = false;
                comicLoader.destroyLoader();
                reloadData(chapter);
            }
        });
        // ?????????floatingActionButton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Chapter nextChapter = getNextChapter();
                    if (nextChapter == null) {
                        ToastUtils.shortToast(CimocDetailActivity.this, "?????????????????????");
                    } else {
                        reloadData(nextChapter);
                    }
                } catch (Exception e) {
                    ToastUtils.shortToast(CimocDetailActivity.this, "????????????????????????");
                }
            }
        });
        // ??????URL
        generateUrl();
    }

    /**
     * ??????URL
     */
    private void generateUrl() {
        if (chapter.getComicType() == 0) {
            comicLoader = new ChineseComicLoader(CimocDetailActivity.this);
        } else {
            comicLoader = new JapaneseComicLoader(CimocDetailActivity.this);
        }
        actionBar.setTitle(chapter.getChapterName());
        swipeRefreshLayout.setRefreshing(true);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                comicLoader.loadPicsList(chapter.getChapterUrl(), new DataLoadInterface() {
                    @Override
                    public void onLoadFinished(Object o, Exception e) {
                        if (e == null) {
                            if (!isRenewAdapter) {
                                isRenewAdapter = true;
                                List<String> picsList;
                                if (comicLoader instanceof JapaneseComicLoader) {
                                    picsList = new ArrayList<>();
                                    picsList.add((String) o);
                                } else {
                                    picsList = (List<String>) o;
                                }
                                chapter.setPicsList(picsList);
                                loadData();
                            } else {
                                /**
                                 * Bug???????????? 2019/4/7 19:16
                                 * ????????????chapter.getPicsList????????????????????????
                                 * ???ComicDetailAdapter??????addItem????????????picsList.add????????????
                                 * ???picsList???ComicDetailAdapter????????????????????????this.picsList = picsList????????????
                                 * ????????????ComicDetailAdapter??????picsList????????????chapter.getPicsList()???????????????????????????
                                 * ????????????????????????????????????ComicDetailAdapter??????addItem????????????
                                 */
                                // chapter.getPicsList().add((String) o);
                                mCimocDetailAdapter.addItemBack((String) o);
                            }
                            ActivityUtil.saveDataWithSP(CimocDetailActivity.this, chapter.getComicName(), BasicConfig.KEY_NAME_GENERAL,
                                    chapter.getChapterName(), chapter.getCurrentIndex());
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * ????????????
     */
    private void loadData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CimocDetailActivity.this);
                mCimocDetailAdapter = new CimocDetailAdapter(chapter.getPicsList(), R.layout.item_comic_detail,
                        CimocDetailActivity.this, new DataLoadInterface() {
                    @Override
                    public void onLoadFinished(Object o, Exception e) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
                if (comicLoader instanceof ChineseComicLoader) {
                    mCimocDetailAdapter.setShowHint(true);
                } else {
                    mCimocDetailAdapter.setShowHint(false);
                }
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mCimocDetailAdapter);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Glide.with(CimocDetailActivity.this).resumeRequests();
//                        if(newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING ){
//                            Glide.with(ComicDetailActivity.this).resumeRequests();
//                        } else {
//                            Glide.with(ComicDetailActivity.this).pauseRequests();
//                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (isSlideToBottom(recyclerView)) {
                            // ????????????
                        } else {
                            // ????????????
                        }
                    }
                });
            }
        });
    }

    /**
     * ??????????????????
     *
     * @param chapter
     */
    private void reloadData(Chapter chapter) {
        this.chapter = chapter;
        isRenewAdapter = false;
        comicLoader.destroyLoader();
        generateUrl();
    }

    /**
     * ??????????????????
     *
     * @param recyclerView
     * @return
     */
    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    private Chapter getNextChapter() {
        int currentIndex = chapter.getCurrentIndex();
        if (currentIndex > 0) {
            return ComicUtil.currentComic.getChapterList().get(currentIndex - 1);
        } else {
            return null;
        }
    }
}
