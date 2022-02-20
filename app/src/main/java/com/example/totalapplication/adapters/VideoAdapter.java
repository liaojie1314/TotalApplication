package com.example.totalapplication.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.totalapplication.R;
import com.example.totalapplication.domain.VideoBean;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.JzvdStd;

public class VideoAdapter extends BaseAdapter {
    Context mContext;
    List<VideoBean.ItemListBean> mDatas;

    public VideoAdapter(Context context, List<VideoBean.ItemListBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie_lv, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //获取指定位置的数据源
        VideoBean.ItemListBean.DataBean dataBean = mDatas.get(position).getData();
        //设置发布信息
        VideoBean.ItemListBean.DataBean.AuthorBean author = dataBean.getAuthor();
        holder.nameTv.setText(author.getName());
        holder.descTv.setText(author.getDescription());
        String iconUrl = author.getIcon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Picasso.with(mContext).load(iconUrl).into(holder.iconView);
        }
        //获取点赞数和评论数
        VideoBean.ItemListBean.DataBean.ConsumptionBean consumptionBean = dataBean.getConsumption();
        holder.heartTv.setText(consumptionBean.getRealCollectionCount() + "");
        holder.commentTv.setText(consumptionBean.getReplyCount() + "");
        //设置视频播放器的信息
        holder.mJzvdStd.setUp(dataBean.getPlayUrl(), dataBean.getTitle(), JzvdStd.SCREEN_NORMAL);
        String thumbUrl = dataBean.getCover().getFeed();//缩略图的url
        Picasso.with(mContext).load(thumbUrl).into(holder.mJzvdStd.posterImageView);
        holder.mJzvdStd.positionInList = position;
        return convertView;
    }


    class ViewHolder {
        JzvdStd mJzvdStd;
        ImageView iconView;
        TextView nameTv, descTv, heartTv, commentTv;

        public ViewHolder(View view) {
            mJzvdStd = view.findViewById(R.id.item_movie_jzvd);
            iconView = view.findViewById(R.id.item_movie_iv_icon);
            nameTv = view.findViewById(R.id.item_movie_tv_name);
            descTv = view.findViewById(R.id.item_movie_tv_desc);
            heartTv = view.findViewById(R.id.item_movie_tv_heart);
            commentTv = view.findViewById(R.id.item_movie_tv_comment);
        }
    }
}
