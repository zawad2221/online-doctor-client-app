package com.example.onlinedoctor.patient.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentVisitingScheduleForPatientBinding;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.model.VisitingSchedule;
import com.example.onlinedoctor.patient.adapter.ChamberVisitingScheduleRecyclerViewAdapter;
import com.example.onlinedoctor.patient.adapter.SpecializationSearchRecyclerViewAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;

import java.util.List;


public class VisitingScheduleForPatientFragment extends Fragment {

    FragmentVisitingScheduleForPatientBinding mFragmentVisitingScheduleForPatientBinding;
    private PatientHomeViewModel mPatientHomeViewModel;
    private SpecializationSearchRecyclerViewAdapter mSpecializationSearchRecyclerViewAdapter;
    private ChamberVisitingScheduleRecyclerViewAdapter mChamberVisitingScheduleRecyclerViewAdapter;
    private NavHostFragment mNavHostFragment;
    private NavController mNavController;
    private ProgressDialog loadingDataProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentVisitingScheduleForPatientBinding = FragmentVisitingScheduleForPatientBinding
                .inflate(inflater, container, false);


        Toolbar toolbar = (Toolbar) mFragmentVisitingScheduleForPatientBinding.getRoot().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);



        return mFragmentVisitingScheduleForPatientBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();

        mPatientHomeViewModel.selectedChamberId.setValue(getChamberIdFormIntentExtra());
        mFragmentVisitingScheduleForPatientBinding.chamberName.setText(getChamberNameFormIntentExtra());
        mFragmentVisitingScheduleForPatientBinding.chamberAddress.setText(getChamberAddressFormIntentExtra());

        mFragmentVisitingScheduleForPatientBinding.toolbarLayout.setTitle(getActivity().getTitle());
        Log.d(getString(R.string.DEBUGING_TAG),"select chamber: "+mPatientHomeViewModel.selectedChamberId);
        mPatientHomeViewModel.initSpecialization();
        getVisitingSchedule(mPatientHomeViewModel.selectedChamberId.getValue());
        specializationLiveDataObserver();
        visitingScheduleLiveDataObserver();
        showLoadingDataProgressDialog();

        initNavController();
        mFragmentVisitingScheduleForPatientBinding.closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        mFragmentVisitingScheduleForPatientBinding.tobBarMenu.setOnClickListener(v -> {
            showMenu(v,R.menu.visiting_schedule_for_patient_top_menu);
        });




    }

    private void showSendQueryDialog(){


        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this.getContext(), R.style.Theme_AppCompat_Light_Dialog)
                .setView(getLayoutInflater().inflate(R.layout.send_quey_dialog,null))
                .setTitle("Send Query")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("Cancel", ((dialog, which) -> {

                }))
                .setCancelable(false);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
