package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Courses;

import java.util.ArrayList;
import java.util.List;

public class CoursesRecylerAdapter extends RecyclerView.Adapter<CoursesRecylerAdapter.ViewHolder> {
    private List<Courses> courses;
    private List<Integer> imgIds;
    
    public CoursesRecylerAdapter(List<Courses> courses){
        this.courses = courses;
        setImgId();
    }

    private void setImgId() {
        imgIds = new ArrayList<>();
        imgIds.add(R.drawable.bilibili);
        imgIds.add(R.drawable.bilibili1);
        imgIds.add(R.drawable.bilibili2);
        imgIds.add(R.drawable.bilibili4);
        imgIds.add(R.drawable.bilibili);
        imgIds.add(R.drawable.bilibili1);
        imgIds.add(R.drawable.bilibili2);
        imgIds.add(R.drawable.bilibili4);
        imgIds.add(R.drawable.bilibili);
        imgIds.add(R.drawable.bilibili1);
        imgIds.add(R.drawable.bilibili2);
        imgIds.add(R.drawable.bilibili4);
        imgIds.add(R.drawable.bilibili);
        imgIds.add(R.drawable.bilibili1);
        imgIds.add(R.drawable.bilibili2);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Courses course = courses.get(position);
        holder.ivImg.setImageResource(imgIds.get(position));
        holder.tvImgTitle.setText(course.getImgTitle());
        holder.tvTitle.setText(course.getTitle());

        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvTitle,tvImgTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvImgTitle = itemView.findViewById(R.id.tv_img_title);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
