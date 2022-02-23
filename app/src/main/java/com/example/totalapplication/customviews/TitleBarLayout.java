package com.example.totalapplication.customviews;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.totalapplication.R;

public class TitleBarLayout extends ConstraintLayout {

    private TextView mTvTitleBar;
    private ImageView mBtnBack;

    public TitleBarLayout(Context context) {
        this(context, null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBarLayout);
        String text = typedArray.getString(R.styleable.TitleBarLayout_text);
        LayoutInflater.from(context).inflate(R.layout.example_titlebar_layout, this);
        mTvTitleBar = findViewById(R.id.tvTitleBar);
        mBtnBack = findViewById(R.id.btnBack);
        mTvTitleBar.setText(text);
        mBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "alpha", 0F, 1.0F);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                Activity activity = (Activity) context;
                activity.finish();
            }
        });
    }

    public void setTitleBar(String text) {
        mTvTitleBar.setText(text);
    }
}
