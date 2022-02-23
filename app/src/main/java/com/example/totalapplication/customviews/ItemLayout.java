package com.example.totalapplication.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.totalapplication.R;

public class ItemLayout extends ConstraintLayout {

    private TextView mTvItem;
    private ImageView mIvGoto;

    public ItemLayout(@NonNull Context context) {
        this(context,null);
    }

    public ItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemLayout);
        String text = typedArray.getString(R.styleable.ItemLayout_text);
        int itemType = typedArray.getInt(R.styleable.ItemLayout_itemType, 1);
        LayoutInflater.from(context).inflate(R.layout.example_item_layout,this);
        mTvItem = findViewById(R.id.tvItem);
        mIvGoto = findViewById(R.id.ivGoto);
        mTvItem.setText(text);
        switch (itemType){
            case 0:
                //no
                mIvGoto.setVisibility(INVISIBLE);
                break;
            case 1:
                //goto
                mIvGoto.setVisibility(VISIBLE);
                break;
        }
    }
}
