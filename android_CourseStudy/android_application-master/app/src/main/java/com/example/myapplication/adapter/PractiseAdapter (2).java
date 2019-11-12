package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Exercise;

import java.util.List;

public class PractiseAdapter extends BaseAdapter {
    private List<Exercise> exercises;
    private Context context;

    public PractiseAdapter(Context context,List<Exercise> exercises){
        this.context = context;
        this.exercises = exercises;
    }
    //list总的记录数，若为零的话，即使有数据也不会显示
    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Exercise getItem(int i) {
        return exercises.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //根据数据的记录数，根据item_practise.xml加载每个view
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        //1加载item_practise.xml布局，只需要加载一次
        if (view == null){
            holder = new viewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.test_layout,null);
            //2 获取控件对象
            holder.tvOrder = view.findViewById(R.id.left_text);
            holder.tvTitle = view.findViewById(R.id.practise_title);
            holder.tvSubTitle = view.findViewById(R.id.practise_number);

            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        //3加载数据
        final Exercise exercise = getItem(i);
        if (exercise != null){
            holder.tvOrder.setText(String.valueOf(i+1));
            holder.tvOrder.setBackgroundResource(R.mipmap.circle);
            holder.tvTitle.setText(exercise.getTitle());
            holder.tvSubTitle.setText(exercise.getSubTitle());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return view;
    }
    //存放item_practise.xml布局中的所有控件
    static class viewHolder{
        TextView tvOrder,tvTitle,tvSubTitle;
    }
}
