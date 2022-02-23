package com.example.totalapplication.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.totalapplication.R;

public class ValueView extends ConstraintLayout {

    private TextView mTvTitle;
    private TextView mTvValue;

    public ValueView(@NonNull Context context) {
        this(context, null);
    }

    public ValueView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValueView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ValueView);
        String title = typedArray.getString(R.styleable.ValueView_text);
        String value = typedArray.getString(R.styleable.ValueView_value);
        LayoutInflater.from(context).inflate(R.layout.example_value_view_layout, this);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvValue = findViewById(R.id.tvValue);
        mTvTitle.setText(title);
        mTvValue.setText(value);
    }

    public void setTitle(String text) {
        mTvTitle.setText(text);
    }

    public void setValue(String text) {
        mTvValue.setText(text);
    }
}
