package com.example.totalapplication.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

/**
 * RecylerView.ViewHolder基类
 *
 */

public class BaseRvViewHolder extends RecyclerView.ViewHolder {
    public BaseRvViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
