package com.example.onlinedoctor.registration.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SpinnerAdapter;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentChamberRegisterFormBinding;
import com.example.onlinedoctor.registration.RegisterActivity;
import com.example.onlinedoctor.registration.model.District;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;

import java.util.ArrayList;

public class ChamberRegisterFormFragment extends Fragment {

    private FragmentChamberRegisterFormBinding mFragmentChamberRegisterFormBinding;
    private RegisterViewModel mRegisterViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentChamberRegisterFormBinding = FragmentChamberRegisterFormBinding.inflate(inflater,container,false);
        View view = mFragmentChamberRegisterFormBinding.getRoot();
        initViewModel();
        mRegisterViewModel.initDistricts();
        if(checkIfHasPreviousData()){
            setPreviousDataInView();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentChamberRegisterFormBinding.ChamberSignUpButton.setOnClickListener(new View.OnClickListener() {
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

    private boolean checkIfHasPreviousData(){
        return (mRegisterViewModel.getChamberName()==null? false: true);
    }

    private void setPreviousDataInView(){
        mFragmentChamberRegisterFormBinding.inputTextChamberName.setText(mRegisterViewModel.getChamberName());
        mFragmentChamberRegisterFormBinding.inputTextChamberPhoneNumber.setText(mRegisterViewModel.getChamberPhoneNumber());
        mFragmentChamberRegisterFormBinding.inputTextChamberAddress.setText(mRegisterViewModel.getChamberAddress());


    }

    private void saveDataInViewModel(){
        mRegisterViewModel.setChamberName(getChamberName());
        mRegisterViewModel.setChamberPhoneNumber(getChamberPhoneNumber());
        mRegisterViewModel.setChamberAddress(getChamberAddress());
        mRegisterViewModel.setChamberPassword(getChamberPassword());
    }
//    private void setDataOnDistrictDropDown(){
//        ArrayList<String> disList = new ArrayList<>();
//        for(District district: mRegisterViewModel.getDistricts()){
//            disList.add(district.getName());
//        }
//        districtDropDownAdapter = new ArrayAdapter<>(this.getContext(), R.layout.blood_group_spinner_item, disList);
//        districtDropDownAdapter.setDropDownViewResource(R.layout.blood_group_spinner_dropdown_item);
//        mFragmentChamberRegisterFormBinding.chamberDistrictSpinner.setAdapter(districtDropDownAdapter);
//
//    }

    private boolean isChamberNameValid(){
        return !(getChamberName().isEmpty());
    }
    private boolean isPhoneNumberValid(){
        String phoneNumber = getChamberPhoneNumber().toString();
        boolean isValidPhoneNumber = phoneNumber.length()==11 && phoneNumber.startsWith("01");
        return isValidPhoneNumber;
    }
    private boolean isChamberAddressValid(){
        return !(getChamberAddress().isEmpty());
    }
    private boolean isChamberPasswordValid(){
        return !(getChamberPassword().isEmpty());
    }
    private boolean checkAllInputAndShowError(){
        if(!isChamberNameValid()){
            showError(mFragmentChamberRegisterFormBinding.inputTextChamberName,"invalid name");
            return false;
        }
        if(!isPhoneNumberValid()){
            showError(mFragmentChamberRegisterFormBinding.inputTextChamberPhoneNumber,"invalid phone number");
            return false;
        }
        if(!isChamberAddressValid()){
            showError(mFragmentChamberRegisterFormBinding.inputTextChamberAddress,"invalid address");
            return false;
        }
        if(!isChamberPasswordValid()) {
            showError(mFragmentChamberRegisterFormBinding.inputTextChamberPassword, "invalid password");
            return false;
        }
        return true;
    }
    private void showError(EditText editText, String message){
        editText.setError(message);
    }
    private String getChamberName(){
        return mFragmentChamberRegisterFormBinding.inputTextChamberName.getText().toString();
    }
    private String getChamberPhoneNumber(){
        return mFragmentChamberRegisterFormBinding.inputTextChamberPhoneNumber.getText().toString();
    }
    private String getChamberAddress(){
        return mFragmentChamberRegisterFormBinding.inputTextChamberAddress.getText().toString();
    }
    private String getChamberPassword(){
        return mFragmentChamberRegisterFormBinding.inputTextChamberPassword.getText().toString();
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
        mFragmentChamberRegisterFormBinding = null;
    }
}