package com.example.onlinedoctor.patient.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.PrescriptionRecyclerViewItemBinding;
import com.example.onlinedoctor.model.Prescription;

import java.util.List;

public class PatientPrescriptionRecyclerAdapter extends RecyclerView.Adapter<PatientPrescriptionRecyclerAdapter.ViewHolder>{
    private List<Prescription> prescriptionList;
    private OnClickListener onClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                PrescriptionRecyclerViewItemBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Prescription prescription = prescriptionList.get(position);
        Log.d("DEBUGING_TAG", "prescription adapter on bind item: "+position);
        holder.mPrescriptionRecyclerViewItemBinding.setPrescription(prescription);

    }

    @Override
    public int getItemCount() {
        if(prescriptionList==null) return 0;
        else return prescriptionList.size();
    }


    public interface OnClickListener{
        public void OnItemClickListener(int position);
    }

    public PatientPrescriptionRecyclerAdapter(List<Prescription> prescriptionList, OnClickListener onClickListener) {
        this.prescriptionList = prescriptionList;
        this.onClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        PrescriptionRecyclerViewItemBinding mPrescriptionRecyclerViewItemBinding;

        public ViewHolder(PrescriptionRecyclerViewItemBinding mPrescriptionRecyclerViewItemBinding) {
            super(mPrescriptionRecyclerViewItemBinding.getRoot());
            this.mPrescriptionRecyclerViewItemBinding = mPrescriptionRecyclerViewItemBinding;
            mPrescriptionRecyclerViewItemBinding.prescriptionItemConstraintLayout.setOnClickListener((View v) -> {
                onClickListener.OnItemClickListener(getAdapterPosition());
            });
        }
    }
}
