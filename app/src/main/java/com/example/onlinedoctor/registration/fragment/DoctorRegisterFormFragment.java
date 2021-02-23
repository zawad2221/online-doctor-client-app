package com.example.onlinedoctor.registration.fragment;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentDoctorRegisterFormBinding;
import com.example.onlinedoctor.databinding.FragmentDoctorRegisterFormBinding;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class DoctorRegisterFormFragment extends Fragment {
    private static final String DEBUGING_TAG = "DEBUGING_TAG";


    private FragmentDoctorRegisterFormBinding mFragmentDoctorRegisterFormBinding;
    private RegisterViewModel mRegisterViewModel;
    private ArrayAdapter<Specialization> specializationArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentDoctorRegisterFormBinding = FragmentDoctorRegisterFormBinding.inflate(inflater,container, false);
        View view = mFragmentDoctorRegisterFormBinding.getRoot();
        initViewModel();
        mRegisterViewModel.initDistricts();
        if(checkIfHasPreviousData()){
            setPreviousDataInView();
        }

        initSpecialization();
        initSpecializationDropDown();
        setSpecializationOnChange();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentDoctorRegisterFormBinding.DoctorSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllInputAndShowError()){
                    saveDataInViewModel();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.formContainer,new VerifyOtpCodeFragment())
                            .commit();
                    mRegisterViewModel.setCurrentSelectedFragment(RegisterViewModel.AllRegisterFragment.VERIFY_OTP_FORM);
                }
            }
        });
    }

    private void initSpecialization(){
        mRegisterViewModel.initSpecializedArea();
    }
    private void setSpecializationOnChange(){
        mRegisterViewModel.getSpecializedArea().observe(this.getActivity(), new Observer<List<Specialization>>() {
            @Override
            public void onChanged(List<Specialization> specializations) {
                Log.d(DEBUGING_TAG,"on chanage specialization data: "+mRegisterViewModel.getSpecializedArea().getValue());
                specializationArrayAdapter.notifyDataSetChanged();
                initSpecializationDropDown();

            }
        });
    }

    private void initSpecializationDropDown(){
        if (mRegisterViewModel.getSpecializedArea().getValue()==null){

            mRegisterViewModel.getSpecializedArea().setValue(new ArrayList<>());
        }
        specializationArrayAdapter = new ArrayAdapter<Specialization>(this.getContext(), R.layout.blood_group_spinner_item, mRegisterViewModel.getSpecializedArea().getValue());
        specializationArrayAdapter.setDropDownViewResource(R.layout.blood_group_spinner_dropdown_item);
        mFragmentDoctorRegisterFormBinding.doctorSpecializedAreaSpinner.setAdapter(specializationArrayAdapter);
    }

    private boolean checkIfHasPreviousData(){
        return (mRegisterViewModel.getDoctorName()==null? false: true);
    }

    private void setPreviousDataInView(){
        mFragmentDoctorRegisterFormBinding.inputTextDoctorName.setText(mRegisterViewModel.getDoctorName());
        mFragmentDoctorRegisterFormBinding.inputTextDoctorPhoneNumber.setText(mRegisterViewModel.getDoctorPhoneNumber());
        mFragmentDoctorRegisterFormBinding.inputTextDoctorBmdcId.setText(mRegisterViewModel.getDoctorBmdcId());
        mFragmentDoctorRegisterFormBinding.inputTextDoctorDesignation.setText(mRegisterViewModel.getDoctorDesignation());
        mFragmentDoctorRegisterFormBinding.inputTextDoctorPassword.setText(mRegisterViewModel.getDoctorPassword());


    }

    private void saveDataInViewModel(){
        mRegisterViewModel.setDoctorName(getDoctorName());
        mRegisterViewModel.setDoctorPhoneNumber(getDoctorPhoneNumber());
        mRegisterViewModel.setDoctorBmdcId(getDoctorBmdcId());
        mRegisterViewModel.setDoctorDesignation(getDoctorDesignation());
        mRegisterViewModel.setDoctorPassword(getDoctorPassword());
        mRegisterViewModel.setSpecialization(getSpecialization());

    }


    private boolean isDoctorNameValid(){
        return !(getDoctorName().isEmpty());
    }
    private boolean isPhoneNumberValid(){
        String phoneNumber = getDoctorPhoneNumber().toString();
        boolean isValidPhoneNumber = phoneNumber.length()==11 && phoneNumber.startsWith("01");
        return isValidPhoneNumber;
    }
    private boolean isDoctorBmdcIdValid(){
        return !(getDoctorBmdcId().isEmpty());
    }
    private boolean isDoctorDesignationValid(){
        return !(getDoctorDesignation().isEmpty());
    }
    private boolean isDoctorPasswordValid(){
        return !(getDoctorPassword().isEmpty());
    }
    private boolean checkAllInputAndShowError(){
        if(!isDoctorNameValid()){
            showError(mFragmentDoctorRegisterFormBinding.inputTextDoctorName,"invalid name");
            return false;
        }
        if(!isPhoneNumberValid()){
            showError(mFragmentDoctorRegisterFormBinding.inputTextDoctorPhoneNumber,"invalid phone number");
            return false;
        }
        if(!isDoctorBmdcIdValid()){
            showError(mFragmentDoctorRegisterFormBinding.inputTextDoctorBmdcId,"invalid input");
            return false;
        }
        if(!isDoctorDesignationValid()){
            showError(mFragmentDoctorRegisterFormBinding.inputTextDoctorDesignation,"invalid input");
            return false;
        }
        if(!isDoctorPasswordValid()) {
            showError(mFragmentDoctorRegisterFormBinding.inputTextDoctorPassword, "invalid password");
            return false;
        }
        return true;
    }
    private void showError(EditText editText, String message){
        editText.setError(message);
    }


    private String getDoctorName(){
        return mFragmentDoctorRegisterFormBinding.inputTextDoctorName.getText().toString();
    }
    private String getDoctorPhoneNumber(){
        return mFragmentDoctorRegisterFormBinding.inputTextDoctorPhoneNumber.getText().toString();
    }
    private String getDoctorBmdcId(){
        return mFragmentDoctorRegisterFormBinding.inputTextDoctorBmdcId.getText().toString();
    }
    private String getDoctorDesignation(){
        return mFragmentDoctorRegisterFormBinding.inputTextDoctorDesignation.getText().toString();
    }
    private String getDoctorPassword(){
        return mFragmentDoctorRegisterFormBinding.inputTextDoctorPassword.getText().toString();
    }
    private Specialization getSpecialization(){
        return mRegisterViewModel.getSpecializedArea().getValue().get(mFragmentDoctorRegisterFormBinding.doctorSpecializedAreaSpinner.getSelectedItemPosition());

    }



    private void initViewModel(){
        if(mRegisterViewModel==null)
            mRegisterViewModel = new ViewModelProvider(this.getActivity(),
                    new RegisterViewModelFactory(this.getActivity().getApplication(),"test"))
                    .get(RegisterViewModel.class);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentDoctorRegisterFormBinding = null;
    }
}