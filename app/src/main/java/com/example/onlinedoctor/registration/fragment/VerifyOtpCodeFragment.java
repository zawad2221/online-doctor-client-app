package com.example.onlinedoctor.registration.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentVerifyOtpCodeBinding;
import com.example.onlinedoctor.login.LoginActivity;
import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Doctor;
import com.example.onlinedoctor.model.Location;
import com.example.onlinedoctor.model.Pathology;
import com.example.onlinedoctor.model.Patient;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class VerifyOtpCodeFragment extends Fragment {
    FragmentVerifyOtpCodeBinding mFragmentVerifyOtpCodeBinding;
    ProgressDialog progressDialog;

    private String DEBUGING_TAG = "DEBUGING_TAG";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private RegisterViewModel mRegisterViewModel;

    public VerifyOtpCodeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentVerifyOtpCodeBinding = FragmentVerifyOtpCodeBinding.inflate(inflater, container, false);
        View view = mFragmentVerifyOtpCodeBinding.getRoot();
        initViewModel();
        setPhoneNumberOnView();

//        mAuth = FirebaseAuth.getInstance();
//        initOnVerificationStateChangeCallBack();
//        sendOtpCode();
        userRegistration();
        registrationResultObserver();

        return view;
    }

    private void initOnVerificationStateChangeCallBack(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(DEBUGING_TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(DEBUGING_TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    mFragmentVerifyOtpCodeBinding.editTextOtpCode.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Toast.makeText(getActivity().getApplicationContext(), "Quota exceeded.",
                            Toast.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(DEBUGING_TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
    }

    private void setPhoneNumberOnView(){
        String phoneNumber = "null";
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.PATIENT){
            phoneNumber=(mRegisterViewModel.getPatientPhoneNumber());
        }
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.DOCTOR){
            phoneNumber=(mRegisterViewModel.getDoctorPhoneNumber());
        }
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.CHAMBER){
            phoneNumber=(mRegisterViewModel.getChamberPhoneNumber());
        }
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.PATHOLOGY){
            phoneNumber=(mRegisterViewModel.getPathologyPhoneNumber());
        }
        mFragmentVerifyOtpCodeBinding.showPhoneNumberTextView.setText(mFragmentVerifyOtpCodeBinding
                .showPhoneNumberTextView.getText().toString()+" "+phoneNumber);
    }

    private void sendOtpCode(){
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.PATIENT){
            startPhoneNumberVerification("+88"+mRegisterViewModel.getPatientPhoneNumber());
        }
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.DOCTOR){
            startPhoneNumberVerification("+88"+mRegisterViewModel.getDoctorPhoneNumber());
        }
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.CHAMBER){
            startPhoneNumberVerification("+88"+mRegisterViewModel.getChamberPhoneNumber());
        }
        if(mRegisterViewModel.getSelectedUser()== RegisterViewModel.AllUserType.PATHOLOGY){
            startPhoneNumberVerification("+88"+mRegisterViewModel.getPathologyPhoneNumber());
        }
    }
    private void initViewModel(){
        if(mRegisterViewModel==null)
            mRegisterViewModel = new ViewModelProvider(this.getActivity(),
                    new RegisterViewModelFactory(this.getActivity().getApplication(),"test"))
                    .get(RegisterViewModel.class);
    }

    private String getOtpCode(){
        return mFragmentVerifyOtpCodeBinding.editTextOtpCode.getText().toString();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentVerifyOtpCodeBinding.cirVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getOtpCode().isEmpty()){
                    showDialog();
                    verifyPhoneNumberWithCode(mVerificationId,getOtpCode());
                }
            }
        });

    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(DEBUGING_TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(DEBUGING_TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                mFragmentVerifyOtpCodeBinding.editTextOtpCode.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this.getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    public void showDialog(){
        progressDialog = new ProgressDialog(this.getContext(),
                R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Verifying...");
        progressDialog.show();


    }

    private void updateUI(int uiState) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button

                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the

                //Toast.makeText(this.getActivity(),"Code send",Toast.LENGTH_LONG).show();
                showOtpCodeStatus("Code send");
                Log.d(DEBUGING_TAG,"Code send");
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                showOtpCodeStatus("Invalid phone number");
                dismissProgressDialog();
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in


                // Set the verification text based on the credential


                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                showOtpCodeStatus("Code doesn't match");
                dismissProgressDialog();
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                showOtpCodeStatus("Verification Success");
                dismissProgressDialog();
                userRegistration();
                registrationResultObserver();
                break;
        }
    }
    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    private void showOtpCodeStatus(String status){
        mFragmentVerifyOtpCodeBinding.codeStatus.setText(status);
    }

    private Patient getPatient(String bloodGroup,
                               String dateOfBirth,
                               String gender,
                               String nid,
                               User user){
        Patient patient= new Patient();
        patient.setPatientBloodGroup(bloodGroup);
        patient.setPatientDateOfBirth(dateOfBirth);
        patient.setPatientGender(gender);
        patient.setPatientNid(nid);
        patient.setPatientUser(user);
        return patient;

    }
    private User getUser(String name,
                         String phoneNumber,
                         String password,
                         String role){
        User user = new User();
        user.setUserName(name);
        user.setUserPhoneNumber(phoneNumber);
        user.setUserRole(role);
        user.setPassword(password);
        return user;
    }

    private void userRegistration(){
        if(mRegisterViewModel.getSelectedUser()==(RegisterViewModel.AllUserType.PATIENT)){
            User user = getUser(mRegisterViewModel.getPatientName(),
                    mRegisterViewModel.getPatientPhoneNumber(),
                    mRegisterViewModel.getPatientPassword(),
                    "patient");
            Patient patient= getPatient(mRegisterViewModel.getPatientBloodGroup(),
                    mRegisterViewModel.getDateOfBirthYear()+"-"
                            +mRegisterViewModel.getDateOfBirthMonth()+"-"
                            +mRegisterViewModel.getDateOfBirthDay(),
                    mRegisterViewModel.getPatientGender(),
                    mRegisterViewModel.getPatientNidNumber(),
                    user);

            mRegisterViewModel.registerUser(patient);

        }
        else if(mRegisterViewModel.getSelectedUser()==(RegisterViewModel.AllUserType.DOCTOR)){
            Doctor doctor = new Doctor();
            doctor.setDoctorBmdcId(mRegisterViewModel.getDoctorBmdcId());
            doctor.setDoctorDesignation(mRegisterViewModel.getDoctorDesignation());
            doctor.setDoctorSpecialization(mRegisterViewModel.getSpecialization());
            User user = getUser(mRegisterViewModel.getDoctorName(),
                    mRegisterViewModel.getDoctorPhoneNumber(),
                    mRegisterViewModel.getDoctorPassword(),
                    "doctor");
            doctor.setDoctorUser(user);
            mRegisterViewModel.registerUser(doctor);
        }
        else if(mRegisterViewModel.getSelectedUser()==(RegisterViewModel.AllUserType.CHAMBER)){
            Chamber chamber = new Chamber();
            Location location = new Location();
            location.setLocationAdderssDetail(mRegisterViewModel.getChamberAddress());
            location.setLocationLatitude(String.valueOf(mRegisterViewModel.getChamberLocationLatLan().latitude));
            location.setLocationLongitude(String.valueOf(mRegisterViewModel.getChamberLocationLatLan().longitude));
            chamber.setChamberLocation(location);
            User user = getUser(mRegisterViewModel.getChamberName(),
                    mRegisterViewModel.getChamberPhoneNumber(),
                    mRegisterViewModel.getChamberPassword(),
                    "chamber");

            chamber.setChamberUser(user);
            mRegisterViewModel.registerUser(chamber);
        }
        else if(mRegisterViewModel.getSelectedUser()==(RegisterViewModel.AllUserType.PATHOLOGY)){
            Pathology pathology = new Pathology();
            Location location = new Location();
            location.setLocationAdderssDetail(mRegisterViewModel.getPathologyAddress());
            location.setLocationLatitude(String.valueOf(mRegisterViewModel.getPathologyLocationLatLan().latitude));
            location.setLocationLongitude(String.valueOf(mRegisterViewModel.getPathologyLocationLatLan().longitude));
            pathology.setPathologyLocation(location);
            User user = getUser(mRegisterViewModel.getPathologyName(),
                    mRegisterViewModel.getPathologyPhoneNumber(),
                    mRegisterViewModel.getPathologyPassword(),
                    "pathology");

            pathology.setPathologyUser(user);
            mRegisterViewModel.registerUser(pathology);
        }
    }

    private void registrationResultObserver(){
        mRegisterViewModel.getRegistrationResponse().observe(this.getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                String message = "";
                if(aBoolean==true) message = "Registration Completed";
                else message = "Registration Failed";
                new AlertDialog.Builder(getActivity())
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(aBoolean){
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                }
                                else {
                                    getActivity().onBackPressed();
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });
    }





}