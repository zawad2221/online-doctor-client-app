package com.example.onlinedoctor.patient.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientPrescriptionListBinding;
import com.example.onlinedoctor.model.Prescription;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.adapter.PatientPrescriptionRecyclerAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;

import java.util.List;


public class PatientPrescriptionListFragment extends Fragment {
    private PatientHomeViewModel mPatientHomeViewModel;
    private FragmentPatientPrescriptionListBinding mFragmentPatientPrescriptionListBinding;
    private PatientPrescriptionRecyclerAdapter mPatientPrescriptionRecyclerAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentPatientPrescriptionListBinding = FragmentPatientPrescriptionListBinding
                .inflate(inflater, container, false);
        iniViewModel();
        getPrescriptionList();
        return mFragmentPatientPrescriptionListBinding.getRoot();
    }


    private void iniViewModel(){
        mPatientHomeViewModel = new ViewModelProvider(getActivity()).get(PatientHomeViewModel.class);
    }
    private void getPrescriptionList(){
        mPatientHomeViewModel.getPrescriptionListByPatientUserId(getContext(), User.loginUser.getUserId());
        patientPrescriptionListObserver();
    }
    private void patientPrescriptionListObserver(){
        mPatientHomeViewModel.getPrescriptionListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Prescription>>() {
            @Override
            public void onChanged(List<Prescription> prescriptions) {
                initPrescriptionRecyclerView();
                Log.d(getString(R.string.DEBUGING_TAG), "patient prescription list observer");
            }
        });
    }
    private void initPrescriptionRecyclerView(){
        initPrescriptionRecyclerAdapter();
        mFragmentPatientPrescriptionListBinding.patientPrescriptionRecyclerView.setAdapter(mPatientPrescriptionRecyclerAdapter);
        mFragmentPatientPrescriptionListBinding.patientPrescriptionRecyclerView.setHasFixedSize(true);
        mFragmentPatientPrescriptionListBinding.patientPrescriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void initPrescriptionRecyclerAdapter(){
        mPatientPrescriptionRecyclerAdapter = new PatientPrescriptionRecyclerAdapter(
                mPatientHomeViewModel.getPrescriptionListLiveData().getValue(),
                new PatientPrescriptionRecyclerAdapter.OnClickListener() {
                    @Override
                    public void OnItemClickListener(int position) {
                        prescriptionRecyclerViewItemClick(position);
                    }
                }
        );
    }
    private void prescriptionRecyclerViewItemClick(int position){
        Toast.makeText(getContext(),"itemClick: "+position,Toast.LENGTH_LONG).show();

    }
}