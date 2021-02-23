package com.example.onlinedoctor.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinedoctor.model.User;
import com.google.android.gms.common.util.RetainForClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRepository {

    private int LOGIN_SUCCESS_CODE =200;

    private static final String DEBUGING_TAG = "DEBUGING_TAG";

    public static LoginRepository instance;
    public static LoginRepository getInstance(){
        if(instance==null){
            instance = new LoginRepository();
        }
        return instance;
    }

    public MutableLiveData<Boolean> login(User user){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/user/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Call<User> call = loginApi.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()&&response.code()==LOGIN_SUCCESS_CODE){
                    result.setValue(true);
                    Log.d(DEBUGING_TAG,"login success");
                }
                else {
                    result.setValue(false);
                    Log.d(DEBUGING_TAG,"login is not success");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result.setValue(false);
                Log.d(DEBUGING_TAG,"login failed");
            }
        });
        return result;
    }
}
