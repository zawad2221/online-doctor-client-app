package com.example.onlinedoctor.patient.view_model;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.appointment.AppointmentRepository;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.model.VisitingSchedule;
import com.example.onlinedoctor.patient.PatientRepository;
import com.example.onlinedoctor.registration.RegistrationRepository;
import com.example.onlinedoctor.visiting_schedule.VisitingScheduleRepository;

import java.util.List;

public class PatientHomeViewModel extends ViewModel {
    private PatientRepository mPatientRepository;
    private RegistrationRepository mRegistrationRepository;
    private VisitingScheduleRepository mVisitingScheduleRepository;
    private AppointmentRepository mAppointmentRepository;

    private MutableLiveData<List<Specialization>> specializationList;
    private MutableLiveData<List<Chamber>> chamberList;
    private MutableLiveData<List<VisitingSchedule>> visitingScheduleList;

    public MutableLiveData<Integer> selectedChamberId = new MutableLiveData<>();
    public int selectedVisitingSchedule;
    public int selectedAppointmentFromBookedAppointmentPage;

    public int visitingScheduleRecyclerViewSelectedItem = -2;

    private MutableLiveData<Appointment> newMadeAppointment = new MutableLiveData<>();
    private MutableLiveData<Integer> bookedPatientNumber;
    private MutableLiveData<List<Appointment>> patientOldAppointment;
    private MutableLiveData<List<Appointment>> patientBookedAppointmentList;

    public MutableLiveData<String> bottomNavSelectedItem = new MutableLiveData<>("null");




    public void initSpecialization(){
        initRegistrationRepository();
        specializationList = mRegistrationRepository.getSpecialization();
    }
    public void getAppointmentByPatientIdVisitingScheduleIdAndDate(
            Context context,
            int patientId,
            int visitingScheduleId,
            String date
    ){
        initAppointmentRepository();
        patientOldAppointment = mAppointmentRepository.getAppointmentByPatientIdVisitingScheduleIdAndDate(
                context,
                patientId,
                visitingScheduleId,
                date);
    }

    public void getOldAppointmentOfPatient(Context context, int patientUserId, String dateOfToday){
        initPatientRepository();
        patientBookedAppointmentList = mPatientRepository.getOldAppointmentOfPatient(context, patientUserId, dateOfToday);
    }
    public void getNewAppointmentOfPatient(Context context, int patientUserId, String dateOfToday){
        initPatientRepository();
        patientBookedAppointmentList = mPatientRepository.getNewAppointmentOfPatient(context, patientUserId, dateOfToday);
    }


    public void getBookedAppointmentByPatientId(Activity activity, Context context, int patientId){
        initPatientRepository();
        patientBookedAppointmentList =mPatientRepository.getAppointmentByPatientId(activity, context, patientId);
    }

    public void getBookedPatientNumberOnScheduleIdAndDate(Context context,
                                                          int visitingScheduleId,
                                                          String date){
        initAppointmentRepository();
        bookedPatientNumber =mAppointmentRepository.getBookedPatientNumberOnScheduleIdAndDate(context,
                visitingScheduleId,
                date);
    }

    //get chamber list on specialization and location

    public void initChamber(int specializationId, Double[][] location){
        initPatientRepository();
        if(chamberList==null) chamberList = new MutableLiveData<>();
        chamberList = mPatientRepository.getChamberOnSpecializationAndLocation(
                specializationId,
                location
        );
    }

    public void initVisitingSchedule(Context context,
                                     int chamberId){
        initVisitingScheduleRepository();
        if(visitingScheduleList==null) visitingScheduleList = new MutableLiveData<>();
        visitingScheduleList = mVisitingScheduleRepository.getVisitingScheduleOnChamber(
                context,
                chamberId
        );
    }
    public void initVisitingSchedule(Context context,
                                     int chamberId,
                                     int specializationId){
        initPatientRepository();
        if(visitingScheduleList==null) visitingScheduleList = new MutableLiveData<>();
        visitingScheduleList = mVisitingScheduleRepository.getVisitingScheduleOnChamberAndSpecialization(
                context,
                chamberId,
                specializationId
        );
    }

    public void makeAppointment(Context context, Appointment appointment){
        initPatientRepository();
        newMadeAppointment=mPatientRepository.makeAppointment(context,appointment);
    }

    private void initPatientRepository(){
        if(mPatientRepository==null){
            mPatientRepository = PatientRepository.getInstance();
        }
    }
    private void initVisitingScheduleRepository(){
        if(mVisitingScheduleRepository==null){
            mVisitingScheduleRepository = VisitingScheduleRepository.getInstance();
        }
    }
    private void initRegistrationRepository(){
        if(mRegistrationRepository==null){
            mRegistrationRepository = RegistrationRepository.getInstance();
        }
    }
    private void initAppointmentRepository(){
        if(mAppointmentRepository==null){
            mAppointmentRepository = AppointmentRepository.getInstance();
        }
    }

    public MutableLiveData<List<Specialization>> getSpecializationList() {
        return specializationList;
    }

    public MutableLiveData<List<Chamber>> getChamberList() {
        return chamberList;
    }

    public MutableLiveData<List<VisitingSchedule>> getVisitingScheduleList() {
        return visitingScheduleList;
    }

    public MutableLiveData<Appointment> getNewMadeAppointment() {
        return newMadeAppointment;
    }

    public MutableLiveData<List<Appointment>> getPatientOldAppointment() {
        return patientOldAppointment;
    }

    public MutableLiveData<Integer> getBookedPatientNumber() {
        return bookedPatientNumber;
    }

    public MutableLiveData<List<Appointment>> getPatientBookedAppointmentList() {
        return patientBookedAppointmentList;
    }
}
