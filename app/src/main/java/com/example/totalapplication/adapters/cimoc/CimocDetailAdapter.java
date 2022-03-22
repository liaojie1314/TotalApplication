package com.example.totalapplication.adapters.cimoc;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.totalapplication.R;
import com.example.totalapplication.config.PictureConfig;
import com.example.totalapplication.interfaces.DataLoadInterface;

import java.util.List;

public class CimocDetailAdapter extends RecyclerView.Adapter<CimocDetailAdapter.ViewHolder> {
    private List<String> picsList;
    private int itemLayout;
    private Context mContext;
    private DataLoadInterface loadDataListener;
    private boolean isShowHint = false;

    public CimocDetailAdapter(List<String> picsList, int itemLayout, Context mContext, DataLoadInterface loadDataListener) {
        this.itemLayout = itemLayout;
        this.mContext = mContext;
        this.loadDataListener = loadDataListener;
        this.picsList = picsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        try {
            String url = picsList.get(i);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL);
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .thumbnail(PictureConfig.PIC_MIDDLE)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            loadDataListener.onLoadFinished(e, null);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            loadDataListener.onLoadFinished(null, null);
                            return false;
                        }
                    })
                    .into(viewHolder.imageView);
            if (i == picsList.size() - 1 && isShowHint) {
                viewHolder.hintView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.hintView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.d("测试", "onBindViewHolder: " + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return picsList == null ? 0 : picsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String picUrl, int position) {
        if (position >= 0) {
            picsList.add(position, picUrl);
            notifyItemInserted(position);
            notifyItemRangeChanged(position,picsList.size() - position);
        }
    }

    public void removeItem(int position) {
        picsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,picsList.size() - position);
    }

    public void addItemBack(String picUrl) {
        picsList.add(picUrl);
        notifyItemInserted(picsList.size() - 1);
        notifyItemRangeChanged(picsList.size() - 1,1);
    }

    public void setShowHint(boolean isShowHint) {
        this.isShowHint = isShowHint;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;
        public TextView hintView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_comic_detail_img);
            titleView = itemView.findViewById(R.id.item_comic_detail_title);
            hintView = itemView.findViewById(R.id.item_comic_detail_hint);
        }
    }
}
