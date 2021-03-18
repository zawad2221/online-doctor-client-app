package com.example.onlinedoctor.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.DateAndTime;
import com.example.onlinedoctor.databinding.VisitingScheduleItemBinding;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.List;

public class ChamberVisitingScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ChamberVisitingScheduleRecyclerViewAdapter.ViewHolder>{
    List<VisitingSchedule> visitingScheduleList;
    public static OnItemClickListener onItemClickListener;
    int selectedItemPosition=-1;

    public ChamberVisitingScheduleRecyclerViewAdapter(List<VisitingSchedule> visitingScheduleList, OnItemClickListener onItemClickListener) {
        this.visitingScheduleList = visitingScheduleList;
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
        final VisitingSchedule visitingSchedule = visitingScheduleList.get(position);
        holder.visitingScheduleItemBinding.doctorName.setText(
                visitingSchedule
                .getVisitingScheduleDoctor()
                .getDoctorUser()
                .getUserName()
        );
        holder.visitingScheduleItemBinding.doctorSpecialization.setText(
                visitingSchedule
                .getVisitingScheduleDoctor()
                .getDoctorSpecialization()
                .getSpecializationName()
        );
        holder.visitingScheduleItemBinding.scheduleTime.setText(
                        DateAndTime.convert24to12(visitingSchedule.getStartAt())
                        +" - "
                        + DateAndTime.convert24to12(visitingSchedule.getEndAt())
        );
        holder.visitingScheduleItemBinding.visitingScheduleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);



            }
        });



    }

    @Override
    public int getItemCount() {
        if(visitingScheduleList==null) return 0;
        else return visitingScheduleList.size();
    }

    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VisitingScheduleItemBinding visitingScheduleItemBinding;
        public ViewHolder(@NonNull VisitingScheduleItemBinding visitingScheduleItemBinding) {
            super(visitingScheduleItemBinding.getRoot());
            this.visitingScheduleItemBinding=visitingScheduleItemBinding;

        }
    }
}
