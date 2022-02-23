package com.example.totalapplication.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totalapplication.R;
import com.example.totalapplication.activities.AnimalDetailActivity;

import java.util.List;
import java.util.Map;

public class RecyclerStaggeredAdapter extends RecyclerView.Adapter<RecyclerStaggeredAdapter.ViewHolder> {

    Context context;
    List<Map<String, Object>> data;

    public RecyclerStaggeredAdapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_one_view_3_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(context).load(data.get(i).get("pic")).into(holder.img);
        holder.name.setText(data.get(i).get("name").toString());

        final Intent intent = new Intent(context, AnimalDetailActivity.class);
        intent.putExtra("pic", (int) data.get(i).get("pic"));
        intent.putExtra("name", (String) data.get(i).get("name"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) context,holder.img,"shared").toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_item_3_pic);
            name = itemView.findViewById(R.id.tv_recy_item_3_name);

        }
    }
}