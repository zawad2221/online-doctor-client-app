package com.example.onlinedoctor.patient.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentBookedAppointmentDetailsBinding;
import com.example.onlinedoctor.model.Appointment;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class BookedAppointmentDetailsFragment extends Fragment {
    private PatientHomeViewModel mPatientHomeViewModel;
    private FragmentBookedAppointmentDetailsBinding mFragmentBookedAppointmentDetailsBinding;
    private NavController navController;

    private SupportMapFragment supportMapFragment;

    private GoogleMap mMap;
    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng chamberLocation = getChamberLocation();
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(chamberLocation)
                    .title(getChamberName());
            Marker marker = googleMap.addMarker(markerOptions);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getChamberLocation(),12.0f));
            mMap = googleMap;

        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentBookedAppointmentDetailsBinding = FragmentBookedAppointmentDetailsBinding
                .inflate(inflater, container, false);
        initViewModel();
        mFragmentBookedAppointmentDetailsBinding.setAppointment(getCurrentSelectedAppointment());

        return mFragmentBookedAppointmentDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPatientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PATIENT_FRAGMENT_BOOKED_APPOINTMENT_DETAILS));
        initMap();

        mFragmentBookedAppointmentDetailsBinding.closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });


    }
    private void initMap(){
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.chamberAddressMap);
        if(supportMapFragment!=null){
            supportMapFragment.getMapAsync(onMapReadyCallback);
        }
    }
    private void initViewModel(){
        if(mPatientHomeViewModel==null)
            mPatientHomeViewModel = new ViewModelProvider(getActivity())
                    .get(PatientHomeViewModel.class);
    }



    private Appointment getCurrentSelectedAppointment(){
        return mPatientHomeViewModel.getPatientBookedAppointmentList().getValue().get(mPatientHomeViewModel.selectedAppointmentFromBookedAppointmentPage);
    }
    private String getChamberName(){
        return getCurrentSelectedAppointment().getAppointmentVisitingSchedule().getVisitingScheduleChamber().getChamberUser().getUserName();
    }
    private LatLng getChamberLocation(){
        return new LatLng(getChamberLatitude(),getChamberLongitude());
    }
    private Double getChamberLongitude(){
        return Double.parseDouble(getCurrentSelectedAppointment().getAppointmentVisitingSchedule().getVisitingScheduleChamber().getChamberLocation().getLocationLongitude());
    }
    private Double getChamberLatitude(){
        return Double.parseDouble(getCurrentSelectedAppointment().getAppointmentVisitingSchedule().getVisitingScheduleChamber().getChamberLocation().getLocationLatitude());
    }

}