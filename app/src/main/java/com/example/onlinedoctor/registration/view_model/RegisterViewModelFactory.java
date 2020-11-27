package com.example.onlinedoctor.registration.view_model;


import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;


    public RegisterViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new com.example.onlinedoctor.registration.view_model.RegisterViewModel();
    }
}