package com.example.totalapplication.adapters.cimoc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.totalapplication.R;
import com.example.totalapplication.activities.cimoc.CimocDetailActivity;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.domain.entities.Chapter;
import com.example.totalapplication.domain.entities.Comic;
import java.util.List;

public class CimocIntroAdapter extends RecyclerView.Adapter<CimocIntroAdapter.ViewHolder> {

    private int itemLayout;
    private Context mContext;
    private Comic comic;
    private List<Chapter> chapterList;

    public CimocIntroAdapter(List<Chapter> chapterList, int itemLayout, Context mContext) {
        this.itemLayout = itemLayout;
        this.mContext = mContext;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Chapter chapter = chapterList.get(i);
        viewHolder.chapterTitleView.setText(chapter.getChapterName());
        viewHolder.frameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CimocDetailActivity.class);
                intent.putExtra(BasicConfig.INTENT_DATA_NAME_CHAPTER, chapter);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return chapterList == null ? 0 : chapterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView chapterTitleView;
        public FrameLayout frameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterTitleView = itemView.findViewById(R.id.item_cl_chapter_title);
            frameView = itemView.findViewById(R.id.item_cl_frameLayout);
        }
    }
}
