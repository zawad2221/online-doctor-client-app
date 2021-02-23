package com.example.onlinedoctor.registration.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.registration.RegistrationRepository;
import com.example.onlinedoctor.registration.model.District;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<List<Specialization>> specializedArea;
    private MutableLiveData<Boolean> registrationResponse;

    private RegistrationRepository registrationRepository;

    private ArrayList<District> districts;

    private AllRegisterFragment currentSelectedFragment;

    public enum AllRegisterFragment{
        PATIENT_REGISTER_FORM,
        DOCTOR_REGISTER_FORM,
        CHAMBER_REGISTER_FORM,
        PATHOLOGY_REGISTER_FORM,
        SELECT_USER_FORM,
        VERIFY_OTP_FORM,
         CHAMBER_SELECT_LOCATION_FORM,
         PATHOLOGY_SELECT_LOCATION_FORM
    }

    //values patient,doctor,chamber,pathology
    private AllUserType selectedUser=null;
    public enum AllUserType{
        DOCTOR,
        PATIENT,
        CHAMBER,
        PATHOLOGY
    }

    //for patient only
    private String patientName, patientPhoneNumber, patientPassword;
    private String patientGender, patientBloodGroup, patientNidNumber;
    int dateOfBirthYear=0, dateOfBirthMonth=0, dateOfBirthDay=0;

    //for doctor only
    private String doctorBmdcId, doctorName, doctorPhoneNumber, doctorDesignation, doctorPassword;
    private Specialization specialization;


    //for chamber and
    private LatLng chamberLocationLatLan =null;
    private String chamberName, chamberPhoneNumber, chamberAddress, chamberPassword;

    //for  and pathology
    private LatLng pathologyLocationLatLan =null;
    private String pathologyName, pathologyPhoneNumber, pathologyAddress, pathologyPassword;

    //method#########################################################################

    private void initRegistrationRepo(){
        if(registrationRepository==null){
            registrationRepository = RegistrationRepository.getInstance();
        }
    }

    public void initDistricts(){
        districts = new ArrayList<>();
        ArrayList<String> subDistrictDhaka = new ArrayList<String>();
        subDistrictDhaka.add("Dhamrai");
        subDistrictDhaka.add("Keraniganj");
        districts.add(new District("Dhaka",subDistrictDhaka));
    }



    public void initSpecializedArea(){
        initRegistrationRepo();
        if (specializedArea==null){
            specializedArea = new MutableLiveData<>();
        }
        specializedArea = registrationRepository.getSpecialization();


    }

    public <T> void registerUser(T object){
        initRegistrationRepo();
        registrationResponse = registrationRepository.registration(object);
    }


    ////###################################################################################

    public MutableLiveData<Boolean> getRegistrationResponse() {
        return registrationResponse;
    }

    public void setRegistrationResponse(MutableLiveData<Boolean> registrationResponse) {
        this.registrationResponse = registrationResponse;
    }

    public MutableLiveData<List<Specialization>> getSpecializedArea() {
        return specializedArea;
    }

    public RegistrationRepository getRegistrationRepository() {
        return registrationRepository;
    }

    public void setRegistrationRepository(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public void setChamberLocationLatLan(LatLng chamberLocationLatLan) {
        this.chamberLocationLatLan = chamberLocationLatLan;
    }

    public void setPathologyLocationLatLan(LatLng pathologyLocationLatLan) {
        this.pathologyLocationLatLan = pathologyLocationLatLan;
    }

    public void setPathologyName(String pathologyName) {
        this.pathologyName = pathologyName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setPathologyPhoneNumber(String pathologyPhoneNumber) {
        this.pathologyPhoneNumber = pathologyPhoneNumber;
    }

    public void setPathologyAddress(String pathologyAddress) {
        this.pathologyAddress = pathologyAddress;
    }

    public void setPathologyPassword(String pathologyPassword) {
        this.pathologyPassword = pathologyPassword;
    }

    public LatLng getChamberLocationLatLan() {
        return chamberLocationLatLan;
    }

    public LatLng getPathologyLocationLatLan() {
        return pathologyLocationLatLan;
    }

    public String getPathologyName() {
        return pathologyName;
    }


    public String getPathologyPhoneNumber() {
        return pathologyPhoneNumber;
    }

    public String getPathologyAddress() {
        return pathologyAddress;
    }

    public String getPathologyPassword() {
        return pathologyPassword;
    }

    public String getDoctorBmdcId() {
        return doctorBmdcId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorBmdcId(String doctorBmdcId) {
        this.doctorBmdcId = doctorBmdcId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorPhoneNumber(String doctorPhoneNumber) {
        this.doctorPhoneNumber = doctorPhoneNumber;
    }

    public void setDoctorDesignation(String doctorDesignation) {
        this.doctorDesignation = doctorDesignation;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public void setSpecializedArea(MutableLiveData<List<Specialization>> specializedArea) {
        this.specializedArea = specializedArea;
    }

    public String getDoctorPhoneNumber() {
        return doctorPhoneNumber;
    }

    public String getDoctorDesignation() {
        return doctorDesignation;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setChamberAddress(String chamberAddress) {
        this.chamberAddress = chamberAddress;
    }

    public String getChamberAddress() {
        return chamberAddress;
    }

    public String getPatientName() {
        return patientName;
    }

    public AllRegisterFragment getCurrentSelectedFragment() {
        return currentSelectedFragment;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public String getPatientBloodGroup() {
        return patientBloodGroup;
    }

    public String getPatientNidNumber() {
        return patientNidNumber;
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



    public AllUserType getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(AllUserType selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public void setPatientBloodGroup(String patientBloodGroup) {
        this.patientBloodGroup = patientBloodGroup;
    }

    public void setCurrentSelectedFragment(AllRegisterFragment currentSelectedFragment) {
        this.currentSelectedFragment = currentSelectedFragment;
    }

    public void setPatientNidNumber(String patientNidNumber) {
        this.patientNidNumber = patientNidNumber;
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



    public ArrayList<District> getDistricts() {
        return districts;
    }



    public void setDistricts(ArrayList<District> districts) {
        this.districts = districts;
    }



    public String getChamberName() {
        return chamberName;
    }



    public String getChamberPhoneNumber() {
        return chamberPhoneNumber;
    }

    public String getChamberPassword() {
        return chamberPassword;
    }

    public void setChamberName(String chamberName) {
        this.chamberName = chamberName;
    }



    public void setChamberPhoneNumber(String chamberPhoneNumber) {
        this.chamberPhoneNumber = chamberPhoneNumber;
    }

    public void setChamberPassword(String chamberPassword) {
        this.chamberPassword = chamberPassword;
    }
}
