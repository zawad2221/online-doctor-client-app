package com.example.onlinedoctor.doctor.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentDoctorsScheduleAppointmentsDetailsBinding;
import com.example.onlinedoctor.doctor.view_model.DoctorMainViewModel;
import com.example.onlinedoctor.patient.adapter.PatientMedicalHistoryViewPagerAdapter;
import com.example.onlinedoctor.patient.fragment.PatientMedicalHistoryFragment;
import com.google.android.material.tabs.TabLayoutMediator;


public class DoctorsScheduleAppointmentsDetailsFragment extends Fragment {
    FragmentDoctorsScheduleAppointmentsDetailsBinding mFragmentDoctorsScheduleAppointmentsDetailsBinding;
    DoctorMainViewModel mDoctorMainViewModel;
    private PatientMedicalHistoryViewPagerAdapter mPatientMedicalHistoryViewPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentDoctorsScheduleAppointmentsDetailsBinding = FragmentDoctorsScheduleAppointmentsDetailsBinding
                .inflate(inflater, container, false);

        return mFragmentDoctorsScheduleAppointmentsDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();

        initPagerAdapter();
        initTabLayout();

        mFragmentDoctorsScheduleAppointmentsDetailsBinding.setAppointment(
                mDoctorMainViewModel.getAppointmentList().getValue().get(
                        mDoctorMainViewModel.selectedVisitingScheduleAppointmentItem
                )
        );
        mFragmentDoctorsScheduleAppointmentsDetailsBinding.closeFragment.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }

    private void initViewModel(){
        mDoctorMainViewModel = new ViewModelProvider(getActivity())
                .get(DoctorMainViewModel.class);
    }

    private void initPagerAdapter(){
        mPatientMedicalHistoryViewPagerAdapter = new PatientMedicalHistoryViewPagerAdapter(getActivity());
        mFragmentDoctorsScheduleAppointmentsDetailsBinding.medicalHistoryViewPager.setAdapter(mPatientMedicalHistoryViewPagerAdapter);
        mFragmentDoctorsScheduleAppointmentsDetailsBinding.medicalHistoryViewPager.setPageTransformer(new PatientMedicalHistoryFragment.ZoomOutPageTransformer());
    }

    private void initTabLayout(){
        new TabLayoutMediator(
                mFragmentDoctorsScheduleAppointmentsDetailsBinding.tabLayout,
                mFragmentDoctorsScheduleAppointmentsDetailsBinding.medicalHistoryViewPager,
                ((tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setText("Prescriptions");
                            break;
                        case 1:
                            tab.setText("Reports");
                            break;
                    }
                })
        ).attach();
    }
}