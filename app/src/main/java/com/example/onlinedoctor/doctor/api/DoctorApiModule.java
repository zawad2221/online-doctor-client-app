package com.example.onlinedoctor.doctor.api;

import android.content.Context;

import com.example.onlinedoctor.R;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DoctorApiModule {
    Context context;

    public DoctorApiModule(Context context) {
        this.context = context;
    }

    @Provides
    public DoctorApi provideDoctorApi(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build().create(DoctorApi.class);
    }
}
