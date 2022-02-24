package com.example.totalapplication.customviews.waterwave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WaterWaveView2 extends View {
    //存放圆环的集合
    private ArrayList<Wave> mList;
    //界面刷新
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            invalidate();//刷新界面,会执行onDraw方法
        }
    };

    public WaterWaveView2(Context context) {
        this(context, null);
    }

    public WaterWaveView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mList = new ArrayList<Wave>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                deleteItem();
                Wave wave = new Wave(x, y);
                mList.add(wave);
                //刷新界面
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                deleteItem();
                Wave wave1 = new Wave(x1, y1);
                mList.add(wave1);
                invalidate();
                break;
        }
        //处理事件
        return true;
    }

    private void deleteItem() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).paint.getAlpha() == 0) {
                mList.remove(i);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //避免程序一运行就进行绘制
        if (mList.size() > 0) {

            //对集合中的圆环对象循环绘制
            for (Wave wave : mList) {
                canvas.drawCircle(wave.x, wave.y, wave.radius, wave.paint);
                wave.radius += 3;
                //对画笔透明度进行操作
                int alpha = wave.paint.getAlpha();
                if (alpha < 80) {
                    alpha = 0;
                } else {
                    alpha -= 3;
                }
                //设置画笔宽度和透明度
                wave.paint.setStrokeWidth(wave.radius / 8);
                wave.paint.setAlpha(alpha);
                //延迟刷新界面
                mHandler.sendEmptyMessageDelayed(1, 100);
            }
        }
    }

    private static class Wave {
        public float x;//圆心x坐标
        public float y;//圆心y坐标
        public Paint paint; //画圆的画笔
        public float width; //线条宽度
        public int radius; //圆的半径
        public int ranNum;//随机数
        public int[] randomColor = {Color.BLUE, Color.CYAN,
                Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};

        public Wave(float x, float y) {
            this.x = x;
            this.y = y;
            initData();
        }

        /**
         * 初始化数据,每次点击一次都要初始化一次
         */
        private void initData() {
            paint = new Paint();//因为点击一次需要画出不同的圆环
            paint.setAntiAlias(true);//打开抗锯齿
            ranNum = (int) (Math.random() * 6);//[0,5]的随机数
            paint.setColor(randomColor[ranNum]);//设置画笔的颜色
            paint.setStyle(Paint.Style.STROKE);//描边
            paint.setStrokeWidth(width);//设置描边宽度
            paint.setAlpha(255);//透明度的设置(0-255),0为完全透明
            radius = 0;//初始化
            width = 0;
        }
    }
}
