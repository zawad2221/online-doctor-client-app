package com.example.onlinedoctor.visiting_schedule;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.doctor.api.DaggerDoctorApiComponent;
import com.example.onlinedoctor.doctor.api.DoctorApi;
import com.example.onlinedoctor.doctor.api.DoctorApiComponent;
import com.example.onlinedoctor.doctor.api.DoctorApiModule;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitingScheduleRepository {
    public static VisitingScheduleRepository instance;

    public static VisitingScheduleRepository getInstance(){
        if(instance==null){
            instance = new VisitingScheduleRepository();
        }
        return instance;
    }

    public MutableLiveData<List<VisitingSchedule>> getVisitingScheduleOnChamberAndSpecialization(Context context,
                                                                                                 int chamberId,
                                                                                                 int specializationId){
        return getVisitingSchedule(context,chamberId,specializationId);
    }
    public MutableLiveData<List<VisitingSchedule>> getVisitingScheduleOnChamber(Context context, int chamberId){
        return getVisitingSchedule(context,chamberId,0);
    }
    public MutableLiveData<List<VisitingSchedule>> getVisitingSchedule(Context context,
                                                                       int chamberId,
                                                                       int specializationId){
        MutableLiveData<List<VisitingSchedule>> visitingSchedule = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url)+"visitingSchedule/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        VisitingScheduleApi visitingScheduleApi = retrofit.create(VisitingScheduleApi.class);
        Call<List<VisitingSchedule>> call = visitingScheduleApi
                .getVisitingScheduleOnChamberAndSpecialization(chamberId,
                        specializationId);
        call.enqueue(new Callback<List<VisitingSchedule>>() {
            @Override
            public void onResponse(Call<List<VisitingSchedule>> call, Response<List<VisitingSchedule>> response) {
                if(response.isSuccessful() && response.code()==200){
                    visitingSchedule.setValue(response.body());
                    Log.d(context.getString(R.string.DEBUGING_TAG),"success to get schedule data");
                }
                Log.d(context.getString(R.string.DEBUGING_TAG),"unsucces to get schedule data");
            }

            @Override
            public void onFailure(Call<List<VisitingSchedule>> call, Throwable t) {
                Log.d(context.getString(R.string.DEBUGING_TAG),"failed to get schedule list data");
            }
        });
        return visitingSchedule;
    }

    public MutableLiveData<VisitingSchedule> createVisitingSchedule(Context context, VisitingSchedule visitingSchedule){
        MutableLiveData<VisitingSchedule> visitingScheduleResponse = new MutableLiveData<>();
        VisitingScheduleApi visitingScheduleApi = getVisitingScheduleApi(context);
        Call<VisitingSchedule> call = visitingScheduleApi.createVisitingSchedule(visitingSchedule);
        call.enqueue(new Callback<VisitingSchedule>() {
            @Override
            public void onResponse(Call<VisitingSchedule> call, Response<VisitingSchedule> response) {
                if(response.isSuccessful()){
                    visitingScheduleResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<VisitingSchedule> call, Throwable t) {

            }
        });
        return visitingScheduleResponse;
    }

    private VisitingScheduleApi getVisitingScheduleApi(Context context){
        VisitingScheduleApiComponent visitingScheduleApiComponent = DaggerVisitingScheduleApiComponent.builder()
                .visitingScheduleApiModule(new VisitingScheduleApiModule(context))
                .build();
        return visitingScheduleApiComponent.getVisitingScheduleApi();
    }

}
