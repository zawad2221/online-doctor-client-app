package com.example.onlinedoctor.prescription;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.model.Prescription;
import com.example.onlinedoctor.patient.api.DaggerPatientApiComponent;
import com.example.onlinedoctor.patient.api.PatientApi;
import com.example.onlinedoctor.patient.api.PatientApiComponent;
import com.example.onlinedoctor.patient.api.PatientApiModule;
import com.example.onlinedoctor.prescription.api.DaggerPrescriptionApiComponent;
import com.example.onlinedoctor.prescription.api.PrescriptionApi;
import com.example.onlinedoctor.prescription.api.PrescriptionApiComponent;
import com.example.onlinedoctor.prescription.api.PrescriptionApiModule;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionRepository {

    private static PrescriptionRepository instance;

    public static PrescriptionRepository getInstance(){
        if(instance==null) instance = new PrescriptionRepository();
        return instance;
    }

    public MutableLiveData<List<Prescription>> getPrescriptionByPatientUserId(Context context, int patientUsrId){
        MutableLiveData<List<Prescription>> prescriptionList = new MutableLiveData<>();

        PrescriptionApi prescriptionApi = getPrescriptionApi(context);
        Call<List<Prescription>> call = prescriptionApi.getPrescriptionByPatientUserId(patientUsrId);
        call.enqueue(new Callback<List<Prescription>>() {
            @Override
            public void onResponse(Call<List<Prescription>> call, Response<List<Prescription>> response) {
                if(response.isSuccessful()){
                    prescriptionList.setValue(response.body());
                    Log.d(context.getString(R.string.DEBUGING_TAG), "repository got prescription: "+response.body().size());
                }
            }

            @Override
            public void onFailure(Call<List<Prescription>> call, Throwable t) {
                Log.d(context.getString(R.string.DEBUGING_TAG), "repository failed to got prescription: "+t.getMessage());
            }
        });
        return prescriptionList;
    }

    private PrescriptionApi getPrescriptionApi(Context context){
        PrescriptionApiComponent prescriptionApiComponent = DaggerPrescriptionApiComponent.builder()
                .prescriptionApiModule(new PrescriptionApiModule(context))
                .build();

        return prescriptionApiComponent.getPrescriptionApi();
    }
}
