package com.example.onlinedoctor.patient;

import com.example.onlinedoctor.model.Chamber;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PatientApi {
    @GET("getChamberOnSpecializationAndLocation/{specializationId}/{min_latitude}/{max_latitude}/{min_longitude}/{max_longitude}/")
    public Call<List<Chamber>> getChamberOnSpecializationAndLocation(
            @Path(value = "specializationId", encoded=true) int specializationId,
            @Path(value = "min_latitude", encoded=true) double minLatitude,
            @Path(value = "max_latitude", encoded=true) double maxLatitude,
            @Path(value = "min_longitude", encoded=true) double minLongitude,
            @Path(value = "max_longitude", encoded=true) double maxLongitude
            );
}
