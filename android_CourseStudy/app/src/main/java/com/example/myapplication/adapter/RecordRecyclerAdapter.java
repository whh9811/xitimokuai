package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Records;

import java.util.List;

public class RecordRecyclerAdapter extends RecyclerView.Adapter<RecordRecyclerAdapter.ViewHolder> {
    private List<Records> recordsList;

    private OnItemClickListener onItemClickListener;

    public RecordRecyclerAdapter(List<Records> recordsList){
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Records records = recordsList.get(position);
        holder.tvRecord.setText(records.getTitle());

        if (onItemClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.onItemClickListener = itemClickListener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvRecord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecord = itemView.findViewById(R.id.tv_record);
        }
    }

    public interface OnItemClickListener{
        void onItemLongClick(View view,int position);
    }
}
