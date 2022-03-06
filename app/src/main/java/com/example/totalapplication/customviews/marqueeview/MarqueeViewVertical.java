package com.example.totalapplication.customviews.marqueeview;

import static com.example.totalapplication.customviews.marqueeview.MarqueeViewVertical.RunDirection.BOTTOM_2_TOP;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

public class MarqueeViewVertical extends ViewGroup {

    private static final String TAG = "MarqueeViewVertical";
    private TextView mFirstTv;
    private TextView mSecondTv;
    private String[] mTextArray;

    public MarqueeViewVertical(Context context) {
        this(context, null);
    }

    public MarqueeViewVertical(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeViewVertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        init();
    }

    private void init() {
        //准备俩个TextView用于显示文字
        mFirstTv = new TextView(getContext());
        mFirstTv.setTextColor(Color.WHITE);
        mSecondTv = new TextView(getContext());
        mSecondTv.setTextColor(Color.RED);
        //属性设置
        //TODO:字号,颜色

    }

    private int currentTextIndex = 0;

    public void setTextArray(String[] textArray) {
        if (textArray.length == 0) {
            throw new IllegalArgumentException("Text array's length must greater than one.");
        }
        this.mTextArray = textArray;
        //设置内容
        //内容可能是一行 俩行 或者多行
        mFirstTv.setText(textArray[0]);
        if (textArray.length > 1) {
            mSecondTv.setText(textArray[1]);
            currentTextIndex = 1;
        }
        //从上往下:
        //第一个内容
        //========
        //第二个内容
        //从下往上:
        //第二个内容
        //第一个内容
        //========

    }

    public static final int DEFAULT_LOOP_DURATION = 2000;
    private int loopDuration = DEFAULT_LOOP_DURATION;

    public int getLoopDuration() {
        return loopDuration;
    }

    public void setLoopDuration(int loopDuration) {
        this.loopDuration = loopDuration;
    }

    public RunDirection getCurrentDirection() {
        return mCurrentDirection;
    }

    public void setCurrentDirection(RunDirection currentDirection) {
        mCurrentDirection = currentDirection;
    }

    private void doAnimator() {
        float targetPosition;
        //判断方向
        if (mCurrentDirection == BOTTOM_2_TOP) {
            //默认
            //从0.0f开始 滚动一个控件的负高度
            //滚动前要判断方向
            targetPosition = -mFirstTv.getMeasuredHeight();
//        firstAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                Log.d(TAG, "animator update value ==>" + animatedValue);
//            }
//        });
//            firstAnimator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    Log.d(TAG, "动画结束...");
//                }
//            });
        } else {
            //从下往上
            //从0.0f开始 滚动一个控件的负高度
            //滚动前要判断方向
            targetPosition = mFirstTv.getMeasuredHeight();
        }
        ObjectAnimator firstAnimator = ObjectAnimator.ofFloat(mFirstTv,
                "translationY", 0.0f, targetPosition);
        ObjectAnimator secondAnimator = ObjectAnimator.ofFloat(mSecondTv,
                "translationY", 0.0f, targetPosition);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(firstAnimator, secondAnimator);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束 准备下一次动画切换
                removeCallbacks(runTask);
                postDelayed(runTask, loopDuration);
            }
        });
    }

    private Runnable runTask = new Runnable() {
        @Override
        public void run() {
            //判断方向
            //从下往上滚动
            //播放内容之前 需要进行内容切换
            String firstText = mTextArray[currentTextIndex];
            mFirstTv.setText(firstText);
            //第二个内容处理 瞬移
            currentTextIndex++;
            //要处理边界 如果已经到最后一个 就要回到第一个
            if (currentTextIndex >= mTextArray.length) {
                currentTextIndex = 0;
            }
            String secondText = mTextArray[currentTextIndex];
            mSecondTv.setText(secondText);
            doAnimator();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量孩子
        int childHeightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST);
        mFirstTv.measure(widthMeasureSpec, childHeightSpec);
        mSecondTv.measure(widthMeasureSpec, childHeightSpec);
        //高度
        int firstHeight = mFirstTv.getMeasuredHeight();
        int secondHeight = mSecondTv.getMeasuredHeight();
        //测量自己
        int maxHeight = Math.max(firstHeight, secondHeight);
        int selfHeightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, selfHeightSpec);
    }

    public enum RunDirection {
        BOTTOM_2_TOP, TOP_2_BOTTOM
    }

    private RunDirection mCurrentDirection = BOTTOM_2_TOP;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放内容
        //分方向,是从下往上滚动 还是从上往下滚动
        mFirstTv.layout(0, 0, mFirstTv.getMeasuredWidth(), mFirstTv.getMeasuredHeight());
        if (mCurrentDirection == BOTTOM_2_TOP) {
            //从下往上 first就会摆放在中间 second 就会摆放到底部
            mSecondTv.layout(0, mFirstTv.getMeasuredHeight(),
                    mSecondTv.getMeasuredWidth(),
                    mFirstTv.getMeasuredHeight() + mSecondTv.getMeasuredHeight());
        } else {
            //从上往下 first 还是摆放在中间 second 摆放在顶部
            mSecondTv.layout(0, -mSecondTv.getMeasuredHeight(),
                    mSecondTv.getMeasuredWidth(), 0);
        }
        //在做动画切换时 再做更新内容
        //什么时候去滚动
        //判断内容行数 如果一行以上 则要进行滚动
        // 如果只有一行就不滚动
        if (this.mTextArray.length > 1) {
            doAnimator();
        }
    }
}
