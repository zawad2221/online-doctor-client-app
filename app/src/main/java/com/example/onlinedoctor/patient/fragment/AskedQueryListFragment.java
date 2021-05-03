package com.example.onlinedoctor.patient.fragment;

import android.app.AlertDialog;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentAskedQueryListBinding;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.adapter.AskedQueryRecyclerAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class AskedQueryListFragment extends Fragment {
    private PatientHomeViewModel mPatientHomeViewModel;
    FragmentAskedQueryListBinding mFragmentAskedQueryListBinding;
    AskedQueryRecyclerAdapter askedQueryRecyclerAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentAskedQueryListBinding = FragmentAskedQueryListBinding
                .inflate(inflater,  container,false );
        initViewModel();
        return mFragmentAskedQueryListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        answeredFilterListener();
        mPatientHomeViewModel.getAskedQueryList(getContext(), getLoggedInPatientUserId());
        askedQueryListObserver();

    }

    private int getLoggedInPatientUserId(){
        return User.loginUser.getUserId();
    }

    private void answeredFilterListener(){
        mFragmentAskedQueryListBinding.queryFilterChipGroup.setOnCheckedChangeListener((ChipGroup chipGroup, int checkedId) -> {
            switch (checkedId){
                case R.id.askedQueryAnswered:
                    answeredFilterChecked();
                    break;
                case R.id.askedQueryNotAnswered:
                    notAnsweredFilterChecked();
                    break;
            }
        });
    }

    private void notAnsweredFilterChecked() {
        getAnsweredQuery();
        askedQueryListObserver();
    }

    private void getAnsweredQuery() {
        mPatientHomeViewModel.getAnsweredQueryByPatient(getContext(),getLoggedInPatientUserId());
    }

    private void answeredFilterChecked() {
        getNotAnsweredQuery();
        askedQueryListObserver();
    }

    private void getNotAnsweredQuery() {
        mPatientHomeViewModel.getNotAnsweredQueryByPatient(getContext(),getLoggedInPatientUserId());
    }


    private void initViewModel(){
        mPatientHomeViewModel = new ViewModelProvider(getActivity())
                .get(PatientHomeViewModel.class);
    }



    private void askedQueryListObserver(){
        mPatientHomeViewModel.getAskedQueryMutableLiveData().observe(getViewLifecycleOwner(), (List askedQueryList) -> {
            initAskedQueryRecyclerView();
        });
    }

    private void initAskedQueryRecyclerView(){
        initAskedQueryAdapter();
        mFragmentAskedQueryListBinding.askedQueryRecyclerView.setHasFixedSize(true);
        mFragmentAskedQueryListBinding.askedQueryRecyclerView.setAdapter(askedQueryRecyclerAdapter);
        mFragmentAskedQueryListBinding.askedQueryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    private void initAskedQueryAdapter(){
        askedQueryRecyclerAdapter = new AskedQueryRecyclerAdapter(mPatientHomeViewModel.getAskedQueryMutableLiveData().getValue(), new AskedQueryRecyclerAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                showAlertDialog(mPatientHomeViewModel.getAskedQueryMutableLiveData().getValue().get(position).getAskedQueryAnswer());
            }
        });
    }
    private void showAlertDialog(String message){
        if(message.isEmpty()||message==null){
            message = "Did not reply yet.....";
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Light_Dialog)
                .setTitle("Reply ")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create();
        dialog.show();
        int height= dialog.getWindow().getAttributes().height;
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();

        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.95f), height);

        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setBackgroundColor(getResources().getColor(R.color.blue));
        positiveButton.setTextColor(getResources().getColor(R.color.white));
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
    }


}