package com.example.onlinedoctor.appointment;

import android.content.Context;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.doctor.api.DoctorApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppointmentApiModule {
    Context context;

    public AppointmentApiModule(Context context) {
        this.context = context;
    }

    @Provides
    public AppointmentApi provideAppointmentApi(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build().create(AppointmentApi.class);
    }
}
