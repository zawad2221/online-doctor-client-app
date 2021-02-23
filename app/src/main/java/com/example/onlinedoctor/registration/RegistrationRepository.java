package com.example.onlinedoctor.registration;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Doctor;
import com.example.onlinedoctor.model.Pathology;
import com.example.onlinedoctor.model.Patient;
import com.example.onlinedoctor.model.Specialization;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationRepository {
    private int REGISTRATION_SUCCESS_CODE=201;
    private int DATA_RETRIEVE_SUCCESS_CODE =200;

    private static final String DEBUGING_TAG = "DEBUGING_TAG";

    private static RegistrationRepository instance;
    public static RegistrationRepository getInstance(){
        if(instance==null){
            instance = new RegistrationRepository();
        }
        return instance;
    }

    public  <T> MutableLiveData<Boolean> registration(T object){
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if(object instanceof Doctor){
            Log.d(DEBUGING_TAG,"DOCTOR");
            result = doctorRegistration((Doctor) object);

        }
        else if(object instanceof Patient){
            Log.d(DEBUGING_TAG,"patient");
            result = patientRegistration((Patient) object);
        }
        else if(object instanceof Pathology){
            Log.d(DEBUGING_TAG,"pathology");
            result = pathologyRegistration((Pathology) object);
        }
        else if(object instanceof Chamber){
            Log.d(DEBUGING_TAG,"chamber");
            result = chamberRegistration((Chamber) object);
        }
        return result;



    }

    private MutableLiveData<Boolean> doctorRegistration(Doctor doctor){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/doctor/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RegistrationApi registrationApi = retrofit.create(RegistrationApi.class);
        Call<Doctor> call = registrationApi.doctorRegistration(doctor);
        call.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if(response.isSuccessful() && response.code()==REGISTRATION_SUCCESS_CODE){
                    result.setValue(true);
                }
                else {
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                Log.d(DEBUGING_TAG,"failed register doctor: "+t.getMessage());
                result.setValue(false);
            }
        });
        return result;
    }
    private MutableLiveData<Boolean> patientRegistration(Patient patient){
        Log.d(DEBUGING_TAG,"reg repo patient: "+ patient);
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/patient/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RegistrationApi registrationApi = retrofit.create(RegistrationApi.class);
        Call<Patient> call = registrationApi.patientRegistration(patient);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if(response.isSuccessful() && response.code()==REGISTRATION_SUCCESS_CODE){
                    result.setValue(true);
                    Log.d(DEBUGING_TAG,"successfully register patient: ");
                }
                else {
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.d(DEBUGING_TAG,"failed register patient: "+t.getMessage());
                result.setValue(false);
            }
        });
        return result;
    }
    private MutableLiveData<Boolean> chamberRegistration(Chamber chamber){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/chamber/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RegistrationApi registrationApi = retrofit.create(RegistrationApi.class);
        Call<Chamber> call = registrationApi.chamberRegistration(chamber);
        call.enqueue(new Callback<Chamber>() {
            @Override
            public void onResponse(Call<Chamber> call, Response<Chamber> response) {
                if(response.isSuccessful() && response.code()==REGISTRATION_SUCCESS_CODE){
                    result.setValue(true);
                    Log.d(DEBUGING_TAG,"successfully register chamber: ");
                }
                else {
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Chamber> call, Throwable t) {
                Log.d(DEBUGING_TAG,"failed register chamber: "+t.getMessage());
                result.setValue(false);
            }
        });
        return result;
    }
    private MutableLiveData<Boolean> pathologyRegistration(Pathology pathology){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/pathology/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RegistrationApi registrationApi = retrofit.create(RegistrationApi.class);
        Call<Pathology> call = registrationApi.pathologyRegistration(pathology);
        call.enqueue(new Callback<Pathology>() {
            @Override
            public void onResponse(Call<Pathology> call, Response<Pathology> response) {
                if(response.isSuccessful() && response.code()==REGISTRATION_SUCCESS_CODE){
                    result.setValue(true);
                    Log.d(DEBUGING_TAG,"successfully register Pathology: ");
                }
                else {
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Pathology> call, Throwable t) {
                Log.d(DEBUGING_TAG,"failed register Pathology: "+t.getMessage());
                result.setValue(false);
            }
        });
        return result;
    }

    public MutableLiveData<List<Specialization>> getSpecialization(){
        MutableLiveData<List<Specialization>> listMutableLiveData = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/doctor/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RegistrationApi registrationApi = retrofit.create(RegistrationApi.class);
        Call<List<Specialization>> call = registrationApi.getSpecialization();
        call.enqueue(new Callback<List<Specialization>>() {
            @Override
            public void onResponse(Call<List<Specialization>> call, Response<List<Specialization>> response) {
                if(response.isSuccessful()){
                    listMutableLiveData.postValue(response.body());
                    Log.d(DEBUGING_TAG,"successfully get all place, status: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Specialization>> call, Throwable t) {
                Log.d(DEBUGING_TAG,"failed get all place: "+t.getMessage());

            }
        });
        return listMutableLiveData;
    }

}
