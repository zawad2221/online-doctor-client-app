package com.example.onlinedoctor.patient_report.api;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.example.onlinedoctor.model.TestReport;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface ReportApi {
    @GET("patient_report/getReportByPatientUserId/{patientUserId}/")
    public Call<List<TestReport>> getReportByPatientUserId(
            @Path(value = "patientUserId", encoded = true) int patientUserId
    );
    @GET("patient_report/getDoneReportByPatientUserId/{patientUserId}/")
    public Call<List<TestReport>> getDoneReportByPatientUserId(
            @Path(value = "patientUserId", encoded = true) int patientUserId
    );
    @GET("patient_report/getNotReportByPatientUserId/{patientUserId}/")
    public Call<List<TestReport>> getNotReportByPatientUserId(
            @Path(value = "patientUserId", encoded = true) int patientUserId
    );

    @Streaming
    @GET("patient_report/downloadReportFile/{reportId}/")
    Call<ResponseBody> downloadReportFile(
            @Path(value = "reportId", encoded = true) int reportId
    );
}
