package com.example.onlinedoctor.login.view_model;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;


    public LoginViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new com.example.onlinedoctor.login.view_model.LoginViewModel();
    }
}