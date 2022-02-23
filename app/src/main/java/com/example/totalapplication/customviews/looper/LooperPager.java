package com.example.totalapplication.customviews.looper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.SizeUtils;

public class LooperPager extends LinearLayout {
    private static final String TAG = "SunLooperPager";
    private LooperViewPager mViewPager;
    private LinearLayout mPointContainer;
    private TextView mTitleTv;
    private BindTitleListener mTitleSetListener = null;
    private InnerAdapter mInnerAdapter = null;
    private OnItemClickListener mOnItemClickListener = null;
    private boolean mIsTitleShow;
    private int mPageShowCount;
    private int mSwitchTime;


    public LooperPager(Context context) {
        this(context, null);
    }

    public LooperPager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LooperPager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.looper_pager_layout, this, true);
        //等价于以下于俩行代码
//        View item = LayoutInflater.from(context).inflate(R.layout.looper_pager_layout, this, false);
//        addView(item);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.looper_style);
        //从TypedArray中读取属性值
        mIsTitleShow = ta.getBoolean(R.styleable.looper_style_is_title_show, true);
        //设置成员变量快捷键 Ctrl+alt+f
        mPageShowCount = ta.getInteger(R.styleable.looper_style_show_page_count, 1);
        mSwitchTime = ta.getInteger(R.styleable.looper_style_switch_time, -1);
        Log.d(TAG,"mIsTitleShow--->"+mIsTitleShow);
        Log.d(TAG,"mPageShowCount--->"+mPageShowCount);
        Log.d(TAG,"mSwitchTime--->"+mSwitchTime);
        ta.recycle();//释放资源
        init();
    }

    private void init() {
        initView();
        initEvent();
    }

    private void initEvent() {
        mViewPager.setPagerItemClickListener(new LooperViewPager.OnPagerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mOnItemClickListener != null&&mInnerAdapter!=null) {
                    int realPosition=position%mInnerAdapter.getDataSize();
                    mOnItemClickListener.onItemClick(realPosition);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //切换的一个回调方式
            }

            @Override
            public void onPageSelected(int position) {
                if (mInnerAdapter != null) {
                    int realPosition = position % mInnerAdapter.getDataSize();
                    if (mTitleSetListener != null) {
                        mTitleTv.setText(mTitleSetListener.getTitle(realPosition));
                    }
                    //切换停下来的一个回调方式，停下来之后设置标题
                    //切换指示器焦点
                    updateIndicator();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //切换状态改变的回调

            }
        });
    }


    public interface BindTitleListener {
        String getTitle(int position);
    }

    public void setData(InnerAdapter innerAdapter, BindTitleListener listener) {
        this.mTitleSetListener = listener;
        mViewPager.setAdapter(innerAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 + 1);
        this.mInnerAdapter = innerAdapter;
        if (listener != null) {
            mTitleTv.setText(listener.getTitle(mViewPager.getCurrentItem() % mInnerAdapter.getDataSize()));
        }
        //可以得到数据的个数,根据数据 的个数动态创建圆点
        updateIndicator();
    }

    public abstract static class InnerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final int realPosition = position % getDataSize();
            View itemView = getLooperView(container, realPosition);
            container.addView(itemView);
            return itemView;
        }

        protected abstract int getDataSize();

        protected abstract View getLooperView(ViewGroup container, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void updateIndicator() {
        if (mInnerAdapter != null && mTitleSetListener != null) {
            int count = mInnerAdapter.getDataSize();
            mPointContainer.removeAllViews();
            for (int i = 0; i < count; i++) {
                View point = new View(getContext());
                // Log.d(TAG,"mViewPager.getCurrentItem()=====>"+mViewPager.getCurrentItem());
                // Log.d(TAG,"mTitleSetListener.getDtaSize()====>"+mInnerAdapter.getDataSize());
                if (mViewPager.getCurrentItem() % mInnerAdapter.getDataSize() == i) {
                    //point.setBackgroundColor(Color.parseColor("#ff0000"));
                    point.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_circle_red));//圆圈
                } else {
                    point.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_circle_white));
                    //point.setBackgroundColor(Color.parseColor("#ffffff"));
                }

                //设置大小
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.dip2px(getContext(), 8),
                        SizeUtils.dip2px(getContext(), 8));
                //LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(SizeUtils.dip2px(getContext(),5),
                //        SizeUtils.dip2px(getContext(),5));//正方形
                //LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(SizeUtils.dip2px(getContext(),10),
                //    SizeUtils.dip2px(getContext(),4));//长方形
                layoutParams.setMargins(SizeUtils.dip2px(getContext(), 5), 0,
                        SizeUtils.dip2px(getContext(), 5), 0);
                point.setLayoutParams(layoutParams);
                //添加到容器里去
                mPointContainer.addView(point);
            }
        }
    }

    private void initView() {
        mViewPager = this.findViewById(R.id.looper_pager_vp);
        if (mSwitchTime!=-1) {
            mViewPager.setDelayTime(mSwitchTime);
        }
        mViewPager.setOffscreenPageLimit(3);//预加载3个
        mViewPager.setPageMargin(SizeUtils.dip2px(getContext(), 20));
        mPointContainer = this.findViewById(R.id.looper_point_container_ll);
        mTitleTv = this.findViewById(R.id.looper_title_tv);
        if (!mIsTitleShow) {
            mTitleTv.setVisibility(GONE);
        }
    }
}
