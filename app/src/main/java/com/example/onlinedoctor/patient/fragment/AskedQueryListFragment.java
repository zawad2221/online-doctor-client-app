package com.example.onlinedoctor.patient.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentAskedQueryListBinding;
import com.example.onlinedoctor.login.LoginActivity;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.MainActivity;
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
    public boolean isLogin(){
        return (User.loginUser==null)? false:true;
    }
    public void redirectToLoginPage(){
        getActivity().finish();
        startActivity(new Intent(this.getActivity(), LoginActivity.class));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isLogin()) {
            redirectToLoginPage();
            return;
        }
        answeredFilterListener();
        mPatientHomeViewModel.getAskedQueryList(getContext(), getLoggedInPatientUserId());
        askedQueryListObserver();
        onProfileClick();

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
    private void onProfileClick(){
        mFragmentAskedQueryListBinding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLogoutMenu(v,R.menu.logout_menu);


            }
        });
    }
    private void showLogoutMenu(View view, int menuRes){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.getMenuInflater().inflate(menuRes,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
            switch (item.getItemId()){
                case R.id.logoutOption:
                    logout();
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void logout(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.LOGIN_USER_FILE_NAME), Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        redirectToHomePage();
    }
    private void redirectToHomePage(){
        getActivity().finish();
        startActivity(new Intent(this.getContext(), MainActivity.class));
    }


}