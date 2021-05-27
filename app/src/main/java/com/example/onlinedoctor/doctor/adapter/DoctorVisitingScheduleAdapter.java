package com.example.onlinedoctor.doctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.databinding.DoctorsVisitingScheduleRecyclerItemBinding;
import com.example.onlinedoctor.databinding.VisitingScheduleItemBinding;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.List;

public class DoctorVisitingScheduleAdapter extends RecyclerView.Adapter<DoctorVisitingScheduleAdapter.DoctorVisitingScheduleViewHolder>{
    List<VisitingSchedule> visitingScheduleList;
    OnClickListener onClickListener;

    public DoctorVisitingScheduleAdapter(List<VisitingSchedule> visitingScheduleList, OnClickListener onClickListener) {
        this.visitingScheduleList = visitingScheduleList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public DoctorVisitingScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorVisitingScheduleViewHolder(
                DoctorsVisitingScheduleRecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false)

        );
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorVisitingScheduleViewHolder holder, int position) {
        final VisitingSchedule visitingSchedule = visitingScheduleList.get(position);
        holder.doctorsVisitingScheduleRecyclerItemBinding.setVisitingSchedule(visitingSchedule);
    }

    @Override
    public int getItemCount() {
        if(visitingScheduleList==null) return 0;
        else return visitingScheduleList.size();
    }

    public interface OnClickListener{
        public void onItemClick(int position);
    }

    class DoctorVisitingScheduleViewHolder extends RecyclerView.ViewHolder {
        DoctorsVisitingScheduleRecyclerItemBinding doctorsVisitingScheduleRecyclerItemBinding;

        public DoctorVisitingScheduleViewHolder(DoctorsVisitingScheduleRecyclerItemBinding doctorsVisitingScheduleRecyclerItemBinding) {
            super(doctorsVisitingScheduleRecyclerItemBinding.getRoot());
            this.doctorsVisitingScheduleRecyclerItemBinding=doctorsVisitingScheduleRecyclerItemBinding;
            doctorsVisitingScheduleRecyclerItemBinding.visitingScheduleItem.setOnClickListener(v -> {
                onClickListener.onItemClick(getAdapterPosition());
            });
        }


    }
}
