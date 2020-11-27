package com.example.onlinedoctor.registration.view_model;

import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private String currentSelectedFragment;

    //values patient,doctor,chamber,pathology
    private String selectedUser="";

    //for all user
    private String name, phoneNumber, password;

    //for patient only
    private String gender="", bloodGroup, nidNumber;
    int dateOfBirthYear=0, dateOfBirthMonth=0, dateOfBirthDay=0;

    //for doctor only
    private String bmdcID;


    public String getName() {
        return name;
    }

    public String getCurrentSelectedFragment() {
        return currentSelectedFragment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getNidNumber() {
        return nidNumber;
    }

    public int getDateOfBirthYear() {
        return dateOfBirthYear;
    }

    public int getDateOfBirthMonth() {
        return dateOfBirthMonth;
    }

    public int getDateOfBirthDay() {
        return dateOfBirthDay;
    }

    public String getBmdcID() {
        return bmdcID;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setCurrentSelectedFragment(String currentSelectedFragment) {
        this.currentSelectedFragment = currentSelectedFragment;
    }

    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
    }

    public void setDateOfBirthYear(int dateOfBirthYear) {
        this.dateOfBirthYear = dateOfBirthYear;
    }

    public void setDateOfBirthMonth(int dateOfBirthMonth) {
        this.dateOfBirthMonth = dateOfBirthMonth;
    }

    public void setDateOfBirthDay(int dateOfBirthDay) {
        this.dateOfBirthDay = dateOfBirthDay;
    }

    public void setBmdcID(String bmdcID) {
        this.bmdcID = bmdcID;
    }
}
