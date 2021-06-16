package com.example.onlinedoctor.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.ActivityLoginBinding;
import com.example.onlinedoctor.doctor.activity.DoctorMainActivity;
import com.example.onlinedoctor.login.view_model.LoginViewModel;
import com.example.onlinedoctor.login.view_model.LoginViewModelFactory;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.MainActivity;
import com.example.onlinedoctor.registration.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private LoginViewModel mLoginViewModel;
    ActivityLoginBinding mActivityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view  = mActivityLoginBinding.getRoot();
        setContentView(view);
        initViewModel();

        mActivityLoginBinding.loginForm.cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mActivityLoginBinding.loginForm.signUpLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        mActivityLoginBinding.closeActivity.setOnClickListener(v -> {
            finish();
            startMainActivity();
        });
        

    }
    private void startMainActivity(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startMainActivity();

    }

    private void login() {
        if(isValidInput()){
            showLoginProgressDialog();
            User user  = new User();
            user.setUserPhoneNumber(getPhoneNumber());
            user.setPassword(getPassword());
            mLoginViewModel.login(user, this);
            loginResponseObserver();
        }
    }

    private void loginResponseObserver(){
        mLoginViewModel.getLoginResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getUserId()!=-1){
                    saveLoginUser(user);
                    showLoginResponseDialog("login success",true, user);
                    User.loginUser = user;

                }
                else {
                    showLoginResponseDialog("login failed. invalid phone number or password",false, user);
                }
                progressDialog.dismiss();
            }
        });
    }
    private void redirectToUserPage(User user){
        Log.d(getString(R.string.DEBUGING_TAG),"redirecting....User: "+user.getUserRole());
        if(user.getUserRole().equals("patient")){

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();


        }
        else if(user.getUserRole().equals("doctor")){
            startActivity(new Intent(LoginActivity.this, DoctorMainActivity.class));
            finish();
        }

    }

    private void showLoginProgressDialog(){
        progressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verifying.....");
        progressDialog.show();
    }

    private void showLoginResponseDialog(String message, boolean response, User user){
        AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setTitle(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(response){
                            redirectToUserPage(user);

                        }
                        else {
                            //login failed
                        }
                    }
                })
                .setCancelable(false)
                .create();
                alertDialog.show();

    }
    private void saveLoginUser(User user){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.LOGIN_USER_FILE_NAME),
                MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(
                getString(R.string.LOGIN_USER_PHONE_NUMBER_PREFERENCE_KEY),
                user.getUserPhoneNumber()
        );
        editor.putString(
                getString(R.string.LOGIN_USER_NAME_PREFERENCE_KEY),
                user.getUserName()
        );
        editor.putString(
                getString(R.string.LOGIN_USER_ROLE_PREFERENCE_KEY),
                user.getUserRole()
        );
        editor.putString(
                getString(R.string.LOGIN_USER_ID_PREFERENCE_KEY),
                user.getUserId().toString()
        );
        editor.apply();

    }

    private boolean isValidInput(){
        boolean isValid = false;
        if(isValidPhoneNumber()){
            isValid = true;
        }
        else {

            showError(mActivityLoginBinding.loginForm.editTextPhoneNumber, "invalid phone number");
            return false;
        }
        if(isValidPasswordInput()){
            isValid = true;
        }
        else {

            showError(mActivityLoginBinding.loginForm.editTextPassword,
                    "invalid password input");
            return false;
        }
        return isValid;
    }
    private void showError(EditText editText, String message){
        editText.setError(message);
    }

    private String getPhoneNumber(){
        return mActivityLoginBinding.loginForm.editTextPhoneNumber.getText().toString();
    }
    private String getPassword(){
        return mActivityLoginBinding.loginForm.editTextPassword.getText().toString();
    }

    private boolean isValidPasswordInput(){
        return !getPassword().isEmpty();
    }
    private boolean isValidPhoneNumber(){
        String phoneNumber = getPhoneNumber();
        return !(phoneNumber.isEmpty() || !(phoneNumber.length() ==11) || !phoneNumber.startsWith("01"));
    }

    private void initViewModel(){
        if(mLoginViewModel==null){
            mLoginViewModel = new ViewModelProvider(this,
                    new LoginViewModelFactory(this.getApplication(),"test"))
                    .get(LoginViewModel.class);
        }
    }

}