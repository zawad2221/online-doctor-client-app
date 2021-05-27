package com.example.onlinedoctor.doctor.view_model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.doctor.repository.DoctorMainRepository;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.List;

public class DoctorMainViewModel extends ViewModel {
    private DoctorMainRepository doctorMainRepository;
    private void initDoctorRepository(){
        if(doctorMainRepository==null){
            doctorMainRepository=DoctorMainRepository.getInstance();
        }
    }

    //visiting schedule list
    private MutableLiveData<List<VisitingSchedule>> visitingScheduleList = new MutableLiveData<>();
    public void initVisitingSchedule(Context context, int doctorUserId){
        initDoctorRepository();
        visitingScheduleList = doctorMainRepository.getVisitingScheduleByDoctorUserId(context, doctorUserId);
    }

    public MutableLiveData<List<VisitingSchedule>> getVisitingScheduleList() {
        return visitingScheduleList;
    }
}
