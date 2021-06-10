package com.example.onlinedoctor.doctor.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onlinedoctor.DateAndTime;
import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentCreateVisitingScheduleBinding;
import com.example.onlinedoctor.doctor.adapter.SearchChamberRecyclerAdapter;
import com.example.onlinedoctor.doctor.view_model.DoctorMainViewModel;
import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.DaysOfWeek;
import com.example.onlinedoctor.model.Doctor;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.model.VisitingSchedule;
import com.example.onlinedoctor.model.VisitingScheduleFee;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;


public class CreateVisitingScheduleFragment extends Fragment {
    DoctorMainViewModel mDoctorMainViewModel;
    FragmentCreateVisitingScheduleBinding mFragmentCreateVisitingScheduleBinding;
    SearchChamberRecyclerAdapter searchChamberRecyclerAdapter;
    ArrayAdapter<String> daysOfWeekAdapter;
    MaterialTimePicker startAt, endAt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentCreateVisitingScheduleBinding = FragmentCreateVisitingScheduleBinding
                .inflate(inflater, container, false);
        return mFragmentCreateVisitingScheduleBinding.getRoot();
    }
    private void onBack(){
        getActivity().onBackPressed();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentCreateVisitingScheduleBinding.closeFragment.setOnClickListener(v->{
            onBack();
        });
        initViewModel();
        searchChamberButtonOnClick();
        initDaysOfWeekDropDown();
        daysOfWeekOnClick();
        startAtButtonOnClick();
        endAtButtonOnClick();
        daysOfWeekItemClickListener();
        createScheduleButtonClick();


    }

    // schedule time
    private void startAtButtonOnClick(){
        mFragmentCreateVisitingScheduleBinding.startTimeButton.setOnClickListener(v->{
            showStartAtTimePicker();
            startAtTimePickerListener();
        });
    }
    private void endAtButtonOnClick(){
        mFragmentCreateVisitingScheduleBinding.endTimeButton.setOnClickListener(v->{
            showEndAtTimePicker();
            endAtTimePickerListener();
        });
    }

    private void startAtTimePickerListener(){
        startAt.addOnPositiveButtonClickListener(v -> {
            String time24 = makeTimeOneLengthToTow(String.valueOf(startAt.getHour()))
                    +":"+makeTimeOneLengthToTow(String.valueOf(startAt.getMinute()))
                    +":00";
            String time = DateAndTime.convert24to12(time24);
           mFragmentCreateVisitingScheduleBinding.startTimeButton.setText(time);
           mDoctorMainViewModel.newSchedule.setStartAt(time24);
        });
    }
    private String makeTimeOneLengthToTow(String number){
        return checkIfLengthOne(number)?"0"+number: number;
    }
    private boolean checkIfLengthOne(String number){
        return number.length() == 1;
    }
    private void endAtTimePickerListener(){
        endAt.addOnPositiveButtonClickListener(v -> {
            String time24 = makeTimeOneLengthToTow(String.valueOf(endAt.getHour()))
                    +":"+makeTimeOneLengthToTow(String.valueOf(endAt.getMinute()))
                    +":00";
            String time = DateAndTime.convert24to12(time24);
            mFragmentCreateVisitingScheduleBinding.endTimeButton.setText(time);
            mDoctorMainViewModel.newSchedule.setEndAt(time24);
        });
    }

    private void showStartAtTimePicker(){
        startAt = getTimePicker("Select Start Time");
        startAt.show(getChildFragmentManager(),"start_at");
    }
    private void showEndAtTimePicker(){
        endAt = getTimePicker("Select Start Time");
        endAt.show(getChildFragmentManager(),"end_at");
    }

    private MaterialTimePicker getTimePicker(String message){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText(message)
                .build();
        return materialTimePicker;
    }

    //schedule day of week
    private void initDaysOfWeekDropDown(){
        daysOfWeekAdapter = new ArrayAdapter<String>(this.getContext(),  R.layout.days_of_week_drop_down_item, mDoctorMainViewModel.daysOfWeek);
        mFragmentCreateVisitingScheduleBinding.scheduleDaysOfWeek.setAdapter(daysOfWeekAdapter);
    }
    private void daysOfWeekOnClick(){
        mFragmentCreateVisitingScheduleBinding.scheduleDaysOfWeek.setOnClickListener(v -> {
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.scheduleDaysOfWeekLayout,null);
            daysOfWeekAdapter.getFilter().filter(null);
        });
    }
    private void daysOfWeekItemClickListener(){
        mFragmentCreateVisitingScheduleBinding.scheduleDaysOfWeek.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            DaysOfWeek daysOfWeek = new DaysOfWeek();
            daysOfWeek.setDay(mDoctorMainViewModel.daysOfWeek[position]);
            mDoctorMainViewModel.newSchedule.setVisitingScheduleDaysOfWeek(daysOfWeek);
        });
    }
    private String getDaysOfWeek(){
        return mFragmentCreateVisitingScheduleBinding.scheduleDaysOfWeek.getText().toString();
    }
    private boolean isDaysOfWeekValid(){
        return !getDaysOfWeek().isEmpty();
    }
    private void showErrorInTextInputLayout(TextInputLayout textInputLayout, String message){
        textInputLayout.setError(message);
    }

    // schedule chamber
    private void searchChamberButtonOnClick(){
        mFragmentCreateVisitingScheduleBinding.searchChamberButton.setOnClickListener(v -> {
            if(isValidChamberSearchInput()){
                searchChamber(getContext(),getChamberQuery());
                searchChamberObserver();
            }
            else {
                setErrorMessageOnChamberQueryInput("invalid input");
            }
        });
    }
    private String getChamberQuery(){
        return mFragmentCreateVisitingScheduleBinding.chamberNameOrNumber.getText().toString();
    }
    private void setErrorMessageOnChamberQueryInput(String message){
        mFragmentCreateVisitingScheduleBinding.chamberNameOrNumber.setError(message);
    }
    private boolean isValidChamberSearchInput(){
        return getChamberQuery().isEmpty()? false:true;
    }

    private void initViewModel(){
        mDoctorMainViewModel = new ViewModelProvider(getActivity())
                .get(DoctorMainViewModel.class);
    }
    private void searchChamber(Context context, String query){
        mDoctorMainViewModel.searchChamber(context, query);
    }
    private void searchChamberObserver(){
        mDoctorMainViewModel.getSearchChamberList().observe(getActivity(), new Observer<List<Chamber>>() {
            @Override
            public void onChanged(List<Chamber> chambers) {
                initRecyclerView();
                setVisibilityOfChamberRecyclerView(View.VISIBLE);
            }
        });
    }
    private void selectedChamber(int position){
        mDoctorMainViewModel.selectedChamberItemPosition=position;
        mDoctorMainViewModel.newSchedule.setVisitingScheduleChamber(mDoctorMainViewModel.getSearchChamberList().getValue().get(position));
        setVisibilityOfChamberRecyclerView(View.GONE);
        setChamberInView();
    }
    private void setChamberInView(){
        mFragmentCreateVisitingScheduleBinding.chamberNameOrNumber.setText(
                mDoctorMainViewModel.getSearchChamberList().getValue().get(

                        mDoctorMainViewModel.selectedChamberItemPosition
                )
                .getChamberUser()
                .getUserName()
        );

    }
    private void initAdapter(){
        searchChamberRecyclerAdapter = new SearchChamberRecyclerAdapter(mDoctorMainViewModel.getSearchChamberList().getValue(),
                new SearchChamberRecyclerAdapter.SearchChamberRecyclerItemClickListener() {
                    @Override
                    public void itemOnClick(int position) {
                        selectedChamber(position);
                        showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.chamberNameLayout,null);
                    }
                });
    }
    private void initRecyclerView(){
        initAdapter();
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setHasFixedSize(true);
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setAdapter(searchChamberRecyclerAdapter);
    }

    private void setVisibilityOfChamberRecyclerView(int visibility){
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setVisibility(visibility);
    }

    //max patient
    private String getMaxPatient(){
        return mFragmentCreateVisitingScheduleBinding.maxPatient.getText().toString();
    }
    private boolean isMaxPatientValid(){
        return !getMaxPatient().isEmpty();
    }

    //schedule fee
    private String getOldFee(){
        return mFragmentCreateVisitingScheduleBinding.oldFee.getText().toString();
    }
    private boolean isOldFeeValid(){
        return !getOldFee().isEmpty();
    }

    private String getReportShowingFee(){
        return mFragmentCreateVisitingScheduleBinding.reportFee.getText().toString();
    }
    private boolean isReportShowingFeeValid(){
        return !getReportShowingFee().isEmpty();
    }

    private String getNewFee(){
        return mFragmentCreateVisitingScheduleBinding.newFee.getText().toString();
    }
    private boolean isNewFeeValid(){
        return !getNewFee().isEmpty();
    }

    //schedule additional info
    private String getAdditionalInfo(){
        return mFragmentCreateVisitingScheduleBinding.additionalInfo.getText().toString();
    }
    private boolean isAdditionalInfoValid(){
        return !getAdditionalInfo().isEmpty();
    }

    private void showToast(String message){
        Toast.makeText(this.getContext(),message,Toast.LENGTH_LONG).show();
    }

    private boolean checkInputAndShowError(){
        if(mDoctorMainViewModel.newSchedule.getVisitingScheduleChamber()==null){
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.chamberNameLayout,"select a chamber");
            return false;
        }
        else if(mDoctorMainViewModel.newSchedule.getVisitingScheduleDaysOfWeek()==null){
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.scheduleDaysOfWeekLayout,"select a day");
            return false;
        }
        else if(mDoctorMainViewModel.newSchedule.getStartAt()==null){
            showToast("select starting time");
            return false;
        }
        else if(mDoctorMainViewModel.newSchedule.getEndAt()==null){
            showToast("select ending time");
            return false;
        }
        else if(!isMaxPatientValid()){
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.maxPatientLayout,"enter number of patient");
            return false;
        }
        else if(!isNewFeeValid()){
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.newFeeLayout,"enter new patient fee");
            return false;
        }
        else if(!isOldFeeValid()){
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.oldFeeLayout,"enter old patient fee");
            return false;
        }
        else if(!isReportShowingFeeValid()){
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.reportFeeLayout,"enter report showing fee");
            return false;
        }
        else if(!isAdditionalInfoValid()){
            showErrorInTextInputLayout(mFragmentCreateVisitingScheduleBinding.additionalInfoLayout,"invalid input");
            return false;
        }
        else return true;

    }
    private void createScheduleButtonClick(){
        mFragmentCreateVisitingScheduleBinding.createScheduleButton.setOnClickListener((View v) -> {
            if(checkInputAndShowError()){
                mDoctorMainViewModel.newSchedule.setMaxPatient(Integer.parseInt(getMaxPatient()));
                VisitingScheduleFee visitingScheduleFee = new VisitingScheduleFee();
                visitingScheduleFee.setNewFee(Integer.parseInt(getNewFee()));
                visitingScheduleFee.setOldFee(Integer.parseInt(getOldFee()));
                visitingScheduleFee.setReportFee(Integer.parseInt(getReportShowingFee()));
                mDoctorMainViewModel.newSchedule.setVisitingScheduleFee(visitingScheduleFee);
                mDoctorMainViewModel.newSchedule.setVisitingScheduleAdditionalInformation(getAdditionalInfo());
                Doctor doctor = new Doctor();
                doctor.setDoctorUser(User.loginUser);
                mDoctorMainViewModel.newSchedule.setVisitingScheduleDoctor(doctor);
                createSchedule();

            }
        });
    }
    private void createSchedule(){
        mDoctorMainViewModel.createVisitingSchedule(getContext(),mDoctorMainViewModel.newSchedule);
        createScheduleResponse();
    }
    private void createScheduleResponse(){
        mDoctorMainViewModel.getVisitingScheduleResponse().observe(getActivity(), new Observer<VisitingSchedule>() {
            @Override
            public void onChanged(VisitingSchedule visitingSchedule) {
                showDialog("New Schedule Created");
            }
        });
    }
    private void showDialog(String message){
        new AlertDialog.Builder(getContext())
                .setTitle(message)
                .setNeutralButton("OK", (dialog, which) -> {
                    getActivity().onBackPressed();
                })
                .setCancelable(false)
                .show();
    }

}