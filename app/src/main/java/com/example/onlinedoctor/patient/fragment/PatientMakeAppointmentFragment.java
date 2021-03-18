package com.example.onlinedoctor.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.DateAndTime;
import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientMakeAppointmentBinding;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;


public class PatientMakeAppointmentFragment extends Fragment {
    private PatientHomeViewModel patientHomeViewModel;
    private FragmentPatientMakeAppointmentBinding mFragmentPatientMakeAppointmentBinding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentPatientMakeAppointmentBinding = FragmentPatientMakeAppointmentBinding
                .inflate(inflater, container, false);
        initViewModel();
        setVisitingScheduleDataOnView();

        return mFragmentPatientMakeAppointmentBinding.getRoot();
    }

    private void setVisitingScheduleDataOnView(){
        if(isScheduleCanceled()){
            setIsCanceledTextVisibility(View.VISIBLE);
            setMakeAppointmentButtonEnableStatus(false);
        }
        setDoctorNameInView();
        setDoctorSpecializationInView();
        setScheduleTimeDataInView(getScheduleTime());
        saveAppointmentDate();

        setAppointmentDateInView();
        setFeeData(
                patientHomeViewModel
                        .getVisitingScheduleList()
                        .getValue()
                        .get(patientHomeViewModel.selectedVisitingSchedule)
                        .getVisitingScheduleFee()
                        .getNewFee().toString(),
                patientHomeViewModel
                        .getVisitingScheduleList()
                        .getValue()
                        .get(patientHomeViewModel.selectedVisitingSchedule)
                        .getVisitingScheduleFee()
                        .getOldFee().toString(),
                patientHomeViewModel
                        .getVisitingScheduleList()
                        .getValue()
                        .get(patientHomeViewModel.selectedVisitingSchedule)
                        .getVisitingScheduleFee()
                        .getReportFee().toString()
        );
        setDayOfWeedDataInView();
        setMaxPatientNumberInView();


    }

    private void setAppointmentDateInView() {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.scheduleDate.setText(
                patientHomeViewModel
                        .getVisitingScheduleList()
                        .getValue()
                        .get(patientHomeViewModel.selectedVisitingSchedule)
                        .getAppointmentDate()
        );
    }

    private void saveAppointmentDate() {
        patientHomeViewModel
                .getVisitingScheduleList()
                .getValue()
                .get(patientHomeViewModel.selectedVisitingSchedule)
                .setAppointmentDate(getAppointmentDate());
        Log.d(getString(R.string.DEBUGING_TAG),"appointment date"+getAppointmentDate());
    }

    private void setScheduleTimeDataInView(String scheduleTime) {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.scheduleTime.setText(
                scheduleTime
        );
    }

    private String getScheduleTime() {
        return DateAndTime.convert24to12(
                patientHomeViewModel.getVisitingScheduleList().getValue().get(
                        patientHomeViewModel.selectedVisitingSchedule
                ).getStartAt()
        )
                +"-"
                +DateAndTime.convert24to12(
                patientHomeViewModel.getVisitingScheduleList().getValue().get(
                        patientHomeViewModel.selectedVisitingSchedule
                ).getEndAt()
        );
    }

    private void setDoctorSpecializationInView() {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.specialization.setText(
                patientHomeViewModel.getVisitingScheduleList().getValue().get(
                        patientHomeViewModel.selectedVisitingSchedule
                ).getVisitingScheduleDoctor().getDoctorSpecialization().getSpecializationName()
        );
    }

    private void setDoctorNameInView() {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.doctorName.setText(
                patientHomeViewModel.getVisitingScheduleList().getValue().get(
                        patientHomeViewModel.selectedVisitingSchedule
                ).getVisitingScheduleDoctor().getDoctorUser().getUserName()
        );
    }

    private void setDayOfWeedDataInView(){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout
                .scheduleDayOfWeek.setText(
                        patientHomeViewModel.getVisitingScheduleList().getValue()
                .get(
                        patientHomeViewModel.selectedVisitingSchedule
                )
                .getVisitingScheduleDaysOfWeek().getDay().toUpperCase()
        );
    }
    private void setMaxPatientNumberInView(){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout
                .maxPatientNumberTextView.setText(
                patientHomeViewModel.getVisitingScheduleList().getValue()
                        .get(
                                patientHomeViewModel.selectedVisitingSchedule
                        ).getMaxPatient().toString()
        );
    }

    private String getAppointmentDate(){
        if(DateAndTime.isDaysOfWeekIsToday(
                getDayOfWeek(
                        patientHomeViewModel.getVisitingScheduleList().getValue().get(
                                patientHomeViewModel.selectedVisitingSchedule
                        ).getVisitingScheduleDaysOfWeek().getDay()
                )
        )){
            if(!DateAndTime.isTimeOneGreaterThanTimeTwo(
                    DateAndTime.getLocalTime(),
                    patientHomeViewModel.getVisitingScheduleList().getValue().get(
                            patientHomeViewModel.selectedVisitingSchedule
                    ).getStartAt()
            )){
                return getDateOfNextDayOfWeek(getDayOfWeek(
                        patientHomeViewModel.getVisitingScheduleList().getValue().get(
                                patientHomeViewModel.selectedVisitingSchedule
                        ).getVisitingScheduleDaysOfWeek().getDay()
                ));
            }
            else {
                return getLocalDate();
            }
        }
        else {
            return getDateOfNextDayOfWeek(getDayOfWeek(
                    patientHomeViewModel.getVisitingScheduleList().getValue().get(
                            patientHomeViewModel.selectedVisitingSchedule
                    ).getVisitingScheduleDaysOfWeek().getDay()
            ));
        }
    }

    private String getLocalDate(){
        return DateAndTime.getLocalDate();
    }
    private String getDateOfNextDayOfWeek(DateAndTime.DAYS_OF_WEEK daysOfWeek){
        return DateAndTime.getDateOfNextDayOfWeek(daysOfWeek);
    }

    private boolean isScheduleCanceled(){
        return patientHomeViewModel.getVisitingScheduleList()
                .getValue()
                .get(patientHomeViewModel.selectedVisitingSchedule)
                .getIsCanceled();
    }


    private void setMakeAppointmentButtonEnableStatus(boolean status){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.makeAppointmentButton.setEnabled(status);
    }
    private void setIsCanceledTextVisibility(int visibility){
        mFragmentPatientMakeAppointmentBinding
                .makeAppointmentLayout
                .isCanceledStatusTextView
                .setVisibility(visibility);
    }

    private void setFeeData(String newFee, String oldFee, String reportShowingFee){
        setNewFeeData(newFee);
        setOldFeeData(oldFee);
        setReportShowingFeeData(reportShowingFee);
    }
    private void setNewFeeData(String newFee){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.newPatientFee.setText(newFee);
    }
    private void setOldFeeData(String oldFee){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.oldPatientFee.setText(oldFee);
    }
    private void setReportShowingFeeData(String reportShowingFee) {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.reportShowingFeeTextView.setText(reportShowingFee);
    }

    private void initViewModel(){
        patientHomeViewModel = new ViewModelProvider(getActivity()).get(PatientHomeViewModel.class);
    }
    private DateAndTime.DAYS_OF_WEEK getDayOfWeek(String day){
        switch (day){
            case "saturday":
                return DateAndTime.DAYS_OF_WEEK.SATURDAY;
            case "sunday":
                return DateAndTime.DAYS_OF_WEEK.SUNDAY;
            case "monday":
                return DateAndTime.DAYS_OF_WEEK.MONDAY;
            case "tuesday":
                return DateAndTime.DAYS_OF_WEEK.TUESDAY;
            case "wednesday":
                return DateAndTime.DAYS_OF_WEEK.WEDNESDAY;
            case "thursday":
                return DateAndTime.DAYS_OF_WEEK.THURSDAY;
            case "friday":
                return DateAndTime.DAYS_OF_WEEK.FRIDAY;
            default: return null;

        }
    }
}