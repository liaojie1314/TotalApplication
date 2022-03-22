package com.example.totalapplication.loaders;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import com.example.totalapplication.controllers.AWCoreController;
import com.example.totalapplication.domain.entities.Chapter;
import com.example.totalapplication.domain.entities.Comic;
import com.example.totalapplication.interfaces.DataLoadInterface;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseComicLoader {
    protected final static String TAG = "测试5";
    protected final static int LOAD_SUCCESS = 123;
    protected final static int LOAD_FAILURE = 321;
    protected List<Comic> comicList;
    protected List<Chapter> chapterList;
    protected List<String> picsList;
    protected String homepage = "";
    protected String staticSrc = "";
    protected boolean isLoaded = false;
    protected int loadType;
    protected String currentUrl = "";
    protected AWCoreController awCoreController;
    private AWCoreController.OnAgentDataLoadingListener loadingListener;
    private Fragment mFragment;
    private Activity mActivity;
    private boolean isActivity;
    protected DataLoadInterface mDataLoadInterface;

    public BaseComicLoader(Activity mActivity) {
        this.mActivity = mActivity;
        this.isActivity = true;
        initData();
    }

    public BaseComicLoader(Fragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = mFragment.getActivity();
        this.isActivity = false;
        initData();
    }

    private void initData() {
        comicList = new ArrayList<>();
        chapterList = new ArrayList<>();
        picsList = new ArrayList<>();
        setHomepage();
        setStaticSrc();
        loadingListener = new AWCoreController.OnAgentDataLoadingListener() {
            @Override
            public void onWebLoading(String html) {
                if (!isLoaded) {
                    parseWebData(html);
                }
            }
        };
        if (isActivity) {
            awCoreController = new AWCoreController(mActivity, loadingListener);
        } else {
            awCoreController = new AWCoreController(mFragment, loadingListener);
        }
    }

    protected void parseWebData(final String htmlData) {
        if (loadType == 0) {
            loadComicData(htmlData);
        } else if (loadType == 2) {
            loadPicsData(htmlData);
        }
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_SUCCESS:
                    mDataLoadInterface.onLoadFinished(msg.obj, null);
                    break;
                case LOAD_FAILURE:
                    mDataLoadInterface.onLoadFinished(null, (Exception) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    public boolean isLoaded() {
        return this.isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void pause() {
        this.isLoaded = false;
    }

    public void loadComicList(final String url, final DataLoadInterface dataLoadInterface) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadType = 0;
                BaseComicLoader.this.mDataLoadInterface = dataLoadInterface;
                load(url);
            }
        });
    }

    public void loadChapterList(final Comic comic, final boolean usingWebCore, final DataLoadInterface dataLoadInterface) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadType = 1;
                BaseComicLoader.this.mDataLoadInterface = dataLoadInterface;
                loadChapterData(comic);
            }
        });
    }

    public void loadPicsList(final String url, final DataLoadInterface dataLoadInterface) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadType = 2;
                currentUrl = url;
                picsList = new ArrayList<>();
                BaseComicLoader.this.mDataLoadInterface = dataLoadInterface;
                load(url);
            }
        });
    }

    public void destroyLoader() {
        if (awCoreController != null) {
            isLoaded = false;
            awCoreController.destroy();
        }
    }

    protected void load(String url) {
        isLoaded = false;
        awCoreController.loadUrl(url);
    }

    protected Message getMessage(Object obj, int what) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        return message;
    }

    protected abstract void setStaticSrc();
    protected abstract void setHomepage();
    protected abstract void loadChapterData(final Comic comic);
    protected abstract void loadComicData(final String html);
    protected abstract void loadPicsData(final String html);
}
