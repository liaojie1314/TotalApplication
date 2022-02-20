package com.example.totalapplication.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totalapplication.R;

import java.util.List;
import java.util.Map;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder> {

    Context context;
    List<Map<String, Object>> data;
//    private final static int TYPE_CONTENT = 0;//正常内容
//    private final static int TYPE_FOOTER = 1;//下拉刷新


    public RecyclerGridAdapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == data.size()) {
//            return TYPE_FOOTER;
//        }
//        return TYPE_CONTENT;
//    }

    @Override
    public long getItemId(int position) {//position对应数据源集合的索引
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        if (i == TYPE_FOOTER) {
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer, viewGroup, false);
//            return new ViewHolder(view);
//        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_one_view_2_item, viewGroup, false);
            return new ViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
//        if (getItemViewType(i)==TYPE_FOOTER){
//
//        }
//        else{
            Glide.with(context).load(data.get(i).get("pic")).into(holder.img);
            holder.name.setText(data.get(i).get("name").toString());
//        }
    }

    @Override
    public int getItemCount() {
        return data.size() /*+ 1*/;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_item_2_pic);
            name = itemView.findViewById(R.id.tv_recy_item_2_name);
            //ContentLoadingProgressBar progressBar = itemView.findViewById(R.id.pb_progress);
        }
    }
}
