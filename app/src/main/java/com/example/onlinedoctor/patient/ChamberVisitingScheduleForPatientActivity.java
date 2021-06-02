package com.example.onlinedoctor.patient;

import android.content.Intent;
import android.os.Bundle;

import com.example.onlinedoctor.databinding.ActivityChamberVisitingScheduleForPatientBinding;

import com.example.onlinedoctor.databinding.MakeAppointmentLayoutBinding;
import com.example.onlinedoctor.login.LoginActivity;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.adapter.ChamberVisitingScheduleRecyclerViewAdapter;
import com.example.onlinedoctor.patient.adapter.SpecializationSearchRecyclerViewAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;

import com.example.onlinedoctor.R;

public class ChamberVisitingScheduleForPatientActivity extends AppCompatActivity {
    private PatientHomeViewModel mPatientHomeViewModel;
    private SpecializationSearchRecyclerViewAdapter mSpecializationSearchRecyclerViewAdapter;
    private ActivityChamberVisitingScheduleForPatientBinding mActivityChamberVisitingScheduleForPatientBinding;
    private ChamberVisitingScheduleRecyclerViewAdapter mChamberVisitingScheduleRecyclerViewAdapter;
    private MakeAppointmentLayoutBinding mMakeAppointmentLayoutBinding;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChamberVisitingScheduleForPatientBinding = ActivityChamberVisitingScheduleForPatientBinding
                .inflate(getLayoutInflater());
        setContentView(mActivityChamberVisitingScheduleForPatientBinding.getRoot());
        mMakeAppointmentLayoutBinding = MakeAppointmentLayoutBinding
                .inflate(getLayoutInflater());
        if(!isLogin()) {
            redirectToLoginPage();
            return;
        }
        initViewModel();
        //mPatientHomeViewModel.selectedChamberId.setValue(getChamberIdFormIntentExtra());
//        mPatientHomeViewModel.initSpecialization();
//        getVisitingSchedule(mPatientHomeViewModel.selectedChamberId);
//        specializationLiveDataObserver();
//        visitingScheduleLiveDataObserver();

