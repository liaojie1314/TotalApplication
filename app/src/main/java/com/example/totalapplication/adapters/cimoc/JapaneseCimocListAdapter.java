package com.example.totalapplication.adapters.cimoc;

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
import com.bumptech.glide.request.RequestOptions;
import com.example.totalapplication.R;
import com.example.totalapplication.Utils.ActivityUtil;
import com.example.totalapplication.activities.cimoc.CimocIntroActivity;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.config.PictureConfig;
import com.example.totalapplication.domain.entities.Comic;
import java.util.List;

public class JapaneseCimocListAdapter extends RecyclerView.Adapter<JapaneseCimocListAdapter.ViewHolder> {
    private List<Comic> comicList;
    private int itemLayout;
    private Context mContext;

    public JapaneseCimocListAdapter(List<Comic> comicList, int itemLayout, Context mContext) {
        this.comicList = comicList;
        this.itemLayout = itemLayout;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Comic comic = comicList.get(i);
        RequestOptions options = new RequestOptions()
                .placeholder(mContext.getResources().getDrawable(R.drawable.ic_pic_preload))
                .override(290, 420);
        Glide.with(mContext)
                .load(comic.getComicPosterUrl())
                .apply(options)
                .thumbnail(PictureConfig.PIC_MIDDLE)
                .into(viewHolder.comicImageView);
        viewHolder.comicNameTextView.setText(ActivityUtil.getSubString(comic.getComicName(), 6, true));
        viewHolder.comicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comic.setComicType(1);
                Intent intent = new Intent(mContext, CimocIntroActivity.class);
                intent.putExtra(BasicConfig.INTENT_DATA_NAME_COMIC, comic);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList == null ? 0 : comicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView comicImageView;
        public TextView comicNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comicImageView = itemView.findViewById(R.id.item_home_jp_img);
            comicNameTextView = itemView.findViewById(R.id.item_home_jp_comic_name);
        }
    }
}
