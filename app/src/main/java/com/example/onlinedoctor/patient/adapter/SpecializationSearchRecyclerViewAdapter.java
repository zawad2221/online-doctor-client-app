package com.example.onlinedoctor.patient.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.PatientHomeSpecializaionRecyclerViewItemBinding;
import com.example.onlinedoctor.model.Specialization;

import java.util.List;

public class SpecializationSearchRecyclerViewAdapter extends RecyclerView.Adapter<SpecializationSearchRecyclerViewAdapter.ViewHolder> {
    private int selectedItem=-1;
    private int alreadySelectedItem = -2;
    public static OnItemClickListener mOnItemClickListener;
    public static interface OnItemClickListener {
        public void onItemClick(int position);
    }

    List<Specialization> specializationList;

    public SpecializationSearchRecyclerViewAdapter( List<Specialization> specializationList, OnItemClickListener mOnItemClickListener) {
        this.specializationList = specializationList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(PatientHomeSpecializaionRecyclerViewItemBinding.
                inflate(
                        LayoutInflater.from
                                (
                                parent.getContext()
                                ),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Specialization specialization = specializationList.get(position);
        holder.patientHomeSpecializaionRecyclerViewItemBinding.specializationSearchRecyclerItem.setText(specialization.getSpecializationName());
        holder.patientHomeSpecializaionRecyclerViewItemBinding.placeTypeOptionRecyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
                selectedItem = position;
                notifyDataSetChanged();
            }
        });
        if(position==selectedItem && alreadySelectedItem!=selectedItem){
            holder.patientHomeSpecializaionRecyclerViewItemBinding.specializationSearchRecyclerItem.setBackgroundResource(
                    R.color.recycler_item_selected_color
            );
            alreadySelectedItem = position;
        }
        else {
            holder.patientHomeSpecializaionRecyclerViewItemBinding.specializationSearchRecyclerItem.setBackgroundResource(
                    R.color.white
            );
            if(position==alreadySelectedItem) alreadySelectedItem=-2;

        }

    }

    @Override
    public int getItemCount() {
        if(specializationList==null)
            return 0;
        else
            return specializationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        PatientHomeSpecializaionRecyclerViewItemBinding patientHomeSpecializaionRecyclerViewItemBinding;

        public ViewHolder(@NonNull PatientHomeSpecializaionRecyclerViewItemBinding patientHomeSpecializaionRecyclerViewItemBinding) {
            super(patientHomeSpecializaionRecyclerViewItemBinding.getRoot());
            this.patientHomeSpecializaionRecyclerViewItemBinding = patientHomeSpecializaionRecyclerViewItemBinding;

        }
    }
}
