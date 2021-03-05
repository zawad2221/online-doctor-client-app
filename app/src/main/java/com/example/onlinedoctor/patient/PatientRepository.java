package com.example.onlinedoctor.patient;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Location;

import java.util.List;

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
}
