package com.example.onlinedoctor.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.databinding.PrescribedMedicineRecyclerViewItemBinding;
import com.example.onlinedoctor.model.PrescribedMedicine;

import java.util.List;

public class PrescribedMedicineRecyclerAdapter extends RecyclerView.Adapter<PrescribedMedicineRecyclerAdapter.ViewModel>{

    List<PrescribedMedicine> prescribedMedicineList;

    public PrescribedMedicineRecyclerAdapter(List<PrescribedMedicine> prescribedMedicineList) {
        this.prescribedMedicineList = prescribedMedicineList;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        return new ViewModel(
                PrescribedMedicineRecyclerViewItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        final PrescribedMedicine prescribedMedicine = prescribedMedicineList.get(position);
        holder.prescribedMedicineRecyclerViewItemBinding.setPrescribedMedicine(prescribedMedicine);
    }

    @Override
    public int getItemCount() {
        if(prescribedMedicineList==null) return 0;
        else return prescribedMedicineList.size();
    }

    class ViewModel extends RecyclerView.ViewHolder{
        PrescribedMedicineRecyclerViewItemBinding prescribedMedicineRecyclerViewItemBinding;

        public ViewModel(@NonNull PrescribedMedicineRecyclerViewItemBinding prescribedMedicineRecyclerViewItemBinding) {
            super(prescribedMedicineRecyclerViewItemBinding.getRoot());
            this.prescribedMedicineRecyclerViewItemBinding=prescribedMedicineRecyclerViewItemBinding;
        }
    }
}
