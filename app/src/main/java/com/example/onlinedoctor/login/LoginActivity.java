package com.example.onlinedoctor.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.ActivityLoginBinding;
import com.example.onlinedoctor.login.view_model.LoginViewModel;
import com.example.onlinedoctor.login.view_model.LoginViewModelFactory;
import com.example.onlinedoctor.model.User;
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
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        

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
        mLoginViewModel.getLoginResponse().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showLoginResponseDialog("login success",aBoolean);
                }
                else {
                    showLoginResponseDialog("login failed. invalid phone number or password",aBoolean);
                }
                progressDialog.dismiss();
            }
        });
    }

    private void showLoginProgressDialog(){
        progressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verifying.....");
        progressDialog.show();
    }

    private void showLoginResponseDialog(String message, boolean response){
        AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setTitle(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(response){
                            //login success

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