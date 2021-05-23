package com.example.onlinedoctor.patient_report;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.model.TestReport;
import com.example.onlinedoctor.patient_report.api.DaggerReportApiComponent;
import com.example.onlinedoctor.patient_report.api.ReportApi;
import com.example.onlinedoctor.patient_report.api.ReportApiComponent;
import com.example.onlinedoctor.patient_report.api.ReportApiModule;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportRepository {
    private static ReportRepository instance;
    public static ReportRepository getInstance(){
        if(instance==null) instance = new ReportRepository();
        return instance;
    }

    public MutableLiveData<List<TestReport>> getReportByPatientUserId(Context context, int patientUserId){
        MutableLiveData<List<TestReport>> testReportLiveData = new MutableLiveData<>();
        ReportApi reportApi = getReportApi(context);
        Call<List<TestReport>> call = reportApi.getReportByPatientUserId(patientUserId);
        call.enqueue(new Callback<List<TestReport>>() {
            @Override
            public void onResponse(Call<List<TestReport>> call, Response<List<TestReport>> response) {
                if(response.isSuccessful()){
                    testReportLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TestReport>> call, Throwable t) {

            }
        });
        return testReportLiveData;
    }
    public MutableLiveData<List<TestReport>> getDoneReportByPatientUserId(Context context, int patientUserId){
        MutableLiveData<List<TestReport>> testReportLiveData = new MutableLiveData<>();
        ReportApi reportApi = getReportApi(context);
        Call<List<TestReport>> call = reportApi.getDoneReportByPatientUserId(patientUserId);
        call.enqueue(new Callback<List<TestReport>>() {
            @Override
            public void onResponse(Call<List<TestReport>> call, Response<List<TestReport>> response) {
                if(response.isSuccessful()){
                    testReportLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TestReport>> call, Throwable t) {

            }
        });
        return testReportLiveData;
    }
    public MutableLiveData<List<TestReport>> getNotReportByPatientUserId(Context context, int patientUserId){
        MutableLiveData<List<TestReport>> testReportLiveData = new MutableLiveData<>();
        ReportApi reportApi = getReportApi(context);
        Call<List<TestReport>> call = reportApi.getNotReportByPatientUserId(patientUserId);
        call.enqueue(new Callback<List<TestReport>>() {
            @Override
            public void onResponse(Call<List<TestReport>> call, Response<List<TestReport>> response) {
                if(response.isSuccessful()){
                    testReportLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TestReport>> call, Throwable t) {

            }
        });
        return testReportLiveData;
    }

    public MutableLiveData<ResponseBody> downloadReportFile(Context context, int reportId){
        MutableLiveData<ResponseBody> downloadResponse = new MutableLiveData<>();

        ReportApi reportApi = getReportApi(context);
        Call<ResponseBody> call = reportApi.downloadReportFile(reportId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    downloadResponse.setValue(response.body());
                    Log.d(context.getString(R.string.DEBUGING_TAG), "file download response: "+ response.body());
                }
                else {

                    //downloadResponse.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //downloadResponse.setValue(false);
            }
        });
        return downloadResponse;
    }


    private ReportApi getReportApi(Context context){
        ReportApiComponent reportApiComponent = DaggerReportApiComponent.builder()
                .reportApiModule(new ReportApiModule(context))
                .build();
        return reportApiComponent.getReportApi();
    }
}
