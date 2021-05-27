package com.example.onlinedoctor.model;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.example.onlinedoctor.DateAndTime;
import com.example.onlinedoctor.login.LoginActivity;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class User {

    public static User loginUser = new User();

    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("userPhoneNumber")
    private String userPhoneNumber;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("userRole")
    private String userRole;
    @JsonProperty("password")
    private String password;



    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("userPhoneNumber")
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    @JsonProperty("userPhoneNumber")
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("userRole")
    public String getUserRole() {
        return userRole;
    }

    @JsonProperty("userRole")
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static void profileClick(Context context){
        if(User.loginUser!=null && User.loginUser.getUserId()!=null){
            switch (loginUser.getUserRole()){
                case "patient":
                    showPatientProfile();
                    break;
                case "doctor":
                    showDoctorProfile();
                    break;
                case "pathology":
                    showPathologyProfile();
                    break;
                case "chamber":
                    showChamberProfile();
                    break;

            }
        }
        else {
            context.startActivity(new Intent(context, LoginActivity.class));

        }
    }

    public static boolean isLoggedInUser(){
        if(User.loginUser.getUserId()==null) return false;
        else return true;
    }

    private static void showDoctorProfile() {
    }

    private static void showPathologyProfile() {
    }

    private static void showChamberProfile() {
    }

    private static void showPatientProfile() {
    }

    @BindingAdapter("android:setLinearLayoutVisibility")
    public static void setLinearLayoutVisibility(LinearLayout linearLayout, VisitingSchedule visitingSchedule){

        if(visitingSchedule.getIsCanceled()) linearLayout.setVisibility(View.VISIBLE);
        else  linearLayout.setVisibility(View.GONE);
    }
}
