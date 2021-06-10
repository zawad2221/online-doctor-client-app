package com.example.onlinedoctor.doctor.view_model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinedoctor.doctor.repository.DoctorMainRepository;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Prescription;
import com.example.onlinedoctor.model.Test;
import com.example.onlinedoctor.model.TestReport;
import com.example.onlinedoctor.model.VisitingSchedule;
import com.example.onlinedoctor.patient_report.ReportRepository;
import com.example.onlinedoctor.prescription.PrescriptionRepository;
import com.example.onlinedoctor.visiting_schedule.VisitingScheduleRepository;

import java.util.List;

public class DoctorMainViewModel extends ViewModel {
    private DoctorMainRepository doctorMainRepository;
    private PrescriptionRepository mPrescriptionRepository;
    private ReportRepository mReportRepository;
    private void initDoctorRepository(){
        if(doctorMainRepository==null){
            doctorMainRepository=DoctorMainRepository.getInstance();
        }
    }

    private VisitingScheduleRepository mVisitingScheduleRepository;
    private void initScheduleRepository(){
        if(mVisitingScheduleRepository==null){
            mVisitingScheduleRepository=VisitingScheduleRepository.getInstance();
        }
    }

    //visiting schedule list
    private MutableLiveData<List<VisitingSchedule>> visitingScheduleList = new MutableLiveData<>();
    public int selectedVisitingScheduleItem;
    public void initVisitingSchedule(Context context, int doctorUserId){
        initDoctorRepository();
        visitingScheduleList = doctorMainRepository.getVisitingScheduleByDoctorUserId(context, doctorUserId);
    }
    public MutableLiveData<List<VisitingSchedule>> getVisitingScheduleList() {
        return visitingScheduleList;
    }

    //schedule appointment list
    private MutableLiveData<List<Appointment>> appointmentList = new MutableLiveData<>();
    public int selectedVisitingScheduleAppointmentItem;
    public void initScheduleAppointment(Context context, int doctorUserId, int scheduleId, String date){
        initDoctorRepository();
        appointmentList = doctorMainRepository.getAppointmentByDoctorUserIdScheduleIdAndDate(context, doctorUserId, scheduleId, date);
    }
    public MutableLiveData<List<Appointment>> getAppointmentList(){
        return appointmentList;
    }

    //patient report
    private MutableLiveData<List<TestReport>> patientTestReportList = new MutableLiveData<>();
    private void initReportRepository(){
        if(mReportRepository==null) mReportRepository=ReportRepository.getInstance();
    }
    public void getPatientReportByPatientUserId(Context context, int patientUserId){
        initReportRepository();
        patientTestReportList = mReportRepository.getReportByPatientUserId(context,patientUserId);
    }
    public void getDoneReportByPatientUserId(Context context, int patientUserId){
        initReportRepository();
        patientTestReportList = mReportRepository.getDoneReportByPatientUserId(context,patientUserId);
    }
    public void getNotReportByPatientUserId(Context context, int patientUserId){
        initReportRepository();
        patientTestReportList = mReportRepository.getNotReportByPatientUserId(context,patientUserId);
    }
    public MutableLiveData<List<TestReport>> getPatientTestReportList(){
        return patientTestReportList;
    }

    //patient prescription
    private MutableLiveData<List<Prescription>> prescriptionListLiveData = new MutableLiveData<>();
    private void initPrescriptionRepository(){
        if(mPrescriptionRepository==null) mPrescriptionRepository = PrescriptionRepository.getInstance();
    }
    public void getPrescriptionListByPatientUserId(Context context, int patientUserId){
        initPrescriptionRepository();
        prescriptionListLiveData =  mPrescriptionRepository.getPrescriptionByPatientUserId(context,patientUserId);
    }
    public MutableLiveData<List<Prescription>> getPrescriptionListLiveData(){
        return prescriptionListLiveData;
    }
    public int selectedPrescriptionItem;

    //search chamber
    private MutableLiveData<List<Chamber>> searchChamberList = new MutableLiveData<>();
    public void searchChamber(Context context, String query){
        initDoctorRepository();
        searchChamberList = doctorMainRepository.searchChamber(context,query);
    }
    public MutableLiveData<List<Chamber>> getSearchChamberList(){
        return searchChamberList;
    }
    public int selectedChamberItemPosition;

    //days of week
    public String[] daysOfWeek = new String[]{"saturday","sunday","monday","tuesday","wednesday","thursday","friday"};
    //new schedule
    public VisitingSchedule newSchedule = new VisitingSchedule();
    private MutableLiveData<VisitingSchedule> visitingScheduleResponse = new MutableLiveData<>();
    public MutableLiveData<VisitingSchedule> getVisitingScheduleResponse(){
        return visitingScheduleResponse;
    }
    public void createVisitingSchedule(Context context, VisitingSchedule visitingSchedule){
        initScheduleRepository();
        visitingScheduleResponse = mVisitingScheduleRepository.createVisitingSchedule(context, visitingSchedule);
    }

}