//        // Fetch the PositiveButton
//        final Button lPositiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        // Fetch the LinearLayout.
//        final LinearLayout lParent         = (LinearLayout) lPositiveButton.getParent();
//        // Ensure the Parent of the Buttons aligns it's contents to the right.
//        lParent.setGravity(Gravity.RIGHT);
//        final Button lNegativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lNegativeButton.getLayoutParams();
//        layoutParams.weight = -10;
//
//        lPositiveButton.setLayoutParams(layoutParams);
//        lNegativeButton.setLayoutParams(layoutParams);
//        // Fetch the LinearLayout.
//        final LinearLayout lParentNegative         = (LinearLayout) lNegativeButton.getParent();
        // Ensure the Parent of the Buttons aligns it's contents to the right.
        //lParentNegative.setGravity(Gravity.LEFT);

        // Hide the LeftSpacer. (Strict dependence on the order of the layout!)
        //lParent.getChildAt(1).setVisibility(View.GONE);

    }

    private void showMenu(View view, @MenuRes Integer menuRes){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener((MenuItem menuItem) ->{
            switch (menuItem.getItemId()){
                case R.id.sendQuery:
                    showSendQueryDialog();
                    break;

            }
            return false;
        });
        popupMenu.show();
    }
    private void initNavController(){
        mNavHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.chamberVisitingScheduleFragmentHolder);
        mNavController = mNavHostFragment.getNavController();
    }
    private int getChamberIdFormIntentExtra(){
        int chamberId = 0;
        try{
            Intent intent = getActivity().getIntent();
            chamberId = Integer.parseInt(intent.getStringExtra(getString(R.string.chamber_visiting_schedule_activity_chamber_id_extra_value)));
        }
        catch (Exception e){
            Log.d(getString(R.string.DEBUGING_TAG),"failed to get chamber id form intent chamVisiSchedPatiAcitvit");
        }
        return chamberId;


    }
    private String getChamberNameFormIntentExtra(){
        String chamberName = null;
        try{
            Intent intent = getActivity().getIntent();
            chamberName = (intent.getStringExtra(getString(R.string.chamber_visiting_schedule_activity_chamber_name_extra_value)));
        }
        catch (Exception e){
            Log.d(getString(R.string.DEBUGING_TAG),"failed to get chamber name form intent chamVisiSchedPatiAcitvit");
        }
        return chamberName;


    }
    private String getChamberAddressFormIntentExtra(){
        String chamberAddress = null;
        try{
            Intent intent = getActivity().getIntent();
            chamberAddress = (intent.getStringExtra(getString(R.string.chamber_visiting_schedule_activity_chamber_address_extra_value)));
        }
        catch (Exception e){
            Log.d(getString(R.string.DEBUGING_TAG),"failed to get chamberAddress form intent chamVisiSchedPatiAcitvit");
        }
        return chamberAddress;


    }



    private void getVisitingSchedule(int chamberId){
        mPatientHomeViewModel.initVisitingSchedule(getContext(),
                chamberId
        );
    }
    private void getVisitingSchedule(int chamberId, int specializationId){
        mPatientHomeViewModel.initVisitingSchedule(getContext(),
                chamberId,
                specializationId
        );
    }

    private void initViewModel() {
        mPatientHomeViewModel = new ViewModelProvider(
                requireActivity()
        )
                .get(PatientHomeViewModel.class);
    }
    private void initSpecializationRecyclerView() {


        mSpecializationSearchRecyclerViewAdapter=new SpecializationSearchRecyclerViewAdapter(mPatientHomeViewModel.getSpecializationList().getValue(), new SpecializationSearchRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(getString(R.string.DEBUGING_TAG),"tap: "+position);

                specializationOnClick(position);
            }

        });

        mFragmentVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setHasFixedSize(true);
        mFragmentVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mFragmentVisitingScheduleForPatientBinding.scheduleLayout.specializationFilterRecyclerView.setAdapter(mSpecializationSearchRecyclerViewAdapter);

    }
    private void specializationLiveDataObserver(){
        mPatientHomeViewModel.getSpecializationList().observe(getActivity(), new Observer<List<Specialization>>() {
            @Override
            public void onChanged(List<Specialization> specializations) {
                initSpecializationRecyclerView();

                Log.d(getString(R.string.DEBUGING_TAG),"specialization change: "+specializations.size());
                Log.d(getString(R.string.DEBUGING_TAG),"specialization change view model: "+mPatientHomeViewModel.getSpecializationList().getValue().size());

            }
        });
    }

    private void specializationOnClick(int position){
        showLoadingDataProgressDialog();
        if(position==mPatientHomeViewModel.visitingScheduleRecyclerViewSelectedItem){
            getVisitingSchedule(mPatientHomeViewModel.selectedChamberId.getValue());
            mPatientHomeViewModel.visitingScheduleRecyclerViewSelectedItem = -1;
        }
        else {
            getVisitingSchedule(
                    mPatientHomeViewModel.selectedChamberId.getValue(),
                    mPatientHomeViewModel.getSpecializationList().getValue().get(position).getSpecializationId()
            );
            mPatientHomeViewModel.visitingScheduleRecyclerViewSelectedItem = position;
        }


        visitingScheduleLiveDataObserver();

    }

    private void initVisitingScheduleRecyclerView() {

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
        mChamberVisitingScheduleRecyclerViewAdapter=new ChamberVisitingScheduleRecyclerViewAdapter(mPatientHomeViewModel.getVisitingScheduleList().getValue(), new ChamberVisitingScheduleRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(getString(R.string.DEBUGING_TAG),"tap in visiting schedule: "+position);
                visitingScheduleOnClick(position);


            }

        });
        mFragmentVisitingScheduleForPatientBinding.scheduleLayout.doctorVisitingScheduleRecyclerView.setHasFixedSize(true);
        mFragmentVisitingScheduleForPatientBinding.scheduleLayout.doctorVisitingScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mFragmentVisitingScheduleForPatientBinding.scheduleLayout.doctorVisitingScheduleRecyclerView.setAdapter(mChamberVisitingScheduleRecyclerViewAdapter);

    }

    private void visitingScheduleOnClick(int position) {
        mPatientHomeViewModel.selectedVisitingSchedule = position;
        mNavController.navigate(R.id.action_chamberVisitingScheduleForPatient_to_patientMakeAppointment);
    }

    private void visitingScheduleLiveDataObserver(){
        mPatientHomeViewModel.getVisitingScheduleList().observe(getActivity(), new Observer<List<VisitingSchedule>>() {
            @Override
            public void onChanged(List<VisitingSchedule> visitingSchedules) {
                dismissLoadingDataProgressDialog();
                initVisitingScheduleRecyclerView();
                Log.d(getString(R.string.DEBUGING_TAG),"visiting schedule on change"+(visitingSchedules==null? "null":visitingSchedules.size()));
            }
        });
    }

    private void showLoadingDataProgressDialog(){
        loadingDataProgressDialog = new ProgressDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        loadingDataProgressDialog.setIndeterminate(true);
        loadingDataProgressDialog.setCancelable(false);
        loadingDataProgressDialog.setMessage("Loading...");
        loadingDataProgressDialog.show();
    }
    private void dismissLoadingDataProgressDialog(){
        loadingDataProgressDialog.dismiss();
    }
}