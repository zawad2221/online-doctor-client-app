package com.example.onlinedoctor.doctor.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.DateAndTime;
import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentDoctorHomeBinding;
import com.example.onlinedoctor.doctor.adapter.DoctorVisitingScheduleAdapter;
import com.example.onlinedoctor.doctor.view_model.DoctorMainViewModel;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.model.VisitingSchedule;

import java.util.List;


public class DoctorHomeFragment extends Fragment {
    private FragmentDoctorHomeBinding mDoctorHomeBinding;
    private DoctorMainViewModel mDoctorMainViewModel;
    private DoctorVisitingScheduleAdapter doctorVisitingScheduleAdapter;
    private NavController navController;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDoctorHomeBinding = FragmentDoctorHomeBinding
                .inflate(inflater, container, false);
        return mDoctorHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNavController();
        iniViewModel();
        initVisitingScheduleList();
        visitingScheduleListObserver();
        mDoctorHomeBinding.createScheduleButton.setOnClickListener(v -> {
            showCreateSchedulePage();
        });
    }

    private void showCreateSchedulePage(){
        navController.navigate(R.id.action_doctorHomeFragment_to_createVisitingSchedule);
    }

    private void iniViewModel(){
        mDoctorMainViewModel = new ViewModelProvider(getActivity())
                .get(DoctorMainViewModel.class);
    }
    private void initAdapter(){
        doctorVisitingScheduleAdapter = new DoctorVisitingScheduleAdapter(mDoctorMainViewModel.getVisitingScheduleList().getValue(), new DoctorVisitingScheduleAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                mDoctorMainViewModel.selectedVisitingScheduleItem=position;
                navController.navigate(R.id.action_doctorHomeFragment_to_doctorScheduleAppointmentFragment);
            }
        });
    }
    private void initRecyclerView(){
        initAdapter();
        mDoctorHomeBinding.visitingScheduleListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mDoctorHomeBinding.visitingScheduleListRecycler.setHasFixedSize(true);
        mDoctorHomeBinding.visitingScheduleListRecycler.setAdapter(doctorVisitingScheduleAdapter);
    }
    private void initVisitingScheduleList(){
        mDoctorMainViewModel.initVisitingSchedule(getContext(),
                User.loginUser.getUserId());
    }
    private void visitingScheduleListObserver(){
        mDoctorMainViewModel.getVisitingScheduleList().observe(getActivity(), new Observer<List<VisitingSchedule>>() {
            @Override
            public void onChanged(List<VisitingSchedule> visitingSchedules) {
                if(visitingSchedules.get(0).getVisitingScheduleId()!=null){
                    initRecyclerView();
                }
            }
        });
    }
    private void initNavController(){
        navController = ((NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.doctorHomeFragmentHolder)).getNavController();
    }
}