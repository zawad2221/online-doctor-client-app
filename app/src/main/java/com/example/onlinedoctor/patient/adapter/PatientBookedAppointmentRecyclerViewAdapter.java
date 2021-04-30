package com.example.onlinedoctor.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.databinding.PatientBookedAppointmentItemBinding;
import com.example.onlinedoctor.model.Appointment;

import java.util.List;

public class PatientBookedAppointmentRecyclerViewAdapter extends
        RecyclerView.Adapter<PatientBookedAppointmentRecyclerViewAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<Appointment> appointmentList;

    public PatientBookedAppointmentRecyclerViewAdapter(List<Appointment> appointmentList,OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.appointmentList = appointmentList;
    }

    public interface OnItemClickListener{
        public void itemClick(int position);
    }



    @NonNull
    @Override
    public PatientBookedAppointmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                PatientBookedAppointmentItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PatientBookedAppointmentRecyclerViewAdapter.ViewHolder holder, int position) {
        final Appointment appointment = appointmentList.get(position);
        holder.mPatientBookedAppointmentItemBinding.setAppointment(appointment);
    }

    @Override
    public int getItemCount() {
        if(appointmentList==null)
            return 0;
        else
            return appointmentList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        PatientBookedAppointmentItemBinding mPatientBookedAppointmentItemBinding;

        public ViewHolder(@NonNull PatientBookedAppointmentItemBinding mPatientBookedAppointmentItemBinding) {
            super(mPatientBookedAppointmentItemBinding.getRoot());
            this.mPatientBookedAppointmentItemBinding = mPatientBookedAppointmentItemBinding;

            mPatientBookedAppointmentItemBinding.appointmentScheduleItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.itemClick(getAdapterPosition());
                }
            });

        }

    }
}
