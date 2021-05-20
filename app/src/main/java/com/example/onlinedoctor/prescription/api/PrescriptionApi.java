package com.example.onlinedoctor.prescription.api;

import com.example.onlinedoctor.model.Prescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PrescriptionApi {

    @GET("prescription/getPrescriptionByPatientUserId/{patientUserId}/")
    Call<List<Prescription>> getPrescriptionByPatientUserId(
            @Path(value = "patientUserId", encoded = true) int patientUserId
    );
}
