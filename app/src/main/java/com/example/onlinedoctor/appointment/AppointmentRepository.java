package com.example.onlinedoctor.appointment;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.model.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentRepository {
    public static AppointmentRepository instance;
    public static AppointmentRepository getInstance(){
        if(instance==null){
            instance = new AppointmentRepository();
        }
        return instance;
    }

    public MutableLiveData<Integer> getBookedPatientNumberOnScheduleIdAndDate(Context context,
                                                                       int visitingScheduleId,
                                                                       String date){
        MutableLiveData<Integer> bookedPatientNumber = new MutableLiveData<>(-1);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url)+"appointment/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AppointmentApi appointmentApi = retrofit.create(AppointmentApi.class);
        Call<Appointment> call = appointmentApi.getBookedPatientNumberOnScheduleIdAndDate(visitingScheduleId, date);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful() && response.code()==200){
                    Appointment appointment = response.body();
                    bookedPatientNumber.setValue((int)(Float.parseFloat(
                            appointment
                                    .getAdditionalProperties()
                                    .get(
                                            context
                                                    .getString(
                                                            R
                                                                    .string
                                                                    .BOOK_PATIENT_NUMBER_ADDITIONAL_PROPERTIES_KEY
                                                    )
                                    ).toString())));
                }
                else{
                    bookedPatientNumber.setValue(-1);
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                bookedPatientNumber.setValue(-1);
            }
        });
        return bookedPatientNumber;
    }

    public MutableLiveData<List<Appointment>> getAppointmentByPatientIdVisitingScheduleIdAndDate(
            Context context,
            int patientId,
            int visitingScheduleId,
            String date
    ){
        MutableLiveData<List<Appointment>> patientAppointment = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url)+"appointment/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AppointmentApi appointmentApi = retrofit.create(AppointmentApi.class);
        Call<List<Appointment>> call = appointmentApi.getAppointmentByPatientIdVisitingScheduleIdAndDate(
                patientId,
                visitingScheduleId,
                date);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                Log.d(context.getString(R.string.DEBUGING_TAG),"pati appoint: "+response.body());
                patientAppointment.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                Log.d(context.getString(R.string.DEBUGING_TAG),"pati appoint failed ");
            }
        });
        return patientAppointment;
    }
}
