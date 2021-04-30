package com.example.onlinedoctor.patient.api;

import android.content.Context;

import com.example.onlinedoctor.R;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class PatientApiModule {
    Context context;

    public PatientApiModule(Context context) {
        this.context = context;
    }

    @Provides
    public PatientApi providePatientApi(){
        Retrofit.Builder builder= new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build().create(PatientApi.class);
    }
}
