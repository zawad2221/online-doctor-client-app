package com.example.onlinedoctor.patient;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.patient.api.DaggerPatientApiComponent;
import com.example.onlinedoctor.patient.api.PatientApi;
import com.example.onlinedoctor.patient.api.PatientApiComponent;
import com.example.onlinedoctor.patient.api.PatientApiModule;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientRepository {


    private int SUCCESS_CODE =200;
    private static final String DEBUGING_TAG = "DEBUGING_TAG";
    public static PatientRepository instance;
    public static PatientRepository getInstance(){
        if(instance==null){
            instance = new PatientRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Chamber>> getChamberOnSpecializationAndLocation(
            int specializationId,
            Double[][] locations
    ){
        MutableLiveData<List<Chamber>> chamberListLiveData = new MutableLiveData<>();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/chamber/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        PatientApi patientApi = retrofit.create(PatientApi.class);
        Call<List<Chamber>> call = patientApi.getChamberOnSpecializationAndLocation(
                specializationId,
                locations[0][0],
                locations[0][1],
                locations[1][0],
                locations[1][1]);
        call.enqueue(new Callback<List<Chamber>>() {
            @Override
            public void onResponse(Call<List<Chamber>> call, Response<List<Chamber>> response) {
                if(response.isSuccessful() && response.code()==SUCCESS_CODE){

                    if(response.body().size()>0)chamberListLiveData.setValue(response.body());
                }
                else {

                    Log.d(DEBUGING_TAG,"chamber couldn't load data");
                }
            }

            @Override
            public void onFailure(Call<List<Chamber>> call, Throwable t) {
                Log.d(DEBUGING_TAG,"chamber data load failed"+t.getMessage());
            }
        });
        return chamberListLiveData;

    }

    public MutableLiveData<Appointment> makeAppointment(Context context, Appointment appointment){
        MutableLiveData<Appointment> appointmentMutableLiveData = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url)+"appointment/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        PatientApi patientApi = retrofit.create(PatientApi.class);
        Call<Appointment> call = patientApi.makeAppointment(appointment);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    if(response.code()==201){
                        Log.d(context.getString(R.string.DEBUGING_TAG),"make appoint success repo");
                        appointmentMutableLiveData.setValue(response.body());
                    }
                    else if(response.code()==200){
                        appointmentMutableLiveData.setValue(response.body());
                    }

                }

                else {
                    Log.d(context.getString(R.string.DEBUGING_TAG),"make appoint unsuccessful repo");
                    Appointment appointmentFailed = getFiledAppointment();
                    appointmentMutableLiveData.setValue(appointmentFailed);
                }

            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Log.d(context.getString(R.string.DEBUGING_TAG),"make appoint failed repo");
                Appointment appointmentFailed = getFiledAppointment();
                appointmentMutableLiveData.setValue(appointmentFailed);
            }
            private Appointment getFiledAppointment(){
                Appointment appointmentFailed = new Appointment();
                appointmentFailed.setAdditionalProperty(
                        context.getString(R.string.DATA_FETCH_FAILDED_STATUS_KEY),
                        context.getString(R.string.DATA_FETCH_FAILED_STATUS_VALUE));
                return appointmentFailed;
            }
        });
        return appointmentMutableLiveData;
    }

    public MutableLiveData<List<Appointment>> getAppointmentByPatientId(Activity activity, Context context, int patientId){
        MutableLiveData<List<Appointment>> appointmentList = new MutableLiveData<>();

        PatientApi patientApi;
        patientApi = getPatientApi(context);


        Call<List<Appointment>> call=patientApi.getAppointmentByPatientId(patientId);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if (response.isSuccessful()){
                    Log.d(context.getString(R.string.DEBUGING_TAG),"got patient appointment");
                    appointmentList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });




        return appointmentList;
    }

    public MutableLiveData<List<Appointment>> getOldAppointmentOfPatient(Context context, int patientId, String dateOfToday) {
        MutableLiveData<List<Appointment>> appointmentList = new MutableLiveData<>();


        PatientApi patientApi;
        patientApi = getPatientApi(context);


        Call<List<Appointment>> call = patientApi.getOldAppointmentOfPatient(patientId, dateOfToday);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if (response.isSuccessful()) {
                    Log.d(context.getString(R.string.DEBUGING_TAG), "got patient appointment");
                    appointmentList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });
        return appointmentList;

    }

    private PatientApi getPatientApi(Context context){
        PatientApiComponent patientApiComponent = DaggerPatientApiComponent.builder()
                .patientApiModule(new PatientApiModule(context))
                .build();

        return patientApiComponent.getPatientApi();
    }

    public MutableLiveData<List<Appointment>> getNewAppointmentOfPatient(Context context, int patientId, String dateOfToday) {
        MutableLiveData<List<Appointment>> appointmentList = new MutableLiveData<>();


        PatientApi patientApi;
        patientApi = getPatientApi(context);


        Call<List<Appointment>> call = patientApi.getNewAppointmentOfPatient(patientId, dateOfToday);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if (response.isSuccessful()) {
                    Log.d(context.getString(R.string.DEBUGING_TAG), "got patient appointment");
                    appointmentList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });
        return appointmentList;

    }








}
