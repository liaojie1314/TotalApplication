package com.example.totalapplication.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.totalapplication.R;
import com.example.totalapplication.Utils.FragmentUtil;
import com.example.totalapplication.activities.cimoc.CimocMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationFragment extends BaseFragment {
    private BottomNavigationView navigationView;
    private CimocMainActivity cimocMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottomnavigation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cimocMainActivity = (CimocMainActivity) getActivity();
        navigationView = getActivity().findViewById(R.id.fragment_bm_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 设置导航栏图标和字体样式
        navigationView.setItemIconTintList(null);
        ColorStateList colorStateList = getResources().getColorStateList(R.color.bm_navigation_text);
        navigationView.setItemTextColor(colorStateList);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.bm_navigation_home:
                    cimocMainActivity.switchFragment(FragmentUtil.getFragment(CimocMainActivity.FRAGMENT_HOME));
                    break;
                case R.id.bm_navigation_bookshelf:
                    cimocMainActivity.switchFragment(FragmentUtil.getFragment(CimocMainActivity.FRAGMENT_BOOKSHELF));
                    break;
                case R.id.bm_navigation_user:
                    cimocMainActivity.switchFragment(FragmentUtil.getFragment(CimocMainActivity.FRAGMENT_USER));
                    break;
                default:
                    return false;
            }
            return true;
        }
    };
}
