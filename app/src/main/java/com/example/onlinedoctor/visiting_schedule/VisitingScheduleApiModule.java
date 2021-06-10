package com.example.onlinedoctor.visiting_schedule;

import android.content.Context;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.doctor.api.DoctorApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class VisitingScheduleApiModule {
    Context context;

    public VisitingScheduleApiModule(Context context) {
        this.context = context;
    }
    @Provides
    public VisitingScheduleApi provideVisitingScheduleApi(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build().create(VisitingScheduleApi.class);
    }
}
