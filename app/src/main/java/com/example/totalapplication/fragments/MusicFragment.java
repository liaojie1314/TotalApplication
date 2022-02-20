package com.example.totalapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.totalapplication.R;
import com.example.totalapplication.views.LooperPager;
import com.example.totalapplication.views.PagerItem;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {
    private LooperPager mLooperPager;
    private List<PagerItem> mData = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        initEvent();
    }

    private void initEvent() {
        if (mLooperPager != null) {
            mLooperPager.setOnItemClickListener(new LooperPager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getContext(), "点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
                    //todo:根据交互业务实现具体逻辑
                }
            });
        }
    }

    private void initData() {
        mData.add(new PagerItem("第一张图片", R.mipmap.pic1));
        mData.add(new PagerItem("第2张图片", R.mipmap.pic2));
        mData.add(new PagerItem("第三张图片", R.mipmap.pic3));
        mData.add(new PagerItem("第4张图片", R.mipmap.pic4));
    }

    private LooperPager.InnerAdapter mInnerAdapter = new LooperPager.InnerAdapter() {
        @Override
        protected int getDataSize() {
            return mData.size();
        }

        @Override
        protected View getSunView(ViewGroup container, int position) {
            ImageView iv = new ImageView(container.getContext());
            iv.setImageResource(mData.get(position).getPicResId());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }
    };

    private void initView(View view) {
        mLooperPager = view.findViewById(R.id.sun_looper_pager);
        mLooperPager.setData(mInnerAdapter, new LooperPager.BindTitleListener() {
            @Override
            public String getTitle(int position) {
                return mData.get(position).getTitle();
            }
        });
    }
}