package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Courses;

import java.util.ArrayList;
import java.util.List;

public class CoursesGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Courses> courses;
    private List<Integer> imgIds;

    public CoursesGridViewAdapter(Context context, List<Courses> courses){
        this.context = context;
        this.courses = courses;
        setImgIds();
    }

    private void setImgIds() {
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

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int i) {
        return courses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_course,viewGroup,false);

            holder = new ViewHolder();
            holder.ivImg = view.findViewById(R.id.iv_img);
            holder.tvImgTitle = view.findViewById(R.id.tv_img_title);
            holder.tvTitle = view.findViewById(R.id.tv_title);

            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        Courses courses = (Courses) getItem(i);
        holder.ivImg.setImageResource(imgIds.get(i));
        holder.tvImgTitle.setText(courses.getImgTitle());
        holder.tvTitle.setText(courses.getTitle());
        return view;
    }

    static class ViewHolder{
        ImageView ivImg;
        TextView tvImgTitle;
        TextView tvTitle;
    }
}
