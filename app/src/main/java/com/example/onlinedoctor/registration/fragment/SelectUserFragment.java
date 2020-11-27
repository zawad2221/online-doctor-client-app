package com.example.onlinedoctor.registration.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;


public class SelectUserFragment extends Fragment {
    private String DEBUGING_TAG="DEBUGING_TAG";

    private RadioButton mPatientRadioButton,mDoctorRadioButton,mChamberRadioButton,mPathologyRadioButton;
    RegisterViewModel mRegisterViewModel;


    public SelectUserFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_select_user, container, false);
        mChamberRadioButton = view.findViewById(R.id.radioChamber);
        mDoctorRadioButton = view.findViewById(R.id.radioDoctor);
        mPathologyRadioButton = view.findViewById(R.id.radioPathology);
        mPatientRadioButton = view.findViewById(R.id.radioPatient);
        pathologyRadioButtonOnClick();;
        patientRadioButtonOnClick();
        doctorRadioButtonOnClick();
        chamberRadioButtonOnClick();
        return view;
    }
    private void patientRadioButtonOnClick(){
        mPatientRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedUser("patient");
            }
        });
    }
    private void doctorRadioButtonOnClick(){
        mDoctorRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedUser("doctor");
            }
        });
    }
    private void chamberRadioButtonOnClick(){
        mChamberRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedUser("chamber");
            }
        });
    }
    private void pathologyRadioButtonOnClick(){
        mPathologyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedUser("pathology");
            }
        });
    }

    private void setSelectedUser(String user){
        mRegisterViewModel.setSelectedUser(user);
        Log.d(DEBUGING_TAG, "selected user from selectedUser: "+mRegisterViewModel.getSelectedUser());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRegisterViewModel = new ViewModelProvider(getActivity(),
                new RegisterViewModelFactory(this.getActivity().getApplication(),
                "test"))
                .get(RegisterViewModel.class);

    }
}