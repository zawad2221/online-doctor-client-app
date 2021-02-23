package com.example.onlinedoctor.registration.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPathologyRegisterFormBinding;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;


public class PathologyRegisterFormFragment extends Fragment {
    private FragmentPathologyRegisterFormBinding mFragmentPathologyRegisterFormBinding;
    private RegisterViewModel mRegisterViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentPathologyRegisterFormBinding = FragmentPathologyRegisterFormBinding.inflate(inflater,container,false);
        View view = mFragmentPathologyRegisterFormBinding.getRoot();
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
        mFragmentPathologyRegisterFormBinding.pathologyCenterSignUpButton.setOnClickListener(new View.OnClickListener() {
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
        return (mRegisterViewModel.getPathologyName()==null? false: true);
    }

    private void setPreviousDataInView(){
        mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterName.setText(mRegisterViewModel.getPathologyName());
        mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterPhoneNumber.setText(mRegisterViewModel.getPathologyPhoneNumber());
        mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterAddress.setText(mRegisterViewModel.getPathologyAddress());


    }

    private void saveDataInViewModel(){
        mRegisterViewModel.setPathologyName(getPathologyName());
        mRegisterViewModel.setPathologyPhoneNumber(getPathologyPhoneNumber());
        mRegisterViewModel.setPathologyAddress(getPathologyAddress());
        mRegisterViewModel.setPathologyPassword(getPathologyPassword());
    }
//    private void setDataOnDistrictDropDown(){
//        ArrayList<String> disList = new ArrayList<>();
//        for(District district: mRegisterViewModel.getDistricts()){
//            disList.add(district.getName());
//        }
//        districtDropDownAdapter = new ArrayAdapter<>(this.getContext(), R.layout.blood_group_spinner_item, disList);
//        districtDropDownAdapter.setDropDownViewResource(R.layout.blood_group_spinner_dropdown_item);
//        mFragmentPathologyRegisterFormBinding.PathologyDistrictSpinner.setAdapter(districtDropDownAdapter);
//
//    }

    private boolean isPathologyNameValid(){
        return !(getPathologyName().isEmpty());
    }
    private boolean isPhoneNumberValid(){
        String phoneNumber = getPathologyPhoneNumber().toString();
        boolean isValidPhoneNumber = phoneNumber.length()==11 && phoneNumber.startsWith("01");
        return isValidPhoneNumber;
    }
    private boolean isPathologyAddressValid(){
        return !(getPathologyAddress().isEmpty());
    }
    private boolean isPathologyPasswordValid(){
        return !(getPathologyPassword().isEmpty());
    }
    private boolean checkAllInputAndShowError(){
        if(!isPathologyNameValid()){
            showError(mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterName,"invalid name");
            return false;
        }
        if(!isPhoneNumberValid()){
            showError(mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterPhoneNumber,"invalid phone number");
            return false;
        }
        if(!isPathologyAddressValid()){
            showError(mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterAddress,"invalid address");
            return false;
        }
        if(!isPathologyPasswordValid()) {
            showError(mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterPassword, "can not be empty");
            return false;
        }
        return true;
    }
    private void showError(EditText editText, String message){
        editText.setError(message);
    }
    private String getPathologyName(){
        return mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterName.getText().toString();
    }
    private String getPathologyPhoneNumber(){
        return mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterPhoneNumber.getText().toString();
    }
    private String getPathologyAddress(){
        return mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterAddress.getText().toString();
    }
    private String getPathologyPassword(){
        return mFragmentPathologyRegisterFormBinding.inputTextPathologyCenterPassword.getText().toString();
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
        mFragmentPathologyRegisterFormBinding = null;
    }
}