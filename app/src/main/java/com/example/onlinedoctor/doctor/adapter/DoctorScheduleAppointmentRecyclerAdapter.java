package com.example.onlinedoctor.doctor.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.DoctorsScheduleAppointmentItemBinding;
import com.example.onlinedoctor.model.Appointment;

import java.util.List;

public class DoctorScheduleAppointmentRecyclerAdapter extends RecyclerView.Adapter<DoctorScheduleAppointmentRecyclerAdapter.DoctorScheduleAppointmentViewHolder>{
    @NonNull
    @Override
    public DoctorScheduleAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorScheduleAppointmentViewHolder(
                DoctorsScheduleAppointmentItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorScheduleAppointmentViewHolder holder, int position) {
        final Appointment appointment = appointmentList.get(position);
        holder.doctorsScheduleAppointmentItemBinding.setAppointment(appointment);

    }

    @Override
    public int getItemCount() {
        if(appointmentList==null) return 0;
        else return appointmentList.size();
    }

    public interface DoctorScheduleAppointmentOnClickListener{
        public void onItemClick(int position);
        public void onItemStatusChangeMenuClick(int position, int menuItem);
    }
    private List<Appointment> appointmentList;
    private DoctorScheduleAppointmentOnClickListener doctorScheduleAppointmentOnClickListener;
    Context context;

    public DoctorScheduleAppointmentRecyclerAdapter(Context context, List<Appointment> appointmentList, DoctorScheduleAppointmentOnClickListener doctorScheduleAppointmentOnClickListener) {
        this.appointmentList = appointmentList;
        this.doctorScheduleAppointmentOnClickListener = doctorScheduleAppointmentOnClickListener;
        this.context=context;
    }


    class DoctorScheduleAppointmentViewHolder extends RecyclerView.ViewHolder{
        DoctorsScheduleAppointmentItemBinding doctorsScheduleAppointmentItemBinding;
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public DoctorScheduleAppointmentViewHolder(DoctorsScheduleAppointmentItemBinding doctorsScheduleAppointmentItemBinding) {
            super(doctorsScheduleAppointmentItemBinding.getRoot());
            this.doctorsScheduleAppointmentItemBinding=doctorsScheduleAppointmentItemBinding;
            doctorsScheduleAppointmentItemBinding.appointmentItem.setOnClickListener(v -> {
                doctorScheduleAppointmentOnClickListener.onItemClick(getAdapterPosition());
            });
            doctorsScheduleAppointmentItemBinding.appointmentStatusLayout.setOnClickListener(v -> {
                showStatusMenu(v, R.menu.appointment_status_menu);
            });

        }
        @RequiresApi(api = Build.VERSION_CODES.Q)
        private void showStatusMenu(View view, int menuRes){
            PopupMenu popupMenu = new PopupMenu(context,view);
            popupMenu.getMenuInflater().inflate(menuRes,popupMenu.getMenu());
            popupMenu.setForceShowIcon(true);
            popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
                doctorScheduleAppointmentOnClickListener.onItemStatusChangeMenuClick(getAdapterPosition(),
                        item.getItemId());
                return false;
            });
            popupMenu.show();
        }
    }
}
