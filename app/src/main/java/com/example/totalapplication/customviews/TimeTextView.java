package com.example.totalapplication.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.DateTimeUtils;
import com.example.totalapplication.Utils.SizeUtils;

public class TimeTextView extends View {
    private static int TEXT_SIZE = 12;
    private String mText;
    private Paint.Align mAlign;
    private Paint mTextPaint;

    public TimeTextView(Context context) {
        this(context, null);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mText = "00:00";
        mAlign = Paint.Align.LEFT;
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(SizeUtils.dip2px(context, (float) TEXT_SIZE));
        mTextPaint.setColor(ContextCompat.getColor(context, R.color.colorTextForeground));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setTextAlign(mAlign);
        Float x = 0f;
        if (mTextPaint.getTextAlign() == Paint.Align.RIGHT) {
            x = (float) getWidth();
        }
        canvas.drawText(mText, x, SizeUtils.dip2px(getContext(),(float) TEXT_SIZE), mTextPaint);
    }

    public void setText(int newProgress) {
        mText = DateTimeUtils.stringForTime(newProgress);
        invalidate();
    }

    public void setAlignRight() {
        mAlign = Paint.Align.RIGHT;
    }
}
