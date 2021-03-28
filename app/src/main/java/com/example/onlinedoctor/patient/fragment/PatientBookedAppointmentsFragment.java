package com.example.onlinedoctor.patient.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.databinding.FragmentPatientBookedAppointmentsBinding;


public class PatientBookedAppointmentsFragment extends Fragment {
    FragmentPatientBookedAppointmentsBinding mFragmentPatientBookedAppointmentsBinding;



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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}