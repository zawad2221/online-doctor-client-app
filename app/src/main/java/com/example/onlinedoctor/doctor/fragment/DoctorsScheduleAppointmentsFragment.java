package com.example.onlinedoctor.doctor.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.onlinedoctor.DateAndTime;
import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentDoctorsScheduleAppointmentsBinding;
import com.example.onlinedoctor.doctor.adapter.DoctorScheduleAppointmentRecyclerAdapter;
import com.example.onlinedoctor.doctor.view_model.DoctorMainViewModel;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.Date;
import java.util.List;


public class DoctorsScheduleAppointmentsFragment extends Fragment {
    FragmentDoctorsScheduleAppointmentsBinding fragmentDoctorsScheduleAppointmentsBinding;
    DoctorMainViewModel doctorMainViewModel;
    DoctorScheduleAppointmentRecyclerAdapter doctorScheduleAppointmentRecyclerAdapter;
    private NavController navController;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDoctorsScheduleAppointmentsBinding = FragmentDoctorsScheduleAppointmentsBinding
                .inflate(inflater, container, false);
        initNavController();
        initViewModel();
        fragmentDoctorsScheduleAppointmentsBinding.setVisitingSchedule(
                getSelectedVisitingSchedule()
        );
        return fragmentDoctorsScheduleAppointmentsBinding.getRoot();
    }
    private VisitingSchedule getSelectedVisitingSchedule(){
        return doctorMainViewModel.getVisitingScheduleList().getValue().get(
                doctorMainViewModel.selectedVisitingScheduleItem
        );
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDoctorsScheduleAppointmentsBinding.scheduleStatusLayout.setOnClickListener(v -> {
            showStatusMenu(v,R.menu.schedule_status_menu);
        });
        initAppointmentList();
        appointmentListObserver();
        showLoadingProgressDialog();
        fragmentDoctorsScheduleAppointmentsBinding.closeButton.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }

    private void initViewModel(){
        doctorMainViewModel = new ViewModelProvider(getActivity())
                .get(DoctorMainViewModel.class);
    }

    private void showStatusMenu(View view, int menuRes){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.getMenuInflater().inflate(menuRes,popupMenu.getMenu());


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q)
            popupMenu.setForceShowIcon(true);
        popupMenu.setOnMenuItemClickListener((MenuItem item) -> {

            return false;
        });
        popupMenu.show();
    }
    private void initAppointmentList(){
        String date=null;
        if(DateAndTime.isDaysOfWeekIsToday(
            DateAndTime.getDayOfWeekOnString(getSelectedVisitingSchedule()
                    .getVisitingScheduleDaysOfWeek()
                    .getDay())
        )){
            date = DateAndTime.getLocalDate();
        }
        else {
            date = DateAndTime.getDateOfNextDayOfWeek(
                    DateAndTime.getDayOfWeekOnString(
                            getSelectedVisitingSchedule()
                                    .getVisitingScheduleDaysOfWeek()
                                    .getDay()
                    )
            );
        }
        doctorMainViewModel.initScheduleAppointment(getContext(),
                User.loginUser.getUserId(),
                getSelectedVisitingSchedule().getVisitingScheduleId(),
                date);
    }
    private void appointmentListObserver(){
        doctorMainViewModel.getAppointmentList().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                initAppointmentRecyclerView();
                dismissProgressDialog();
            }
        });
    }
    private void initAppointmentRecyclerView(){
        initAppointmentAdapter();
        fragmentDoctorsScheduleAppointmentsBinding.appointmentListRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        fragmentDoctorsScheduleAppointmentsBinding.appointmentListRecyclerView.setHasFixedSize(true);
        fragmentDoctorsScheduleAppointmentsBinding.appointmentListRecyclerView.setAdapter(
                doctorScheduleAppointmentRecyclerAdapter
        );
    }
    private void initAppointmentAdapter(){
        doctorScheduleAppointmentRecyclerAdapter = new DoctorScheduleAppointmentRecyclerAdapter(
                getContext(),
                doctorMainViewModel.getAppointmentList().getValue(),
                new DoctorScheduleAppointmentRecyclerAdapter.DoctorScheduleAppointmentOnClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        doctorMainViewModel.selectedVisitingScheduleAppointmentItem=position;
                        navController.navigate(R.id.action_doctorScheduleAppointmentFragment_to_doctorScheduleAppointmentDetailsFragment);
                        Log.d(getString(R.string.DEBUGING_TAG),"on item click: "+position);
                    }

                    @Override
                    public void onItemStatusChangeMenuClick(int position, int menuItem) {
                        Log.d(getString(R.string.DEBUGING_TAG),"on item status menu change: "+position);
                        updateAppointment(position, menuItem);
                        showLoadingProgressDialog();
                        appointmentListObserver();
                    }
                }
        );
    }
    private void updateAppointment(int position, int menuItem){
        if(menuItem==R.id.cancel_option){
            doctorMainViewModel.getAppointmentList().getValue().get(position).setAppointmentIsCanceled(true);
        }
        else if(menuItem==R.id.confirm_option){
            doctorMainViewModel.getAppointmentList().getValue().get(position).setAppointmentIsConfirmed(true);
        }
        doctorMainViewModel.updateAppointment(getContext(),doctorMainViewModel.getAppointmentList().getValue());
    }
    private void initNavController(){
        navController = ((NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.doctorHomeFragmentHolder)).getNavController();
    }
    private void showLoadingProgressDialog(){
        progressDialog = new ProgressDialog(getContext(),android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
    }
    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }


}