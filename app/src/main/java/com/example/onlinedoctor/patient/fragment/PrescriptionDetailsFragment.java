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
import com.example.onlinedoctor.patient.adapter.PrescribedMedicineRecyclerAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;


public class PrescriptionDetailsFragment extends Fragment {

    private PatientHomeViewModel patientHomeViewModel;
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
        fragmentPrescriptionDetailsBinding.setPrescription(
                patientHomeViewModel.getPrescriptionListLiveData().getValue().get(
                        patientHomeViewModel.selectedPrescriptionRecyclerItemPosition
                )
        );

        return fragmentPrescriptionDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        patientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PRESCRIBED_MEDICINE_FRAGMENT));
        initPrescribedAdapter();
        initRecyclerView();
        fragmentPrescriptionDetailsBinding.closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
                patientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PATIENT_PRESCRIPTION_FRAGMENT));
            }
        });
    }

    private void initViewModel(){
        patientHomeViewModel = new ViewModelProvider(getActivity()).get(PatientHomeViewModel.class);
    }
    private void initPrescribedAdapter(){
        prescribedMedicineRecyclerAdapter = new PrescribedMedicineRecyclerAdapter(
                patientHomeViewModel.getPrescriptionListLiveData().getValue().get(
                        patientHomeViewModel.selectedPrescriptionRecyclerItemPosition
                ).getPrescribedMedicine()
        );
    }
    private void initRecyclerView(){
        fragmentPrescriptionDetailsBinding.prescribedMedicineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentPrescriptionDetailsBinding.prescribedMedicineRecyclerView.setHasFixedSize(true);
        fragmentPrescriptionDetailsBinding.prescribedMedicineRecyclerView.setAdapter(prescribedMedicineRecyclerAdapter);
    }

}