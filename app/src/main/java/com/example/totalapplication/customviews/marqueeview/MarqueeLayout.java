package com.example.totalapplication.customviews.marqueeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MarqueeLayout extends ViewGroup {

    private int margin = 50;

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public MarqueeLayout(Context context) {
        this(context, null);
    }

    public MarqueeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化 加载属性
    }

    private int maxHeight = 0;
    private int totalChildrenWidth = 0;

    private static class Position {
        public Position(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int left;
        public int top;
        public int right;
        public int bottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量
        //测量孩子
        totalChildrenWidth = 0;
        int childCount = getChildCount();
        int startLeft = 0;
        for (int i = 0; i < childCount; i++) {
            //拿到孩子进行测量
            View item = getChildAt(i);
            //如果是隐藏 就不测量了
            if (item.getVisibility() == GONE) {
                continue;
            }
            //进行测量
            int childWidthSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
            item.measure(childWidthSpec, heightMeasureSpec);
            int itemHeight = item.getMeasuredHeight();
            int itemMeasuredWidth = item.getMeasuredWidth();
            totalChildrenWidth += itemMeasuredWidth + margin * 2;
            if (itemHeight > maxHeight) {
                maxHeight = itemHeight;
            }
            //创建item的position
            Position itemPosition = new Position(startLeft, 0, startLeft + itemMeasuredWidth, itemHeight);
            item.setTag(itemPosition);
            startLeft += itemMeasuredWidth + margin * 2;
        }
        //测量自己
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int selfHeightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, selfHeightSpec);
        //全部测量完 容器也测量完了
        //进行判断是否需要进行滚动
        int selfWidth = getMeasuredWidth();
        if (totalChildrenWidth>selfWidth){
            //进行滚动
            startRun();
        }else {
            removeCallbacks(runTask);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //从window中解绑
        removeCallbacks(runTask);
    }

    //    private boolean running = false;
//
//    private final Runnable runTask = new Runnable() {
//        @Override
//        public void run() {
//            defaultStartIndex -= 3;
//            requestLayout();
//            //边界处理
//            //当开始位置是整个内容减去容器长度的时候 要从容器最右边开始
//            if (defaultStartIndex <= -totalChildrenWidth) {
//                defaultStartIndex = getMeasuredWidth();
//            }
//            postDelayed(this, 50);
//        }
//    };
//
//    public boolean isRunning() {
//        return this.running;
//    }
//
//    public void startRun() {
//        running = true;
//        removeCallbacks(runTask);
//        post(runTask);
//    }
//
//    public void stopRun() {
//        removeCallbacks(runTask);
//        running = false;
//        defaultStartIndex = 0;
//    }
//
//    private int defaultStartIndex = 0;

    private void startRun() {
        removeCallbacks(runTask);
        post(runTask);
    }

    private final Runnable runTask = new Runnable() {
        @Override
        public void run() {
            //重新布局
            doLayout();
            postDelayed(this, 50);
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        doLayout();
    }

    //移动距离 px
    private int stepSize = 3;

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    private void doLayout() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View item = getChildAt(i);
            //如果这个孩子是不可见的 不占位置的GONE 不进行摆放
            if (item.getVisibility() == GONE) {
                continue;
            }
            //摆放
            Position position = (Position) item.getTag();
            item.layout(position.left, position.top, position.right, position.bottom);
            //改变position的值
            //判断位置 是否超出范围
            //position.right是否<0
            if (position.right < 0) {
                //移动到布局最后
                position.right = totalChildrenWidth;
                position.left = position.right - item.getMeasuredWidth();
            } else {
                //否则向左移动
                position.left -= stepSize;
                position.right = position.left + item.getMeasuredWidth();

            }
        }
    }
}
