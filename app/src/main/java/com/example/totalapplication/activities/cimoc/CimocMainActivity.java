package com.example.totalapplication.activities.cimoc;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.FragmentUtil;
import com.example.totalapplication.Utils.GlideCacheUtil;
import com.example.totalapplication.Utils.StatusBarUtil;
import com.example.totalapplication.base.BaseActivity;
import com.example.totalapplication.interfaces.MyTouchInterface;
import java.util.ArrayList;
import java.util.List;

public class CimocMainActivity extends BaseActivity {
        public static final int FRAGMENT_HOME = 0;
        public static final int FRAGMENT_BOOKSHELF = 1;
        public static final int FRAGMENT_USER = 2;
        private static final int containerId = R.id.a_main_fl_container;
        private FragmentManager mFragmentManager;
        private Fragment mFragment;
        private List<MyTouchInterface> mMyTouchInterfaces = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_cimoc);
            StatusBarUtil.setRootViewFitsSystemWindows(this, false);
            initFragment();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            GlideCacheUtil.getInstance().clearImageAllCache(this);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            for (MyTouchInterface listener : mMyTouchInterfaces) {
                listener.onTouchEvent(ev);
            }
            return super.dispatchTouchEvent(ev);
        }

        public void initFragment() {
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(containerId, FragmentUtil.getFragment(FRAGMENT_HOME))
                    .commit();
            mFragment = FragmentUtil.getFragment(FRAGMENT_HOME);
        }

        public void switchFragment(Fragment to) {
            if (mFragment != to) {
                mFragment.onPause();
                FragmentTransaction transaction = mFragmentManager.beginTransaction().setCustomAnimations(
                        android.R.anim.fade_in, android.R.anim.fade_out);
                // 先判断是否被add过
                if (!to.isAdded()) {
                    transaction.hide(mFragment).add(containerId, to).commit();
                } else {
                    to.onResume();
                    transaction.hide(mFragment).show(to).commit();
                }
                mFragment = to;
            }
        }

        public void registerMyTouchListener(MyTouchInterface listener) {
            mMyTouchInterfaces.add(listener);
        }

        public void unRegisterMyTouchListener(MyTouchInterface listener) {
            mMyTouchInterfaces.remove( listener );
        }

}
