package com.example.onlinedoctor.patient;

import android.os.Bundle;

import com.example.onlinedoctor.databinding.ActivityChamberVisitingScheduleForPatientBinding;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.patient.adapter.SpecializationSearchRecyclerViewAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModelFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import com.example.onlinedoctor.R;

import java.util.ArrayList;
import java.util.List;

public class ChamberVisitingScheduleForPatientActivity extends AppCompatActivity {
    private PatientHomeViewModel mPatientHomeViewModel;
    private SpecializationSearchRecyclerViewAdapter mSpecializationSearchRecyclerViewAdapter;
    private ActivityChamberVisitingScheduleForPatientBinding mActivityChamberVisitingScheduleForPatientBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChamberVisitingScheduleForPatientBinding = ActivityChamberVisitingScheduleForPatientBinding
                .inflate(getLayoutInflater());
        setContentView(mActivityChamberVisitingScheduleForPatientBinding.getRoot());

        initViewModel();
        mPatientHomeViewModel.initSpecialization();
        specializationLiveDataObserver();

        Log.d(getString(R.string.DEBUGING_TAG),"selected chamberId: "+mPatientHomeViewModel.selectedChamberId);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());


    }

    private void initViewModel() {
        mPatientHomeViewModel = new ViewModelProvider(
                this,
                new PatientHomeViewModelFactory(this.getApplication(), "test")
        )
                .get(PatientHomeViewModel.class);
    }private void initRecyclerView() {

//        List<Specialization> specializationArrayList = new ArrayList<>();
//        Specialization specialization=new Specialization(1,"Medicine");
//        Specialization specialization1=new Specialization(1,"Cardiologist");
//        Specialization specialization2=new Specialization(1,"Neuro");
//        Specialization specialization3=new Specialization(1,"Pharmacy");
//        Specialization specialization4=new Specialization(1,"Pharmacy");
//        Specialization specialization5=new Specialization(1,"Pharmacy");
//
//        specializationArrayList.add(specialization);
//        specializationArrayList.add(specialization1);
//        specializationArrayList.add(specialization2);
//        specializationArrayList.add(specialization3);
//        specializationArrayList.add(specialization4);
//        specializationArrayList.add(specialization5);
        mSpecializationSearchRecyclerViewAdapter=new SpecializationSearchRecyclerViewAdapter(mPatientHomeViewModel.getSpecializationList().getValue(), new SpecializationSearchRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(getString(R.string.DEBUGING_TAG),"tap: "+position);

                //mFragmentPatientHomeBinding.searchView.clearFocus();
            }

        });
        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setHasFixedSize(true);
        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setAdapter(mSpecializationSearchRecyclerViewAdapter);

    }
    private void specializationLiveDataObserver(){
        mPatientHomeViewModel.getSpecializationList().observe(this, new Observer<List<Specialization>>() {
            @Override
            public void onChanged(List<Specialization> specializations) {
                initRecyclerView();

                Log.d(getString(R.string.DEBUGING_TAG),"data from change: "+specializations.size());
                Log.d(getString(R.string.DEBUGING_TAG),"data from change view model: "+mPatientHomeViewModel.getSpecializationList().getValue().size());

            }
        });
    }

}