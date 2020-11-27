package com.example.onlinedoctor.registration.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.service.autofill.RegexValidator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


import com.example.onlinedoctor.R;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;
import com.google.android.material.snackbar.Snackbar;


public class PatientRegisterFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String verifyOptTag="VERIFY_OTP_FORM";

    private RegisterViewModel mRegisterViewModel;

    private Spinner bloodGroupSpinner;
    private Button datePickerButton;
    private String[] bloodGroup={"A+","A-","B+","B-","O+","O-","AB+","AB-"};

    private DialogFragment newFragment;

    private EditText mNameEditText, mPhoneEditText, mNidNumberEditText, mPasswordEditText;
    private RadioButton mGenderMaleRadioButton, mGenderFemaleRadioButton, mGenderOthersRadioButton;
    private Button mRegisterButton;

    private String name, phoneNumber, nidNumber, password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_patient_register_form, container, false);

        bloodGroupSpinner = view.findViewById(R.id.patientBloodGroupSpinner);
        datePickerButton = view.findViewById(R.id.patientBirthDatePickerButton);

        mNameEditText = view.findViewById(R.id.inputTextPatientName);
        mPhoneEditText = view.findViewById(R.id.inputTextPatientPhoneNumber);
        mNidNumberEditText = view.findViewById(R.id.inputTextPatientNid);
        mPasswordEditText = view.findViewById(R.id.inputTextPatientPassword);

        mGenderMaleRadioButton = view.findViewById(R.id.radioPatientGenderMale);
        mGenderFemaleRadioButton = view.findViewById(R.id.radioPatientGenderFemale);
        mGenderOthersRadioButton = view.findViewById(R.id.radioPatientGenderOthers);

        mRegisterButton = view.findViewById(R.id.patientRegisterButton);

        genderFemaleRadioButtonOnClick();
        genderMaleRadioButtonOnClick();
        genderOthersRadioButtonOnClick();

        registerButtonOnClick();

        initBloodGroupSpinner();
        datePickerAction();




        return view;
    }

    private void registerButtonOnClick(){
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromView();
                if(isValidateInput()){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.formContainer,new VerifyOtpCodeFragment(),verifyOptTag)
                            .commit();
                    mRegisterViewModel.setCurrentSelectedFragment(verifyOptTag);
                }
            }
        });
    }

    private void genderMaleRadioButtonOnClick(){
        mGenderMaleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGender("male");
            }
        });
    }
    private void genderFemaleRadioButtonOnClick(){
        mGenderFemaleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGender("female");
            }
        });
    }
    private void genderOthersRadioButtonOnClick(){
        mGenderOthersRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGender("others");
            }
        });
    }

    private void setGender(String gender){
        mRegisterViewModel.setGender(gender);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRegisterViewModel = new ViewModelProvider(this.getActivity(),
                new RegisterViewModelFactory(this.getActivity().getApplication(),"test"))
                .get(RegisterViewModel.class);
    }

    private void getDataFromView(){
        name = mNameEditText.getText().toString();
        phoneNumber = mPhoneEditText.getText().toString();
        nidNumber = mNidNumberEditText.getText().toString();
        password = mPasswordEditText.getText().toString();
    }

    private boolean isValidateInput(){
        boolean isNameValid = !(name.isEmpty());
        boolean isPhoneNumberValid = !(phoneNumber.isEmpty() || !(phoneNumber.length() ==11) || !phoneNumber.startsWith("01"));
        boolean isNidNumberValid = !(nidNumber.isEmpty());
        boolean isPasswordValid = !(password.isEmpty());

        if(!isNameValid){
            mNameEditText.setError("invalid input");
            return false;
        }
        if(!isPhoneNumberValid){
            mPhoneEditText.setError("invalid phone number");
            return false;
        }
        if(!isNidNumberValid){
            mNidNumberEditText.setError("invalid input");
            return false;
        }
        if (!isPasswordValid){
            mPasswordEditText.setError("invalid input");
            return false;
        }
        if(mRegisterViewModel.getDateOfBirthYear()==0){
            Snackbar.make(this.getView(),"select birth date",Snackbar.LENGTH_LONG).show();
            return false;
        }
        if(mRegisterViewModel.getGender().isEmpty()){
            Snackbar.make(this.getView(),"select gender",Snackbar.LENGTH_LONG).show();
            return false;
        }


        return true;

    }

    private void datePickerAction(){
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment = new DatePickerFragment();

                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");



            }
        });

    }

    private void initBloodGroupSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.blood_group_spinner_item,bloodGroup);
        adapter.setDropDownViewResource(R.layout.blood_group_spinner_dropdown_item);
        bloodGroupSpinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private RegisterViewModel mRegisterViewModel;
        private int year, month, day;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            this.day = day;
            this.month = month;
            this.year = year;

            mRegisterViewModel.setDateOfBirthDay(day);
            mRegisterViewModel.setDateOfBirthMonth(month);
            mRegisterViewModel.setDateOfBirthYear(year);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mRegisterViewModel = new ViewModelProvider(this.getActivity(),
                    new RegisterViewModelFactory(this.getActivity().getApplication(),"test"))
                    .get(RegisterViewModel.class);

        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }
}