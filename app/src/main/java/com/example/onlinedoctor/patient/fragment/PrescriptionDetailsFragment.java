package com.example.onlinedoctor.patient.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPrescriptionDetailsBinding;
import com.example.onlinedoctor.doctor.view_model.DoctorMainViewModel;
import com.example.onlinedoctor.model.Prescription;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.adapter.PrescribedMedicineRecyclerAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;

import java.util.List;


public class PrescriptionDetailsFragment extends Fragment {

    private PatientHomeViewModel patientHomeViewModel;
    private DoctorMainViewModel mDoctorMainViewModel;
    private FragmentPrescriptionDetailsBinding fragmentPrescriptionDetailsBinding;
    private PrescribedMedicineRecyclerAdapter prescribedMedicineRecyclerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPrescriptionDetailsBinding = FragmentPrescriptionDetailsBinding
                .inflate(inflater, container, false);
        initViewModel();
        initDocViewModel();

        fragmentPrescriptionDetailsBinding.setPrescription(
                getSelectedPrescription()
        );

        return fragmentPrescriptionDetailsBinding.getRoot();
    }

    private Prescription getSelectedPrescription(){
        if(isPatientLogin()) return patientHomeViewModel.getPrescriptionListLiveData().getValue().get(
                patientHomeViewModel.selectedPrescriptionRecyclerItemPosition
        );
        else return mDoctorMainViewModel.getPrescriptionListLiveData().getValue().get(
                mDoctorMainViewModel.selectedPrescriptionItem
        );
    }
    private boolean isPatientLogin(){
        return User.loginUser.getUserRole().equals("patient")? true:false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isPatientLogin())patientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PRESCRIBED_MEDICINE_FRAGMENT));
        initPrescribedAdapter();
        initRecyclerView();
        fragmentPrescriptionDetailsBinding.closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
                if(isPatientLogin())patientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PATIENT_PRESCRIPTION_FRAGMENT));
            }
        });
    }

    private void initViewModel(){
        patientHomeViewModel = new ViewModelProvider(getActivity()).get(PatientHomeViewModel.class);
    }
    private void initPrescribedAdapter(){
        prescribedMedicineRecyclerAdapter = new PrescribedMedicineRecyclerAdapter(
                getSelectedPrescription().getPrescribedMedicine()
        );
    }
    private void initRecyclerView(){
        fragmentPrescriptionDetailsBinding.prescribedMedicineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentPrescriptionDetailsBinding.prescribedMedicineRecyclerView.setHasFixedSize(true);
        fragmentPrescriptionDetailsBinding.prescribedMedicineRecyclerView.setAdapter(prescribedMedicineRecyclerAdapter);
    }

    private void initDocViewModel(){
        mDoctorMainViewModel = new ViewModelProvider(getActivity())
                .get(DoctorMainViewModel.class);
    }

}