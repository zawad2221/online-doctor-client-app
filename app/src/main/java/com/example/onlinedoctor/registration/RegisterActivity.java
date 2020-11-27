package com.example.onlinedoctor.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.registration.fragment.ChamberRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.DoctorRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.PathologyRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.PatientRegisterFormFragment;
import com.example.onlinedoctor.registration.fragment.SelectUserFragment;
import com.example.onlinedoctor.registration.fragment.VerifyOtpCodeFragment;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private String DEBUGING_TAG="DEBUGING_TAG";
    private String patientRegisterFormTag="PATIENT_REGISTER_FORM",
            doctorRegisterFormTag="DOCTOR_REGISTER_FORM",
            chamberRegisterFormTag="CHAMBER_REGISTER_FORM",
            pathologyRegisterFormTag="PATHOLOGY_REGISTER_FORM",
            selectUserTag="SELECT_USER_FORM",
            verifyOptTag="VERIFY_OTP_FORM";
    private TextView mSignIn,mPreviousPage,mNextPage;

    private RegisterViewModel mRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSignIn = findViewById(R.id.signInOptionTextView);
        mPreviousPage = findViewById(R.id.previousPageTextView);
        mNextPage = findViewById(R.id.nextPageTextView);


        setPreviousButtonGone();

        mRegisterViewModel = new ViewModelProvider(this,
                new RegisterViewModelFactory(this.getApplication(),"test"))
                .get(RegisterViewModel.class);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new SelectUserFragment(),selectUserTag)
                .commit();
        mRegisterViewModel.setCurrentSelectedFragment(selectUserTag);

        nextPageButtonOnClick();
        previousPageOnButtonOnClick();


    }

    private void setPreviousButtonGone(){
        mPreviousPage.setVisibility(View.GONE);
    }

    private void previousPageOnButtonOnClick(){
        mPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRegisterViewModel.getCurrentSelectedFragment().equals(verifyOptTag)){
                    showRegisterForm(mRegisterViewModel.getSelectedUser());

                }
                else if(mRegisterViewModel.getCurrentSelectedFragment().equals(patientRegisterFormTag)){
                    showSelectUserForm();
                }
                else if(mRegisterViewModel.getCurrentSelectedFragment().equals(doctorRegisterFormTag)){
                    showSelectUserForm();
                }
                else if(mRegisterViewModel.getCurrentSelectedFragment().equals(chamberRegisterFormTag)){
                    showSelectUserForm();
                }
                else if(mRegisterViewModel.getCurrentSelectedFragment().equals(chamberRegisterFormTag)){
                    showSelectUserForm();
                }
            }
        });
    }

    private void showSelectUserForm(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.formContainer,new SelectUserFragment(),selectUserTag)
                .commit();
        mRegisterViewModel.setCurrentSelectedFragment(selectUserTag);
        mNextPage.setVisibility(View.VISIBLE);
        mRegisterViewModel.setSelectedUser("");
        setPreviousButtonGone();
    }

    private void showRegisterForm(String selectedUser){
        if(selectedUser.equals("patient")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.formContainer,new PatientRegisterFormFragment(),patientRegisterFormTag)
                    .commit();
            mRegisterViewModel.setCurrentSelectedFragment(patientRegisterFormTag);
        }
        else if(selectedUser.equals("doctor")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.formContainer,new DoctorRegisterFormFragment(),doctorRegisterFormTag)
                    .commit();
            mRegisterViewModel.setCurrentSelectedFragment(doctorRegisterFormTag);
        }
        else if(selectedUser.equals("chamber")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.formContainer,new ChamberRegisterFormFragment(),chamberRegisterFormTag)
                    .commit();
            mRegisterViewModel.setCurrentSelectedFragment(chamberRegisterFormTag);
        }
        else  if(selectedUser.equals("pathology")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.formContainer,new PathologyRegisterFormFragment(),pathologyRegisterFormTag)
                    .commit();
            mRegisterViewModel.setCurrentSelectedFragment(pathologyRegisterFormTag);
        }
    }

    private void nextPageButtonOnClick(){
        mNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUser=mRegisterViewModel.getSelectedUser();
                Log.d(DEBUGING_TAG,"selected user: "+selectedUser);
                if(!selectedUser.equals("")){
                    showRegisterForm(selectedUser);
                    mNextPage.setVisibility(View.GONE);
                    mPreviousPage.setVisibility(View.VISIBLE);
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content),"select user",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }
}