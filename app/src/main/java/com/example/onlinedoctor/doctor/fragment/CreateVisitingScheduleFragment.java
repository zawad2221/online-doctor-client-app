package com.example.onlinedoctor.doctor.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentCreateVisitingScheduleBinding;
import com.example.onlinedoctor.doctor.adapter.SearchChamberRecyclerAdapter;
import com.example.onlinedoctor.doctor.view_model.DoctorMainViewModel;
import com.example.onlinedoctor.model.Chamber;

import java.util.List;


public class CreateVisitingScheduleFragment extends Fragment {
    DoctorMainViewModel mDoctorMainViewModel;
    FragmentCreateVisitingScheduleBinding mFragmentCreateVisitingScheduleBinding;
    SearchChamberRecyclerAdapter searchChamberRecyclerAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentCreateVisitingScheduleBinding = FragmentCreateVisitingScheduleBinding
                .inflate(inflater, container, false);
        return mFragmentCreateVisitingScheduleBinding.getRoot();
    }
    private void onBack(){
        getActivity().onBackPressed();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentCreateVisitingScheduleBinding.closeFragment.setOnClickListener(v->{
            onBack();
        });
        initViewModel();
        searchChamberButtonOnClick();

    }

    private void searchChamberButtonOnClick(){
        mFragmentCreateVisitingScheduleBinding.searchChamberButton.setOnClickListener(v -> {
            if(isValidChamberSearchInput()){
                searchChamber(getContext(),getChamberQuery());
                searchChamberObserver();
            }
            else {
                setErrorMessageOnChamberQueryInput("invalid input");
            }
        });
    }
    private String getChamberQuery(){
        return mFragmentCreateVisitingScheduleBinding.chamberNameOrNumber.getText().toString();
    }
    private void setErrorMessageOnChamberQueryInput(String message){
        mFragmentCreateVisitingScheduleBinding.chamberNameOrNumber.setError(message);
    }
    private boolean isValidChamberSearchInput(){
        return getChamberQuery().isEmpty()? false:true;
    }

    private void initViewModel(){
        mDoctorMainViewModel = new ViewModelProvider(getActivity())
                .get(DoctorMainViewModel.class);
    }
    private void searchChamber(Context context, String query){
        mDoctorMainViewModel.searchChamber(context, query);
    }
    private void searchChamberObserver(){
        mDoctorMainViewModel.getSearchChamberList().observe(getActivity(), new Observer<List<Chamber>>() {
            @Override
            public void onChanged(List<Chamber> chambers) {
                initRecyclerView();
                setVisibilityOfChamberRecyclerView(View.VISIBLE);
            }
        });
    }
    private void selectedChamber(int position){
        mDoctorMainViewModel.selectedChamberItemPosition=position;
        setVisibilityOfChamberRecyclerView(View.GONE);
        setChamberInView();
    }
    private void setChamberInView(){
        mFragmentCreateVisitingScheduleBinding.chamberNameOrNumber.setText(
                mDoctorMainViewModel.getSearchChamberList().getValue().get(

                        mDoctorMainViewModel.selectedChamberItemPosition
                )
                .getChamberUser()
                .getUserName()
        );

    }
    private void initAdapter(){
        searchChamberRecyclerAdapter = new SearchChamberRecyclerAdapter(mDoctorMainViewModel.getSearchChamberList().getValue(),
                new SearchChamberRecyclerAdapter.SearchChamberRecyclerItemClickListener() {
                    @Override
                    public void itemOnClick(int position) {
                        selectedChamber(position);
                    }
                });
    }
    private void initRecyclerView(){
        initAdapter();
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setHasFixedSize(true);
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setAdapter(searchChamberRecyclerAdapter);
    }

    private void setVisibilityOfChamberRecyclerView(int visibility){
        mFragmentCreateVisitingScheduleBinding.chamberSearchResultRecyclerView.setVisibility(visibility);
    }
}