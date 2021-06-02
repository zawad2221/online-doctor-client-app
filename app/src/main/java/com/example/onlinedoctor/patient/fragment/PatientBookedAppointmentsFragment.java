package com.example.onlinedoctor.patient.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.onlinedoctor.databinding.FragmentPatientBookedAppointmentsBinding;
import com.example.onlinedoctor.login.LoginActivity;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.Patient;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.adapter.PatientBookedAppointmentRecyclerViewAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class PatientBookedAppointmentsFragment extends Fragment {
    FragmentPatientBookedAppointmentsBinding mFragmentPatientBookedAppointmentsBinding;
    private PatientHomeViewModel mPatientHomeViewModel;
    private PatientBookedAppointmentRecyclerViewAdapter mPatientBookedAppointmentRecyclerViewAdapter;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentPatientBookedAppointmentsBinding
                = FragmentPatientBookedAppointmentsBinding.inflate(inflater,container, false);
        return mFragmentPatientBookedAppointmentsBinding.getRoot();
    }

    public boolean isLogin(){
        Log.d(getString(R.string.DEBUGING_TAG),"is login: "+ (User.loginUser==null? "false":"true"));
        return (User.loginUser==null)? false:true;
    }
    public void redirectToLoginPage(){
        Log.d(getString(R.string.DEBUGING_TAG),"login page redirect");
        getActivity().finish();
        startActivity(new Intent(this.getActivity(), LoginActivity.class));

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isLogin()) {
            redirectToLoginPage();
            return;
        }
        initViewModel();
        initBookedAppointmentList();
        observeAppointmentList();
        setSelectedFragmentInViewModel();
        initNavHost();
        appointmentFilterListener();

    }
    private void appointmentFilterListener(){
        mFragmentPatientBookedAppointmentsBinding.appointmentFilterChipGroup.setOnCheckedChangeListener((ChipGroup chipGroup, int checkedId) -> {
            switch (checkedId){
                case R.id.appointmentFilterNew:
                    getNewAppointment();
                    observeAppointmentList();
                    break;
                case R.id.appointmentFilterOld:
                    getOldAppointment();
                    observeAppointmentList();
                    break;
                case R.id.appointmentFilterAll:
                    initBookedAppointmentList();
                    observeAppointmentList();
                    break;
                default:
                    initBookedAppointmentList();
                    observeAppointmentList();
                    mFragmentPatientBookedAppointmentsBinding.appointmentFilterAll.setChecked(true);
                    break;
            }

        });
    }



    private void getNewAppointment(){
        mPatientHomeViewModel.getNewAppointmentOfPatient(getContext(), User.loginUser.getUserId(),getLocalDate());

    }
    private void getOldAppointment(){
        mPatientHomeViewModel.getOldAppointmentOfPatient(getContext(), User.loginUser.getUserId(),getLocalDate());

    }
    private String getLocalDate(){
        return DateAndTime.getLocalDate();
    }


    private void setSelectedFragmentInViewModel() {
        mPatientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PATIENT_FRAGMENT_BOOKED_APPOINTMENT));
    }

    private void initNavHost(){
        navController = ((NavHostFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.patientHomeFragmentHolder)).getNavController();
    }

    private void initViewModel(){
        if(mPatientHomeViewModel==null)
            mPatientHomeViewModel = new ViewModelProvider(getActivity())
                    .get(PatientHomeViewModel.class);
    }
    private void initBookedAppointmentList(){
        mPatientHomeViewModel.getBookedAppointmentByPatientId(getActivity(), getContext(), User.loginUser.getUserId());
    }
    private void observeAppointmentList(){
        mPatientHomeViewModel.getPatientBookedAppointmentList().observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                Log.d(getString(R.string.DEBUGING_TAG),"got appointment: "+appointments.size());
                initAppointmentsRecyclerView();
            }
        });
    }

    private void initAppointmentsRecyclerView(){
        mPatientBookedAppointmentRecyclerViewAdapter = new PatientBookedAppointmentRecyclerViewAdapter(mPatientHomeViewModel.getPatientBookedAppointmentList().getValue(), new PatientBookedAppointmentRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                mPatientHomeViewModel.selectedAppointmentFromBookedAppointmentPage = position;
                navController.navigate(R.id.action_patientBookedAppointment_to_patientBookedAppointmentDetails);
            }
        });
        mFragmentPatientBookedAppointmentsBinding.patientBookedAppointmentRecyclerView.setHasFixedSize(true);
        mFragmentPatientBookedAppointmentsBinding.patientBookedAppointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mFragmentPatientBookedAppointmentsBinding.patientBookedAppointmentRecyclerView.setAdapter(mPatientBookedAppointmentRecyclerViewAdapter);

    }

}