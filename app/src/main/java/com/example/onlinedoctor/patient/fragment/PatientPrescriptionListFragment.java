package com.example.onlinedoctor.patient.fragment;

import android.os.Bundle;

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
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientPrescriptionListBinding;
import com.example.onlinedoctor.doctor.view_model.DoctorMainViewModel;
import com.example.onlinedoctor.model.Prescription;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.adapter.PatientPrescriptionRecyclerAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;

import java.util.List;


public class PatientPrescriptionListFragment extends Fragment {
    private PatientHomeViewModel mPatientHomeViewModel;
    private DoctorMainViewModel mDoctorMainViewModel;
    private FragmentPatientPrescriptionListBinding mFragmentPatientPrescriptionListBinding;
    private PatientPrescriptionRecyclerAdapter mPatientPrescriptionRecyclerAdapter;
    private NavController navController;
    private NavController navControllerDoc;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentPatientPrescriptionListBinding = FragmentPatientPrescriptionListBinding
                .inflate(inflater, container, false);
        if(isPatientLogin())initNavHost();
        else initNavHostDoc();
        initDocViewModel();
        iniViewModel();
        initPrescriptionList();
        return mFragmentPatientPrescriptionListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPatientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PATIENT_PRESCRIPTION_FRAGMENT));
    }

    private void initNavHost(){
        navController = (
                (NavHostFragment) getActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.patientHomeFragmentHolder)
        ).getNavController();
    }

    private void initNavHostDoc(){
        navControllerDoc = (
                (NavHostFragment) getActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.doctorHomeFragmentHolder)
        ).getNavController();
    }

    private List<Prescription> getPrescriptionList(){
        if(isPatientLogin()) return mPatientHomeViewModel.getPrescriptionListLiveData().getValue();
        else return mDoctorMainViewModel.getPrescriptionListLiveData().getValue();
    }

    private boolean isPatientLogin(){
        return User.loginUser.getUserRole().equals("patient")? true:false;
    }


    private void iniViewModel(){
        mPatientHomeViewModel = new ViewModelProvider(getActivity()).get(PatientHomeViewModel.class);
    }
    private void initPrescriptionList(){
        if(isPatientLogin()){
            mPatientHomeViewModel.getPrescriptionListByPatientUserId(getContext(), User.loginUser.getUserId());
            Log.d(getString(R.string.DEBUGING_TAG),"pres frag userId: "+User.loginUser.getUserId());
            patientPrescriptionListObserver();
        }
        else {
            mDoctorMainViewModel.getPrescriptionListByPatientUserId(
                    getContext(),
                    mDoctorMainViewModel
                    .getAppointmentList()
                    .getValue()
                    .get(mDoctorMainViewModel.selectedVisitingScheduleAppointmentItem)
                    .getAppointmentPatient()
                    .getPatientUser()
                    .getUserId()
            );
            patientPrescriptionListForDoctorObserver();
        }

    }

    private void patientPrescriptionListForDoctorObserver(){
        mDoctorMainViewModel.getPrescriptionListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Prescription>>() {
            @Override
            public void onChanged(List<Prescription> prescriptions) {
                initPrescriptionRecyclerView();
                Log.d(getString(R.string.DEBUGING_TAG), "patient prescription list observer");
            }
        });
    }

    private void patientPrescriptionListObserver(){
        mPatientHomeViewModel.getPrescriptionListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Prescription>>() {
            @Override
            public void onChanged(List<Prescription> prescriptions) {
                initPrescriptionRecyclerView();
                Log.d(getString(R.string.DEBUGING_TAG), "patient prescription list observer");
            }
        });
    }
    private void initPrescriptionRecyclerView(){
        initPrescriptionRecyclerAdapter();
        mFragmentPatientPrescriptionListBinding.patientPrescriptionRecyclerView.setAdapter(mPatientPrescriptionRecyclerAdapter);
        mFragmentPatientPrescriptionListBinding.patientPrescriptionRecyclerView.setHasFixedSize(true);
        mFragmentPatientPrescriptionListBinding.patientPrescriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void initPrescriptionRecyclerAdapter(){
        mPatientPrescriptionRecyclerAdapter = new PatientPrescriptionRecyclerAdapter(
                getPrescriptionList(),
                new PatientPrescriptionRecyclerAdapter.OnClickListener() {
                    @Override
                    public void OnItemClickListener(int position) {
                        prescriptionRecyclerViewItemClick(position);
                    }
                }
        );
    }
    private void prescriptionRecyclerViewItemClick(int position){
        if(isPatientLogin()) {
            mPatientHomeViewModel.selectedPrescriptionRecyclerItemPosition = position;
            navController.navigate(R.id.action_patientMedicalHistory_to_patientPrescriptionDetails);
        }
        else {
            mDoctorMainViewModel.selectedPrescriptionItem=position;
            navControllerDoc.navigate(R.id.action_doctorScheduleAppointmentDetailsFragment_to_patientPrescriptionDetails);
        }

    }

    private void initDocViewModel(){
        mDoctorMainViewModel = new ViewModelProvider(getActivity())
                .get(DoctorMainViewModel.class);
    }
}