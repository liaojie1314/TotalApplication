package com.example.totalapplication.adapters.cimoc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.totalapplication.R;
import com.example.totalapplication.activities.cimoc.CimocIntroActivity;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.config.PictureConfig;
import com.example.totalapplication.domain.entities.Comic;
import com.example.totalapplication.domain.entities.ComicCollection;

import java.util.List;

public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.ViewHolder> {

    private List<ComicCollection> userComicLikeList;
    private Context mContext;

    public BookShelfAdapter(List<ComicCollection> userComicLikeList, Context mContext) {
        this.userComicLikeList = userComicLikeList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookshelf, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ComicCollection userComicLike = userComicLikeList.get(i);
        viewHolder.comicNameTextView.setText(userComicLike.getComicName());
        Glide.with(mContext)
                .load(userComicLike.getComicPosterUrl())
                .thumbnail(PictureConfig.PIC_MIDDLE)
                .into(viewHolder.comicImageView);
        viewHolder.containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comic comic = new Comic();
                comic.setComicName(userComicLike.getComicName());
                comic.setComicPosterUrl(userComicLike.getComicPosterUrl());
                comic.setComicWebUrl(userComicLike.getComicWebUrl());
                comic.setComicType(userComicLike.getComicType());
                Intent intent = new Intent(mContext, CimocIntroActivity.class);
                intent.putExtra(BasicConfig.INTENT_DATA_NAME_COMIC, comic);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userComicLikeList == null ? 0 : userComicLikeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView comicImageView;
        private TextView comicNameTextView;
        private ConstraintLayout containerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comicImageView = itemView.findViewById(R.id.item_bs_img);
            comicNameTextView = itemView.findViewById(R.id.item_bs_name);
            containerView = itemView.findViewById(R.id.item_bs_container);
        }
    }
}
