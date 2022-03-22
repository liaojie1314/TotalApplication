package com.example.totalapplication.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.totalapplication.R;
import com.example.totalapplication.fragments.subfragments.JapaneseComicSubFragment;
import com.example.totalapplication.fragments.subfragments.RecommendComicSubFragment;
import com.example.totalapplication.managers.MagicIndicatorManager;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CimocHomeFragment extends BaseFragment {
    @BindView(R.id.fg_home_magicIndicator)
    public MagicIndicator magicIndicator;
    @BindView(R.id.fg_home_viewPager)
    public ViewPager viewPager;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_cimoc, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initTabPager();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initTabPager() {
        setDarkStatus(true);
        MagicIndicatorManager indicatorManager = MagicIndicatorManager.getInstance(this);
        indicatorManager.initViewPager(viewPager, getFragmentList(), getTitles());
        indicatorManager.initMagicIndicator(magicIndicator);
    }

    private String[] getTitles() {
        String[] titles = {"推荐", "日漫"};
        return titles;
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RecommendComicSubFragment());
        fragmentList.add(new JapaneseComicSubFragment());
        return fragmentList;
    }
}
