package com.example.onlinedoctor.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.ActivityRegisterBinding;
import com.example.onlinedoctor.login.LoginActivity;
import com.example.onlinedoctor.registration.fragment.ChamberRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.DoctorRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.PathologyRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.PatientRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.SelectLocationFormFragment;
import com.example.onlinedoctor.registration.fragment.SelectUserFragment;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding mActivityRegisterBinding;

    private String DEBUGING_TAG="DEBUGING_TAG";

//    private String patientRegisterFormTag="PATIENT_REGISTER_FORM",
//            doctorRegisterFormTag="DOCTOR_REGISTER_FORM",
//            chamberRegisterFormTag="CHAMBER_REGISTER_FORM",
//            pathologyRegisterFormTag="PATHOLOGY_REGISTER_FORM",
//            selectUserTag="SELECT_USER_FORM",
//            verifyOptTag="VERIFY_OTP_FORM",
//            chamberSelectLocationFormTag = "CHAMBER_SELECT_LOCATION_FORM",
//            pathologySelectLocationFormTag = "PATHOLOGY_SELECT_LOCATION_FORM";

    private TextView mSignIn,mPreviousPage,mNextPage;

    private RegisterViewModel mRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = mActivityRegisterBinding.getRoot();
        setContentView(view);

        mSignIn = findViewById(R.id.signInOptionTextView);
        mPreviousPage = findViewById(R.id.previousPageTextView);
        mNextPage = findViewById(R.id.nextPageTextView);




        mRegisterViewModel = new ViewModelProvider(this,
                new RegisterViewModelFactory(this.getApplication(),"test"))
                .get(RegisterViewModel.class);


        if(savedInstanceState==null) {
            setPreviousPageButtonVisibility(View.GONE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.formContainer,new SelectUserFragment())
                    .commit();
            mRegisterViewModel.setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM);



        }
        if(mRegisterViewModel.getCurrentSelectedFragment()!=RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM){
            setNextPageButtonVisibility(View.GONE);
        }


        nextPageButtonOnClick();
        previousPageOnButtonOnClick();

        signInOptionOnClick();


    }
    private void signInOptionOnClick(){
        mActivityRegisterBinding.signInOptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void setPreviousPageButtonVisibility(int visibility){
        mPreviousPage.setVisibility(visibility);
    }

    private void showPreviousPage(RegisterViewModel.AllUserType selectedUser,
                                  RegisterViewModel.AllRegisterFragment currentForm){
        if(currentForm==RegisterViewModel.AllRegisterFragment.VERIFY_OTP_FORM){
            if(selectedUser.equals(RegisterViewModel.AllUserType.PATIENT)){
                showPatientRegistrationFrom();
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.PATIENT_REGISTER_FORM);
            }
            else if(selectedUser.equals(RegisterViewModel.AllUserType.DOCTOR)){
                showDoctorRegistrationFrom();
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.DOCTOR_REGISTER_FORM);
            }
            else if(selectedUser.equals(RegisterViewModel.AllUserType.CHAMBER)){
                showChamberRegistrationFrom();
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.CHAMBER_REGISTER_FORM);

            }
            else  if(selectedUser.equals(RegisterViewModel.AllUserType.PATHOLOGY)){
                showPathologyRegistrationFrom();
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.PATHOLOGY_REGISTER_FORM);

            }
            setPreviousPageButtonVisibility(View.VISIBLE);
        }
        else if(currentForm==RegisterViewModel.AllRegisterFragment.PATIENT_REGISTER_FORM ||
                currentForm==RegisterViewModel.AllRegisterFragment.DOCTOR_REGISTER_FORM ||
                currentForm==RegisterViewModel.AllRegisterFragment.CHAMBER_SELECT_LOCATION_FORM||
                currentForm==RegisterViewModel.AllRegisterFragment.PATHOLOGY_SELECT_LOCATION_FORM){
            showSelectUserForm();
            setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM);
            setPreviousPageButtonVisibility(View.GONE);

        }
        else if(currentForm==RegisterViewModel.AllRegisterFragment.CHAMBER_REGISTER_FORM){
            showSelectLocationForm(RegisterViewModel.AllRegisterFragment.CHAMBER_SELECT_LOCATION_FORM);
            setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.CHAMBER_SELECT_LOCATION_FORM);
            setPreviousPageButtonVisibility(View.VISIBLE);
        }
        else if(currentForm==RegisterViewModel.AllRegisterFragment.PATHOLOGY_REGISTER_FORM){
            showSelectLocationForm(RegisterViewModel.AllRegisterFragment.PATHOLOGY_SELECT_LOCATION_FORM);
            setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.PATHOLOGY_SELECT_LOCATION_FORM);
            setPreviousPageButtonVisibility(View.VISIBLE);
        }


    }

    private void previousPageOnButtonOnClick(){
        mPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterViewModel.AllUserType selectedUser=mRegisterViewModel.getSelectedUser();
                RegisterViewModel.AllRegisterFragment currentForm = mRegisterViewModel.getCurrentSelectedFragment();
                showPreviousPage(selectedUser,currentForm);
            }
        });
    }

    private void showSelectLocationForm(RegisterViewModel.AllRegisterFragment form){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new SelectLocationFormFragment())
                .commit();

        setCurrentSelectedFragment(form);
        setNextPageButtonVisibility(View.VISIBLE);
        //mRegisterViewModel.setSelectedUser("");
        setPreviousPageButtonVisibility(View.VISIBLE);
    }

    private void showSelectUserForm(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new SelectUserFragment())
                .commit();
        setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM);
        setNextPageButtonVisibility(View.VISIBLE);
        //mRegisterViewModel.setSelectedUser("");
        setPreviousPageButtonVisibility(View.GONE);
    }
    private void setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment fragment){
        mRegisterViewModel.setCurrentSelectedFragment(fragment);
    }
    private void showPatientRegistrationFrom(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new PatientRegisterFormFragment())
                .commit();
    }
    private void showDoctorRegistrationFrom(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new DoctorRegisterFormFragment())
                .commit();
    }
    private void showChamberRegistrationFrom(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new ChamberRegisterFormFragment())
                .commit();
    }
    private void showPathologyRegistrationFrom(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new PathologyRegisterFormFragment())
                .commit();
    }

    private void showNextForm(RegisterViewModel.AllUserType selectedUser,
                              RegisterViewModel.AllRegisterFragment currentForm){
        if(selectedUser.equals(RegisterViewModel.AllUserType.PATIENT)){
            showPatientRegistrationFrom();
            setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.PATIENT_REGISTER_FORM);
        }
        else if(selectedUser.equals(RegisterViewModel.AllUserType.DOCTOR)){
            showDoctorRegistrationFrom();
            setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.DOCTOR_REGISTER_FORM);
        }
        else if(selectedUser.equals(RegisterViewModel.AllUserType.CHAMBER)){
            if(currentForm.equals(RegisterViewModel.AllRegisterFragment.CHAMBER_SELECT_LOCATION_FORM)){
                showChamberRegistrationFrom();
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.CHAMBER_REGISTER_FORM);
                setNextPageButtonVisibility(View.GONE);
            }
            else if(currentForm.equals(RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM)){
                showSelectLocationForm(RegisterViewModel.AllRegisterFragment.CHAMBER_SELECT_LOCATION_FORM);
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.CHAMBER_SELECT_LOCATION_FORM);
                setNextPageButtonVisibility(View.VISIBLE);
            }

        }
        else  if(selectedUser.equals(RegisterViewModel.AllUserType.PATHOLOGY)){
            if(currentForm.equals(RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM)){
                showSelectLocationForm(RegisterViewModel.AllRegisterFragment.PATHOLOGY_SELECT_LOCATION_FORM);
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.PATHOLOGY_SELECT_LOCATION_FORM);
                setNextPageButtonVisibility(View.VISIBLE);
            }
            else if(currentForm.equals(RegisterViewModel.AllRegisterFragment.PATHOLOGY_SELECT_LOCATION_FORM)){
                showPathologyRegistrationFrom();
                setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.PATHOLOGY_REGISTER_FORM);
                setNextPageButtonVisibility(View.GONE);
            }

        }
    }



    private void nextPageButtonOnClick(){
        mNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterViewModel.AllUserType selectedUser=mRegisterViewModel.getSelectedUser();
                RegisterViewModel.AllRegisterFragment currentForm = mRegisterViewModel.getCurrentSelectedFragment();
                Log.d(DEBUGING_TAG,"selected user: "+selectedUser);
                if(selectedUser!=null){
                    showNextForm(selectedUser, currentForm);

                    if(currentForm==RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM){
                        if(selectedUser==RegisterViewModel.AllUserType.CHAMBER || selectedUser==RegisterViewModel.AllUserType.PATHOLOGY){
                            setNextPageButtonVisibility(View.VISIBLE);
                        }
                        else {
                            setNextPageButtonVisibility(View.GONE);
                        }



                    }
                    else {
                        setNextPageButtonVisibility(View.GONE);

                    }
                    setPreviousPageButtonVisibility(View.VISIBLE);

                }
                else {
                    Snackbar.make(findViewById(android.R.id.content),"select user",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setNextPageButtonVisibility(int visibility){
        mNextPage.setVisibility(visibility);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUGING_TAG,"on start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUGING_TAG,"on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(DEBUGING_TAG,"on pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(DEBUGING_TAG,"on stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DEBUGING_TAG,"on destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUGING_TAG,"on restart");
    }

    @Override
    public void onBackPressed() {
        if(mRegisterViewModel.getCurrentSelectedFragment() ==
                RegisterViewModel.AllRegisterFragment.SELECT_USER_FORM)
            super.onBackPressed();
        else
            showPreviousPage(mRegisterViewModel.getSelectedUser(),
                    mRegisterViewModel.getCurrentSelectedFragment());
    }
}