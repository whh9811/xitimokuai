package com.example.myapplication.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.CourseDetail;

import java.util.List;


public class CourseDetailAdapter extends RecyclerView.Adapter<CourseDetailAdapter.ViewHolder> {
    private List<CourseDetail> videos;

    private int selected;
    private OnItemClickListener itemClickListener;

    public CourseDetailAdapter(List<CourseDetail> videos) {
        this.videos = videos;
        this.selected = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CourseDetail video = videos.get(position);
        holder.tvTitle.setText(video.getVideoTitle());

        // 改变选中项的图标和文本颜色
        if(selected == position) {
            holder.tvTitle.setTextColor(Color.parseColor("#009958"));
        } else {
            holder.tvTitle.setTextColor(Color.parseColor("#333333"));
        }

        // 设置选项监听
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_videoname);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
