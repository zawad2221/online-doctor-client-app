package com.example.onlinedoctor.patient.api;

import android.app.Activity;

import dagger.Component;

@Component(modules = {PatientApiModule.class})
public interface PatientApiComponent {
    PatientApi getPatientApi();
    public void inject(Activity activity);

}
