package com.example.onlinedoctor.patient.view_model;

import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.patient.PatientRepository;

public class PatientHomeViewModel extends ViewModel {
    private PatientRepository mPatientRepository;



    private void initPatientRepository(){
        if(mPatientRepository==null){
            mPatientRepository = PatientRepository.getInstance();
        }
    }
}
