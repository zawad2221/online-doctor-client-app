package com.example.onlinedoctor.patient.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientMedicalHistoryBinding;
import com.example.onlinedoctor.login.LoginActivity;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.adapter.PatientMedicalHistoryViewPagerAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

public class PatientMedicalHistoryFragment extends Fragment {

    private PatientHomeViewModel mPatientHomeViewModel;
    private FragmentPatientMedicalHistoryBinding mFragmentPatientMedicalHistoryBinding;
    private PatientMedicalHistoryViewPagerAdapter mPatientMedicalHistoryViewPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public boolean isLogin(){
        return (User.loginUser==null)? false:true;
    }
    public void redirectToLoginPage(){
        getActivity().finish();
        startActivity(new Intent(this.getActivity(), LoginActivity.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentPatientMedicalHistoryBinding = FragmentPatientMedicalHistoryBinding
                .inflate(inflater, container, false);

        return mFragmentPatientMedicalHistoryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isLogin()) {
            redirectToLoginPage();
            return;
        }
        initViewModel();
        initPagerAdapter();
        initTabLayout();
    }

    private void initPagerAdapter(){
        mPatientMedicalHistoryViewPagerAdapter = new PatientMedicalHistoryViewPagerAdapter(getActivity());
        mFragmentPatientMedicalHistoryBinding.medicalHistoryViewPager.setAdapter(mPatientMedicalHistoryViewPagerAdapter);
        mFragmentPatientMedicalHistoryBinding.medicalHistoryViewPager.setPageTransformer(new ZoomOutPageTransformer());
    }

    private void initTabLayout(){
        new TabLayoutMediator(
                mFragmentPatientMedicalHistoryBinding.tabLayout,
                mFragmentPatientMedicalHistoryBinding.medicalHistoryViewPager,
                ((tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setText("Prescriptions");
                            break;
                        case 1:
                            tab.setText("Reports");
                            break;
                    }
                })
        ).attach();
    }

    private void initViewModel(){
        mPatientHomeViewModel = new ViewModelProvider(getActivity()).get(PatientHomeViewModel.class);
    }

    public static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }


}