        Log.d(getString(R.string.DEBUGING_TAG),"selected chamberId: "+mPatientHomeViewModel.selectedChamberId);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle(getTitle());
        //initBottomSheet();


    }
    public boolean isLogin(){
        return (User.loginUser==null)? false:true;
    }
    public void redirectToLoginPage(){
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
    private int getChamberIdFormIntentExtra(){
        int chamberId = 0;
        try{
            Intent intent = getIntent();
            chamberId = Integer.parseInt(intent.getStringExtra(getString(R.string.chamber_visiting_schedule_activity_chamber_id_extra_value)));
        }
        catch (Exception e){
            Log.d(getString(R.string.DEBUGING_TAG),"failed to get chamber id form intent chamVisiSchedPatiAcitvit");
        }
        return chamberId;


    }

//    private void getVisitingSchedule(int chamberId){
//        mPatientHomeViewModel.initVisitingSchedule(this,
//                chamberId
//        );
//    }
//    private void getVisitingSchedule(int chamberId, int specializationId){
//        mPatientHomeViewModel.initVisitingSchedule(this,
//                chamberId,
//                specializationId
//        );
//    }
//
    private void initViewModel() {
        mPatientHomeViewModel = new ViewModelProvider(
                this
        )
                .get(PatientHomeViewModel.class);
    }
//    private void initSpecializationRecyclerView() {
//
////        List<Specialization> specializationArrayList = new ArrayList<>();
////        Specialization specialization=new Specialization(1,"Medicine");
////        Specialization specialization1=new Specialization(1,"Cardiologist");
////        Specialization specialization2=new Specialization(1,"Neuro");
////        Specialization specialization3=new Specialization(1,"Pharmacy");
////        Specialization specialization4=new Specialization(1,"Pharmacy");
////        Specialization specialization5=new Specialization(1,"Pharmacy");
////
////        specializationArrayList.add(specialization);
////        specializationArrayList.add(specialization1);
////        specializationArrayList.add(specialization2);
////        specializationArrayList.add(specialization3);
////        specializationArrayList.add(specialization4);
////        specializationArrayList.add(specialization5);
//        mSpecializationSearchRecyclerViewAdapter=new SpecializationSearchRecyclerViewAdapter(mPatientHomeViewModel.getSpecializationList().getValue(), new SpecializationSearchRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Log.d(getString(R.string.DEBUGING_TAG),"tap: "+position);
//
//                specializationOnClick(position);
//            }
//
//        });
//        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setHasFixedSize(true);
//        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setAdapter(mSpecializationSearchRecyclerViewAdapter);
//
//    }
//    private void specializationLiveDataObserver(){
//        mPatientHomeViewModel.getSpecializationList().observe(this, new Observer<List<Specialization>>() {
//            @Override
//            public void onChanged(List<Specialization> specializations) {
//                initSpecializationRecyclerView();
//
//                Log.d(getString(R.string.DEBUGING_TAG),"data from change: "+specializations.size());
//                Log.d(getString(R.string.DEBUGING_TAG),"data from change view model: "+mPatientHomeViewModel.getSpecializationList().getValue().size());
//
//            }
//        });
//    }
//
//    private void specializationOnClick(int position){
//        if(position==mPatientHomeViewModel.visitingScheduleRecyclerViewSelectedItem){
//            getVisitingSchedule(mPatientHomeViewModel.selectedChamberId);
//            mPatientHomeViewModel.visitingScheduleRecyclerViewSelectedItem = -1;
//        }
//        else {
//            getVisitingSchedule(
//                    mPatientHomeViewModel.selectedChamberId,
//                    mPatientHomeViewModel.getSpecializationList().getValue().get(position).getSpecializationId()
//            );
//            mPatientHomeViewModel.visitingScheduleRecyclerViewSelectedItem = position;
//        }
//
//
//        visitingScheduleLiveDataObserver();
//
//    }
//
//    private void initVisitingScheduleRecyclerView() {
//
////        List<Specialization> specializationArrayList = new ArrayList<>();
////        Specialization specialization=new Specialization(1,"Medicine");
////        Specialization specialization1=new Specialization(1,"Cardiologist");
////        Specialization specialization2=new Specialization(1,"Neuro");
////        Specialization specialization3=new Specialization(1,"Pharmacy");
////        Specialization specialization4=new Specialization(1,"Pharmacy");
////        Specialization specialization5=new Specialization(1,"Pharmacy");
////
////        specializationArrayList.add(specialization);
////        specializationArrayList.add(specialization1);
////        specializationArrayList.add(specialization2);
////        specializationArrayList.add(specialization3);
////        specializationArrayList.add(specialization4);
////        specializationArrayList.add(specialization5);
//        mChamberVisitingScheduleRecyclerViewAdapter=new ChamberVisitingScheduleRecyclerViewAdapter(mPatientHomeViewModel.getVisitingScheduleList().getValue(), new ChamberVisitingScheduleRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Log.d(getString(R.string.DEBUGING_TAG),"tap in visiting schedule: "+position);
//
//
//            }
//
//        });
//        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.doctorVisitingScheduleRecyclerView.setHasFixedSize(true);
//        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.doctorVisitingScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        mActivityChamberVisitingScheduleForPatientBinding.scheduleLayout.doctorVisitingScheduleRecyclerView.setAdapter(mChamberVisitingScheduleRecyclerViewAdapter);
//
//    }
//    private void visitingScheduleLiveDataObserver(){
//        mPatientHomeViewModel.getVisitingScheduleList().observe(this, new Observer<List<VisitingSchedule>>() {
//            @Override
//            public void onChanged(List<VisitingSchedule> visitingSchedules) {
//                initVisitingScheduleRecyclerView();
//                Log.d(getString(R.string.DEBUGING_TAG),"visiting schedule on change"+(visitingSchedules==null? "null":visitingSchedules.size()));
//            }
//        });
//    }



}