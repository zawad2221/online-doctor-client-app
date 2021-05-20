package com.example.onlinedoctor.patient.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.onlinedoctor.patient.fragment.PatientPrescriptionListFragment;
import com.example.onlinedoctor.patient.fragment.PatientReportListFragment;

public class PatientMedicalHistoryViewPagerAdapter extends FragmentStateAdapter {

    public PatientMedicalHistoryViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PatientPrescriptionListFragment();
            case 1:
                return new PatientReportListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
