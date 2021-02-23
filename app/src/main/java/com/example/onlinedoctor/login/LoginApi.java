package com.example.onlinedoctor.login;

import com.example.onlinedoctor.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("login/")
    public Call<User> login(@Body User user);

}
