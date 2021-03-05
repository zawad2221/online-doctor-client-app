package com.example.onlinedoctor.patient.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.patient.PatientRepository;
import com.example.onlinedoctor.registration.RegistrationRepository;

import java.util.List;

public class PatientHomeViewModel extends ViewModel {
    private PatientRepository mPatientRepository;
    private RegistrationRepository mRegistrationRepository;

    private MutableLiveData<List<Specialization>> specializationList;
    private MutableLiveData<List<Chamber>> chamberList;

    public int selectedChamberId;




    public void initSpecialization(){
        initRegistrationRepository();
        specializationList = mRegistrationRepository.getSpecialization();
    }

    //get chamber list on specialization and location

    public void initChamber(int specializationId, Double[][] location){
        initPatientRepository();
        if(chamberList==null) chamberList = new MutableLiveData<>();
        chamberList = mPatientRepository.getChamberOnSpecializationAndLocation(
                specializationId,
                location
        );
    }

    private void initPatientRepository(){
        if(mPatientRepository==null){
            mPatientRepository = PatientRepository.getInstance();
        }
    }
    private void initRegistrationRepository(){
        if(mRegistrationRepository==null){
            mRegistrationRepository = RegistrationRepository.getInstance();
        }
    }

    public MutableLiveData<List<Specialization>> getSpecializationList() {
        return specializationList;
    }

    public MutableLiveData<List<Chamber>> getChamberList() {
        return chamberList;
    }
}
