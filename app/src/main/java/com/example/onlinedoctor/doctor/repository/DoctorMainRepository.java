package com.example.onlinedoctor.doctor.repository;

import android.content.Context;


import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.doctor.api.DaggerDoctorApiComponent;
import com.example.onlinedoctor.doctor.api.DoctorApi;
import com.example.onlinedoctor.doctor.api.DoctorApiComponent;
import com.example.onlinedoctor.doctor.api.DoctorApiModule;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorMainRepository {
    static DoctorMainRepository instance;
    public static DoctorMainRepository getInstance(){
        if(instance==null) instance = new DoctorMainRepository();
        return instance;
    }

    public MutableLiveData<List<Chamber>> searchChamber(Context context, String query){
        MutableLiveData<List<Chamber>> searchedChamberList = new MutableLiveData<>();
        DoctorApi doctorApi = getDoctorApi(context);
        Call<List<Chamber>> call = doctorApi.searchChamber(query);
        call.enqueue(new Callback<List<Chamber>>() {
            @Override
            public void onResponse(Call<List<Chamber>> call, Response<List<Chamber>> response) {
                if(response.isSuccessful()){
                    searchedChamberList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Chamber>> call, Throwable t) {

            }
        });
        return searchedChamberList;
    }

    public MutableLiveData<List<Appointment>> getAppointmentByDoctorUserIdScheduleIdAndDate(Context context,
                                                                                            int doctorUserId,
                                                                                            int scheduleId,
                                                                                            String date){
        MutableLiveData<List<Appointment>> appointmentList = new MutableLiveData<>();

        DoctorApi doctorApi = getDoctorApi(context);
        Call<List<Appointment>> call = doctorApi.getAppointmentByDoctorUserIdScheduleIdAndDate(doctorUserId,
                scheduleId,
                date);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.isSuccessful()){
                    appointmentList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });
        return appointmentList;

    }
    public MutableLiveData<List<VisitingSchedule>> getVisitingScheduleByDoctorUserId(Context context,
                                                                                     int doctorUserId){
        MutableLiveData<List<VisitingSchedule>> visitingScheduleList = new MutableLiveData<>();
        DoctorApi doctorApi = getDoctorApi(context);
        Call<List<VisitingSchedule>> call = doctorApi.getVisitingScheduleByDoctorUserId(
                doctorUserId
        );
        call.enqueue(new Callback<List<VisitingSchedule>>() {
            @Override
            public void onResponse(Call<List<VisitingSchedule>> call, Response<List<VisitingSchedule>> response) {
                if (response.isSuccessful()){
                    visitingScheduleList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<VisitingSchedule>> call, Throwable t) {
                List<VisitingSchedule> visitingSchedules = new ArrayList<>();
                visitingSchedules.add(new VisitingSchedule());
                visitingScheduleList.setValue(visitingSchedules);
            }
        });
        return visitingScheduleList;
    }

    private DoctorApi getDoctorApi(Context context){
        DoctorApiComponent doctorApiComponent = DaggerDoctorApiComponent.builder()
                .doctorApiModule(new DoctorApiModule(context))
                .build();
        return doctorApiComponent.getDoctorApi();
    }
}
