package com.example.onlinedoctor.prescription.api;

import android.content.Context;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.patient.api.PatientApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class PrescriptionApiModule {
    Context context;

    public PrescriptionApiModule(Context context) {
        this.context = context;
    }

    @Provides
    public PrescriptionApi providePrescriptionApi(){
        Retrofit.Builder builder= new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build().create(PrescriptionApi.class);
    }
}
