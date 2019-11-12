package com.example.myapplication.adapter;

import android.content.Context;
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = mexerciseList.get(position);
        if (exercise!=null) {
            holder.tvOrder.setText(String.valueOf(position + 1));
            holder.tvOrder.setBackgroundResource(R.mipmap.circle);
            holder.tvTitle.setText(exercise.getTitle());
            holder.tvSubTitle.setText(exercise.getSubTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mexerciseList.size();
    }
}
