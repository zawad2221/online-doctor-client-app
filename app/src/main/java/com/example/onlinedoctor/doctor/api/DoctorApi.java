package com.example.onlinedoctor.doctor.api;

import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DoctorApi {
    @GET("doctor/getVisitingScheduleByDoctorUserId/{doctorUserId}/")
        Call<List<VisitingSchedule>> getVisitingScheduleByDoctorUserId(
                @Path(value = "doctorUserId", encoded = true) int doctorUserId
    );
    @GET("appointment/getAppointmentByDoctorUserIdScheduleIdAndDate/{doctorUserId}/{visitingScheduleId}/{date}/")
    Call<List<Appointment>> getAppointmentByDoctorUserIdScheduleIdAndDate(
            @Path(value = "doctorUserId", encoded = true) int doctorUserId,
            @Path(value = "visitingScheduleId", encoded = true) int visitingScheduleId,
            @Path(value = "date", encoded = true) String date
    );
}
