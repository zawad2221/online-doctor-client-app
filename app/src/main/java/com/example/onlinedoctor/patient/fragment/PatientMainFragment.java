package com.example.onlinedoctor.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientMainBinding;

public class PatientMainFragment extends Fragment {
    private FragmentPatientMainBinding mFragmentPatientMainBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentPatientMainBinding = FragmentPatientMainBinding
                .inflate(inflater, container, false);

        return mFragmentPatientMainBinding.getRoot();
    }
}