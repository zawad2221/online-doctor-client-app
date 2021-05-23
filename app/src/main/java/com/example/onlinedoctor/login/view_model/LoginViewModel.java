package com.example.onlinedoctor.login.view_model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.login.LoginRepository;
import com.example.onlinedoctor.model.User;

public class LoginViewModel extends ViewModel {


    private MutableLiveData<User> loginResponse = new MutableLiveData<>();
    private LoginRepository loginRepository;

    public void login(User user, Context context){
        initLoginRepo();
        loginResponse = loginRepository.login(user, context);

    }
    private void initLoginRepo(){
        if(loginRepository==null){
            loginRepository=LoginRepository.getInstance();
        }
    }


    public MutableLiveData<User> getLoginResponse() {
        return loginResponse;
    }
}
