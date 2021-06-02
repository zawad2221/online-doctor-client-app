package com.example.onlinedoctor.patient.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.onlinedoctor.DateAndTime;
import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientMakeAppointmentBinding;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.model.Patient;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.model.VisitingSchedule;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;

import java.util.List;


public class PatientMakeAppointmentFragment extends Fragment {
    private PatientHomeViewModel mPatientHomeViewModel;
    private FragmentPatientMakeAppointmentBinding mFragmentPatientMakeAppointmentBinding;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleOnBackPressed() {
                closeFragment();
            }
            private void closeFragment() {
                // Disable to close fragment
                this.setEnabled(false);
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

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

    private void getBookedPatientNumber(){
        mPatientHomeViewModel.getBookedPatientNumberOnScheduleIdAndDate(
                getContext(),
                getCurrentSelectedVisitingSchedule().getVisitingScheduleId(),
                getAppointmentDate()
        );
    }
    private void bookedPatientNumberObserver(){
        mPatientHomeViewModel.getBookedPatientNumber().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer!=-1){
                    setBookedPatientNumberInView(integer);
                    dismissProgressDialog();
                    if(isBookedPatientIsEqualOrGreaterThanMaxPatient()){
                        setMakeAppointmentButtonEnableStatus(false);
                    }
                }
            }
        });
    }

    private boolean isBookedPatientIsEqualOrGreaterThanMaxPatient(){
        return mPatientHomeViewModel.getBookedPatientNumber().getValue()>=getCurrentSelectedVisitingSchedule().getMaxPatient();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentPatientMakeAppointmentBinding
                .makeAppointmentLayout
                .makeAppointmentButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeAppointmentButtonOnClick();
                    }
                });
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.closeFragment
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requireActivity().getOnBackPressedDispatcher().onBackPressed();
                    }
                });

        getBookedPatientNumber();
        showProgressDialog("Loading Data......");
        bookedPatientNumberObserver();
        checkForAlreadyMadeAppointment();
        alreadyMadeAppointmentObserver();
    }



    private void checkForAlreadyMadeAppointment(){
        mPatientHomeViewModel.getAppointmentByPatientIdVisitingScheduleIdAndDate(
                getContext(),
                User.loginUser.getUserId(),
                getCurrentSelectedVisitingSchedule().getVisitingScheduleId(),
                getAppointmentDate()
        );


    }
    private void alreadyMadeAppointmentObserver(){
        mPatientHomeViewModel.getPatientOldAppointment().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointment) {
                Log.d(getString(R.string.DEBUGING_TAG),"on change already");
                if(appointment.size()!=0){
                    setMakeAppointmentButtonEnableStatus(false);
                }
            }
        });
    }

    private void setScheduleAdditionalInfoInView(){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.scheduleAdditionalInformation.setText(
                getCurrentSelectedVisitingSchedule().getVisitingScheduleAdditionalInformation()
        );
    }


    private void setVisitingScheduleDataOnView(){
        if(isScheduleCanceled()){
            setIsCanceledTextVisibility(View.VISIBLE);
            setMakeAppointmentButtonEnableStatus(false);
        }
        setScheduleAdditionalInfoInView();
        setDoctorNameInView();
        setDoctorSpecializationInView();
        setScheduleTimeDataInView(getScheduleTime());
        saveAppointmentDate();

        setAppointmentDateInView();
        setFeeData(
                getCurrentSelectedVisitingSchedule()
                        .getVisitingScheduleFee()
                        .getNewFee().toString(),
                getCurrentSelectedVisitingSchedule()
                        .getVisitingScheduleFee()
                        .getOldFee().toString(),
                getCurrentSelectedVisitingSchedule()
                        .getVisitingScheduleFee()
                        .getReportFee().toString()
        );
        setDayOfWeedDataInView();
        setMaxPatientNumberInView();


    }
    private VisitingSchedule getCurrentSelectedVisitingSchedule(){
        return mPatientHomeViewModel
                .getVisitingScheduleList()
                .getValue()
                .get(mPatientHomeViewModel.selectedVisitingSchedule);
    }

    private void setAppointmentDateInView() {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.scheduleDate.setText(
                getCurrentSelectedVisitingSchedule()
                        .getAppointmentDate()
        );
    }

    private void saveAppointmentDate() {
        getCurrentSelectedVisitingSchedule()
                .setAppointmentDate(getAppointmentDate());
        Log.d(getString(R.string.DEBUGING_TAG),"appointment date"+getAppointmentDate());
    }

    private void setScheduleTimeDataInView(String scheduleTime) {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.scheduleTime.setText(
                scheduleTime
        );
    }

    private void setBookedPatientNumberInView(int number){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout
                .bookedPatientNumberTextView
                .setText(String.valueOf(number));
    }

    private String getScheduleTime() {
        return DateAndTime.convert24to12(
                getCurrentSelectedVisitingSchedule().getStartAt()
        )
                +"-"
                +DateAndTime.convert24to12(
                getCurrentSelectedVisitingSchedule().getEndAt()
        );
    }

    private void setDoctorSpecializationInView() {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.specialization.setText(
                getCurrentSelectedVisitingSchedule()
                        .getVisitingScheduleDoctor()
                        .getDoctorSpecialization()
                        .getSpecializationName()
        );
    }

    private void setDoctorNameInView() {
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.doctorName.setText(
                getCurrentSelectedVisitingSchedule()
                        .getVisitingScheduleDoctor()
                        .getDoctorUser().getUserName()
        );
    }

    private void setDayOfWeedDataInView(){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout
                .scheduleDayOfWeek.setText(
                getCurrentSelectedVisitingSchedule()
                .getVisitingScheduleDaysOfWeek()
                        .getDay()
                        .toUpperCase()
        );
    }
    private void setMaxPatientNumberInView(){
        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout
                .maxPatientNumberTextView.setText(
                getCurrentSelectedVisitingSchedule()
                        .getMaxPatient().toString()
        );
    }

    private String getAppointmentType(){
        int checkId = mFragmentPatientMakeAppointmentBinding
                .makeAppointmentLayout
                .appointmentTypeRadioGroup
                .getCheckedRadioButtonId();
        if(checkId==R.id.appointmentNewRadio) return "new";
        else if(checkId==R.id.appointmentOldRadio) return "old";
        else return "report";
    }

    private String getAppointmentDate(){
        if(DateAndTime.isDaysOfWeekIsToday(
                getDayOfWeek(
                        getCurrentSelectedVisitingSchedule()
                                .getVisitingScheduleDaysOfWeek()
                                .getDay()
                )
        )){
            if(!DateAndTime.isTimeOneGreaterThanTimeTwo(
                    DateAndTime.getLocalTime(),
                    getCurrentSelectedVisitingSchedule()
                            .getStartAt()
            )){
                return getDateOfNextDayOfWeek(getDayOfWeek(
                        getCurrentSelectedVisitingSchedule()
                                .getVisitingScheduleDaysOfWeek().getDay()
                ));
            }
            else {
                return getLocalDate();
            }
        }
        else {
            return getDateOfNextDayOfWeek(getDayOfWeek(
                    getCurrentSelectedVisitingSchedule()
                            .getVisitingScheduleDaysOfWeek()
                            .getDay()
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
        return getCurrentSelectedVisitingSchedule()
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
        mPatientHomeViewModel = new ViewModelProvider(getActivity()).get(PatientHomeViewModel.class);
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

    private void makeAppointmentButtonOnClick(){
        showProgressDialog("Making Appointment...");
        makeAppointment(getContext(),getAppointmentData());
        makeAppointmentResponseObserver();
    }

    private Appointment getAppointmentData(){
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(
                getCurrentSelectedVisitingSchedule()
                .getAppointmentDate()
        );
        Patient appointmentPatient = new Patient();
        appointmentPatient.setPatientUser(User.loginUser);
        appointment.setAppointmentPatient(appointmentPatient);
        appointment.setPatientSymptomNote(getPatientSymptomData());
        appointment.setAppointmentPaymentCredential(getPaymentCredentialData());
        appointment.setAppointmentVisitingSchedule(
                getCurrentSelectedVisitingSchedule()
        );
        appointment.setAppointmentType(getAppointmentType());
        Log.d(getString(R.string.DEBUGING_TAG), "login user: "+User.loginUser.getUserId());

        return appointment;

    }

    private void makeAppointment(Context context, Appointment appointment){
        mPatientHomeViewModel.makeAppointment(context,appointment);
    }

    private void makeAppointmentResponseObserver(){
        mPatientHomeViewModel.getNewMadeAppointment().observe(getActivity(), new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                Log.d(getString(R.string.DEBUGING_TAG),"onChange make: "+appointment);
                String dialogMessage="";
                if(!isMakeAppointmentFailed(appointment)){

                    if(isMakeAppointmentSlotFull(appointment)){
                        dialogMessage = "No slot available to make appointment";
                        setMakeAppointmentButtonEnableStatus(false);
                    }
                    else {
                        dialogMessage= getMakeAppointmentSuccessResponseMessage(appointment);
                        mPatientHomeViewModel.getBookedPatientNumber().setValue(
                                mPatientHomeViewModel.getBookedPatientNumber().getValue()+1
                        );
                        mFragmentPatientMakeAppointmentBinding.makeAppointmentLayout.bookedPatientNumberTextView
                                .setText(
                                        mPatientHomeViewModel.getBookedPatientNumber().getValue().toString()
                                );
                        if(isBookedPatientIsEqualOrGreaterThanMaxPatient()){
                            setMakeAppointmentButtonEnableStatus(false);
                        }
                    }
                }

                else {
                    dialogMessage = "Failed to Make Appointment";
                }
                dismissProgressDialog();
                showMakeAppointmentResponseInfoDialog(dialogMessage);
            }
        });
    }

    private boolean isMakeAppointmentFailed(Appointment appointment){
        boolean isFailed=false;
        try {
            isFailed=(appointment.getAdditionalProperties().get(getString(R.string.DATA_FETCH_FAILDED_STATUS_KEY))
            .equals(getString(R.string.DATA_FETCH_FAILED_STATUS_VALUE))
            );
        }
        catch (Exception e){

        }
        return isFailed;
    }
    private boolean isMakeAppointmentSlotFull(Appointment appointment){
        boolean isFull=false;
        try {
            isFull=(appointment.getAdditionalProperties().get(getString(R.string.DATA_FETCH_FAILDED_STATUS_KEY))
                    .equals(getString(R.string.DATA_FETCH_FULL_STATUS_VALUE))
            );
        }
        catch (Exception e){

        }
        return isFull;
    }
    private String getMakeAppointmentSuccessResponseMessage(Appointment appointment){
        return "Successfully made appointment\nSerial #"+
                appointment.getAppointmentSerialNumber()+
                " Time "+DateAndTime.convert24to12(appointment.getAppointmentTime());
    }

    private void showMakeAppointmentResponseInfoDialog(String message){
        Log.d(getString(R.string.DEBUGING_TAG),"show dialog");
        AlertDialog dialog = new AlertDialog.Builder(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle(message)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .create();
        dialog.show();

        //set positive button in center
        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);


    }


    private String getPaymentCredentialData(){
        return getPaymentCredentialTextFromTextView();
    }
    private String getPaymentCredentialTextFromTextView(){
        return mFragmentPatientMakeAppointmentBinding
                .makeAppointmentLayout
                .paymentEditTextInput
                .getText()
                .toString();
    }
    private boolean isPaymentCredentialInputIsValid(){
        return !getPaymentCredentialTextFromTextView().isEmpty();
    }

    private String getPatientSymptomData(){
        return getPatientSymptomTextFromTextView();
    }
    private String getPatientSymptomTextFromTextView(){
        return mFragmentPatientMakeAppointmentBinding
                .makeAppointmentLayout
                .symptomNoteDetailEditTextInput
                .getText()
                .toString();
    }

    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }



}