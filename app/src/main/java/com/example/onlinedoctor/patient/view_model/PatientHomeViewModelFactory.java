package com.example.onlinedoctor.patient.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PatientHomeViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;

    public PatientHomeViewModelFactory(Application mApplication, String mParam) {
        this.mApplication = mApplication;
        this.mParam = mParam;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PatientHomeViewModel();
    }
}
