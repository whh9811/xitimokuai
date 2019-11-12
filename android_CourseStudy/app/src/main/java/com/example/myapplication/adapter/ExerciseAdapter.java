package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<Exercise> mexerciseList;
    private Context context;

    public ExerciseAdapter(Context context,List<Exercise> exercises){
        this.context = context;
        this.mexerciseList = exercises;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvOrder,tvTitle,tvSubTitle;
        public ViewHolder(View view){
            super(view);
            tvOrder = view.findViewById(R.id.left_text);
            tvTitle = view.findViewById(R.id.practise_title);
            tvSubTitle = view.findViewById(R.id.practise_number);
        }
    }

    public ExerciseAdapter(List<Exercise> exerciseList){
        mexerciseList = exerciseList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private OnItemClickListener mOnItemClickLIstener;
    public void setmOnItemClickLIstener(OnItemClickListener listener){
        this.mOnItemClickLIstener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Exercise exercise = mexerciseList.get(position);
        if (exercise!=null) {
            holder.tvOrder.setText(String.valueOf(position + 1));
            holder.tvOrder.setBackgroundResource(R.mipmap.circle);
            holder.tvTitle.setText(exercise.getTitle());
            holder.tvSubTitle.setText(exercise.getSubTitle());

            //设置圆角背景的颜色
//            GradientDrawable drawable = (GradientDrawable) holder.tvOrder.getBackground();
//            drawable.setColor(Color.parseColor(exercise.getBgColor()));

            //设置监听器
            if (mOnItemClickLIstener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLIstener.onItemClick(holder.itemView,position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickLIstener.onItemLongClick(holder.itemView,position);
                        return true;
                    }
                });
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    @Override
    public int getItemCount() {
        return mexerciseList.size();
    }
}
