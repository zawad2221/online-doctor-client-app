package com.example.onlinedoctor.patient.view_model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.model.VisitingSchedule;
import com.example.onlinedoctor.patient.PatientRepository;
import com.example.onlinedoctor.registration.RegistrationRepository;
import com.example.onlinedoctor.visiting_schedule.VisitingScheduleRepository;

import java.util.List;

public class PatientHomeViewModel extends ViewModel {
    private PatientRepository mPatientRepository;
    private RegistrationRepository mRegistrationRepository;
    private VisitingScheduleRepository mVisitingScheduleRepository;

    private MutableLiveData<List<Specialization>> specializationList;
    private MutableLiveData<List<Chamber>> chamberList;
    private MutableLiveData<List<VisitingSchedule>> visitingScheduleList;

    public MutableLiveData<Integer> selectedChamberId = new MutableLiveData<>();
    public int selectedVisitingSchedule;

    public int visitingScheduleRecyclerViewSelectedItem = -2;




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

    public void initVisitingSchedule(Context context,
                                     int chamberId){
        initVisitingScheduleRepository();
        if(visitingScheduleList==null) visitingScheduleList = new MutableLiveData<>();
        visitingScheduleList = mVisitingScheduleRepository.getVisitingScheduleOnChamber(
                context,
                chamberId
        );
    }
    public void initVisitingSchedule(Context context,
                                     int chamberId,
                                     int specializationId){
        initPatientRepository();
        if(visitingScheduleList==null) visitingScheduleList = new MutableLiveData<>();
        visitingScheduleList = mVisitingScheduleRepository.getVisitingScheduleOnChamberAndSpecialization(
                context,
                chamberId,
                specializationId
        );
    }

    private void initPatientRepository(){
        if(mPatientRepository==null){
            mPatientRepository = PatientRepository.getInstance();
        }
    }
    private void initVisitingScheduleRepository(){
        if(mVisitingScheduleRepository==null){
            mVisitingScheduleRepository = VisitingScheduleRepository.getInstance();
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

    public MutableLiveData<List<VisitingSchedule>> getVisitingScheduleList() {
        return visitingScheduleList;
    }
}
