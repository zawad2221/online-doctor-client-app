package com.example.onlinedoctor.registration;

import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Doctor;
import com.example.onlinedoctor.model.Pathology;
import com.example.onlinedoctor.model.Patient;
import com.example.onlinedoctor.model.Specialization;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegistrationApi {
    @POST("patientRegistration/")
    Call<Patient> patientRegistration(@Body  Patient patient);
    @POST("pathologyRegistration/")
    Call<Pathology> pathologyRegistration(@Body  Pathology pathology);
    @POST("doctorRegistration/")
    Call<Doctor> doctorRegistration(@Body  Doctor doctor);
    @POST("chamberRegistration/")
    Call<Chamber> chamberRegistration(@Body  Chamber chamber);

    @GET("getSpecialization/")
    Call<List<Specialization>> getSpecialization();
}
