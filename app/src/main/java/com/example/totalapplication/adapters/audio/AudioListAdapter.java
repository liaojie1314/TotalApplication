package com.example.totalapplication.adapters.audio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.totalapplication.R;
import com.example.totalapplication.databinding.ItemAudioBinding;
import com.example.totalapplication.domain.AudioBean;

import java.util.List;

public class AudioListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AudioBean>mDatas;
    //点击每一个itemView中的playIV能够回调过来
    public interface OnItemPlayClickListener{
        void OnItemPlayClick(AudioListAdapter adapter,View convertView,View playView,int position);
    }

    private OnItemPlayClickListener mOnItemPlayClickListener;

    public void setOnItemPlayClickListener(OnItemPlayClickListener onItemPlayClickListener) {
        this.mOnItemPlayClickListener = onItemPlayClickListener;
    }

    public AudioListAdapter(Context context, List<AudioBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder  =null;
        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_audio,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //获取指定位置的数据对控件进行设置
        AudioBean audioBean = mDatas.get(position);
        viewHolder.mItemAudioBinding.timeTV.setText(audioBean.getTime());
        viewHolder.mItemAudioBinding.durationTV.setText(audioBean.getDuration());
        viewHolder.mItemAudioBinding.titleTV.setText(audioBean.getTitle());
        if (audioBean.isPlaying()) {
            viewHolder.mItemAudioBinding.controllerLL.setVisibility(View.VISIBLE);
            viewHolder.mItemAudioBinding.pb.setMax(100);
            viewHolder.mItemAudioBinding.pb.setProgress(audioBean.getCurrentProgress());
            viewHolder.mItemAudioBinding.playIV.setImageResource(R.mipmap.red_pause);
        }else {
            viewHolder.mItemAudioBinding.playIV.setImageResource(R.mipmap.red_play);
            viewHolder.mItemAudioBinding.controllerLL.setVisibility(View.GONE);
        }
        View itemView = convertView;
        //点击播放图标可以播放和暂停
        viewHolder.mItemAudioBinding.playIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemPlayClickListener!=null) {
                    mOnItemPlayClickListener.OnItemPlayClick(AudioListAdapter.this,
                            itemView,v,position);
                }
            }
        });
        return convertView;
    }

    class ViewHolder{
        ItemAudioBinding mItemAudioBinding;
        public ViewHolder(View view){
            mItemAudioBinding = ItemAudioBinding.bind(view);
        }
    }
}
