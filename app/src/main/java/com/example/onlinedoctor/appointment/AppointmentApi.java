package com.example.onlinedoctor.appointment;

import com.example.onlinedoctor.model.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AppointmentApi {
    @GET("getBookedPatientNumberOnScheduleIdAndDate/{visitingScheduleId}/{date}/")
    Call<Appointment> getBookedPatientNumberOnScheduleIdAndDate(
            @Path(value = "visitingScheduleId", encoded = true) int visitingScheduleId,
            @Path(value = "date", encoded = true) String date
    );

    @GET("getAppointmentByPatientIdVisitingScheduleIdAndDate/{patientId}/{visitingScheduleId}/{date}/")
    Call<List<Appointment>> getAppointmentByPatientIdVisitingScheduleIdAndDate(
            @Path(value = "patientId", encoded = true) int patientId,
            @Path(value = "visitingScheduleId", encoded = true) int visitingScheduleId,
            @Path(value = "date", encoded = true) String date
    );
}
