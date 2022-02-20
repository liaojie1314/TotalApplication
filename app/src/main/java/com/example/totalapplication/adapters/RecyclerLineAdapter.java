package com.example.totalapplication.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.totalapplication.R;
import java.util.List;
import java.util.Map;

public class RecyclerLineAdapter extends RecyclerView.Adapter<RecyclerLineAdapter.ViewHolder> {

    Context context;
    List<Map<String, Object>> data;

    public RecyclerLineAdapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_one_view_1_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Glide.with(context).load(data.get(i).get("pic")).into(holder.img);
        holder.name.setText(data.get(i).get("name").toString());
        holder.desc.setText(data.get(i).get("desc").toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_recy_item_1_pic);
            name = itemView.findViewById(R.id.tv_recy_item_1_name);
            desc = itemView.findViewById(R.id.tv_recy_item_1_desc);
        }
    }
}
