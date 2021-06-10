package com.example.onlinedoctor.visiting_schedule;

import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VisitingScheduleApi {
    @GET("getVisitingScheduleOnChamberAndSpecialization/{chamberId}/{specializationId}/")
    public Call<List<VisitingSchedule>> getVisitingScheduleOnChamberAndSpecialization(
            @Path(value = "chamberId", encoded = true) int chamberId,
            @Path(value = "specializationId", encoded = true) int specializationId
    );
    @POST("visitingSchedule/createVisitingSchedule/")
    public Call<VisitingSchedule> createVisitingSchedule(@Body VisitingSchedule visitingSchedule);
}
