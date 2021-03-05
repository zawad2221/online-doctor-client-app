package com.example.onlinedoctor.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.onlinedoctor.R;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.MainActivity;
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

    public MutableLiveData<Boolean> login(User user, Context context){
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
                    Log.d(DEBUGING_TAG,"login success: ");
                    saveLoginUser(response.body(),context);
                    redirectToUserPage(context, response.body());
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

    private void redirectToUserPage(Context context, User user){
        Log.d(DEBUGING_TAG,"redirecting....User: "+user.getUserRole());
        if(user.getUserRole().equals("patient")){
            context.startActivity(new Intent(context, MainActivity.class));


        }

    }

    private void saveLoginUser(User user, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.LOGIN_USER_FILE_NAME),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(
                context.getString(R.string.LOGIN_USER_PHONE_NUMBER_PREFERENCE_KEY),
                        user.getUserPhoneNumber()
        );
        editor.putString(
                context.getString(R.string.LOGIN_USER_NAME_PREFERENCE_KEY),
                user.getUserName()
        );
        editor.putString(
                context.getString(R.string.LOGIN_USER_ROLE_PREFERENCE_KEY),
                user.getUserRole()
        );
        editor.putString(
                context.getString(R.string.LOGIN_USER_ID_PREFERENCE_KEY),
                user.getUserId().toString()
        );
        editor.apply();

    }
}
