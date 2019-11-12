package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Practise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailAdapter extends RecyclerView.Adapter<ExerciseDetailAdapter.ViewHolder> {
    private List<Practise> details;
    private List<String> selectedPos;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subject,ivA,ivB,ivC,ivD,contentA,contentB,contentC,contentD;
        LinearLayout tvA,tvB,tvC,tvD;

        public ViewHolder(View itemView){
            super(itemView);
            subject = itemView.findViewById(R.id.tv_subject);
            tvA = itemView.findViewById(R.id.ll_selectA);
            tvB = itemView.findViewById(R.id.ll_selectB);
            tvC = itemView.findViewById(R.id.ll_selectC);
            tvD = itemView.findViewById(R.id.ll_selectD);
            ivA = itemView.findViewById(R.id.iv_A);
            ivB = itemView.findViewById(R.id.iv_B);
            ivC = itemView.findViewById(R.id.iv_C);
            ivD = itemView.findViewById(R.id.iv_D);
            contentA = itemView.findViewById(R.id.tv_contentA);
            contentB = itemView.findViewById(R.id.tv_contentB);
            contentC = itemView.findViewById(R.id.tv_contentC);
            contentD = itemView.findViewById(R.id.tv_contentD);
        }
    }

    private OnSelectListener onSelectListenter;
    public interface OnSelectListener{
        void OnselectA(int position,TextView ivA,TextView ivB,TextView ivC,TextView ivD);
        void OnselectB(int position,TextView ivA,TextView ivB,TextView ivC,TextView ivD);
        void OnselectC(int position,TextView ivA,TextView ivB,TextView ivC,TextView ivD);
        void OnselectD(int position,TextView ivA,TextView ivB,TextView ivC,TextView ivD);
    }
    //参数：习题的集合，监听器（由Activity实现）
    public ExerciseDetailAdapter(List<Practise> details,OnSelectListener onSelectListenter){
        this.details = details;
        selectedPos = new ArrayList<>();
        this.onSelectListenter = onSelectListenter;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_exercise_detail,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseDetailAdapter.ViewHolder holder, final int position) {
        Practise practise = details.get(position);
        holder.subject.setText(practise.getSubject());
        holder.ivA.setText("A");
        holder.ivB.setText("B");
        holder.ivC.setText("C");
        holder.ivD.setText("D");

        holder.ivA.setBackgroundResource(R.mipmap.circle_solid);
        holder.contentA.setText(practise.getSelectA());
        holder.ivB.setBackgroundResource(R.mipmap.circle_solid);
        holder.contentB.setText(practise.getSelectB());
        holder.ivC.setBackgroundResource(R.mipmap.circle_solid);
        holder.contentC.setText(practise.getSelectC());
        holder.ivD.setBackgroundResource(R.mipmap.circle_solid);
        holder.contentD.setText(practise.getSelectD());

        //设置每个图标的监听，并处理事件（根据选项判断答案是否正确，显示相应的图标）
        holder.ivA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListenter.OnselectA(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
        holder.ivB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListenter.OnselectB(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
        holder.ivC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListenter.OnselectC(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
        holder.ivD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListenter.OnselectD(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }


}
