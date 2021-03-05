package com.example.onlinedoctor.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.databinding.VisitingScheduleItemBinding;
import com.example.onlinedoctor.model.Chamber;

import java.util.List;

public class ChamberRecyclerViewAdapter extends RecyclerView.Adapter<ChamberRecyclerViewAdapter.ViewHolder>{
    List<Chamber> chamberList;
    public static OnItemClickListener onItemClickListener;

    public ChamberRecyclerViewAdapter(List<Chamber> chamberList, OnItemClickListener onItemClickListener) {
        this.chamberList = chamberList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                VisitingScheduleItemBinding
                        .inflate(LayoutInflater.from(parent.getContext()),
                                parent,
                                false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Chamber chamber = chamberList.get(position);
        holder.visitingScheduleItemBinding.doctorName.setText(chamber.)

    }

    @Override
    public int getItemCount() {
        if(chamberList==null) return 0;
        else return chamberList.size();
    }

    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VisitingScheduleItemBinding visitingScheduleItemBinding;
        public ViewHolder(@NonNull VisitingScheduleItemBinding visitingScheduleItemBinding) {
            super(visitingScheduleItemBinding.getRoot());
            this.visitingScheduleItemBinding=visitingScheduleItemBinding;
            visitingScheduleItemBinding.visitingScheduleItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
