package com.example.onlinedoctor.patient.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientHomeBinding;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.patient.adapter.SpecializationSearchRecyclerViewAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModelFactory;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class PatientHomeFragment extends Fragment {
    private String DEBUGING_TAG="DEBUGING_TAG";

    private PatientHomeViewModel mPatientHomeViewModel;
    private SpecializationSearchRecyclerViewAdapter mSpecializationSearchRecyclerViewAdapter;
    FragmentPatientHomeBinding mFragmentPatientHomeBinding;
    private GoogleMap gMap;
    private SupportMapFragment supportMapFragment;

    private GoogleMap mMap;



    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {


            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.setOnCameraIdleListener(cameraIdleListener);
            googleMap.setOnCameraMoveListener(cameraMoveListener);

            mMap = googleMap;


        }
    };
    private GoogleMap.OnCameraMoveListener cameraMoveListener = new GoogleMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {
            mMap.clear();
//            mImageViewMarker.setVisibility(View.VISIBLE);
            Log.d(DEBUGING_TAG,"camera moving");

        }
    };
    private GoogleMap.OnCameraIdleListener cameraIdleListener = new GoogleMap.OnCameraIdleListener() {
        private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int locationPickerId) {
            Drawable background = ContextCompat.getDrawable(context, locationPickerId);
            background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
            //Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
            //vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
            Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            background.draw(canvas);
            //vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }
        @Override
        public void onCameraIdle() {
//            mImageViewMarker.setVisibility(View.GONE);
//            MarkerOptions markerOption = new MarkerOptions()
//                    .position(mMap.getCameraPosition().target)
//                    .icon(bitmapDescriptorFromVector(getActivity().getApplicationContext(),R.drawable.location_picker));
//            mMap.addMarker(markerOption);
//            if(mRegisterViewModel.getSelectedUser()==RegisterViewModel.AllUserType.CHAMBER){
//                saveChamberLocationInViewModel();
//            }
//            if(mRegisterViewModel.getSelectedUser()==RegisterViewModel.AllUserType.PATHOLOGY){
//                savePathologyLocationInViewModel();
//            }

            Log.d(DEBUGING_TAG,"camera idle");
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentPatientHomeBinding = FragmentPatientHomeBinding.inflate(inflater, container, false);
        View view = mFragmentPatientHomeBinding.getRoot();
        initViewModel();
        initRecyclerView();


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMap();
    }

    private void initRecyclerView() {
        
        List<Specialization> specializationArrayList = new ArrayList<>();
        Specialization specialization=new Specialization(1,"Medicine");
        Specialization specialization1=new Specialization(1,"Cardiologist");
        Specialization specialization2=new Specialization(1,"Neuro");
        Specialization specialization3=new Specialization(1,"Pharmacy");
        Specialization specialization4=new Specialization(1,"Pharmacy");
        Specialization specialization5=new Specialization(1,"Pharmacy");

        specializationArrayList.add(specialization);
        specializationArrayList.add(specialization1);
        specializationArrayList.add(specialization2);
        specializationArrayList.add(specialization3);
        specializationArrayList.add(specialization4);
        specializationArrayList.add(specialization5);
        mSpecializationSearchRecyclerViewAdapter=new SpecializationSearchRecyclerViewAdapter(specializationArrayList, new SpecializationSearchRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

        });
        mFragmentPatientHomeBinding.specializationSearchRecyclerView.setHasFixedSize(true);
        mFragmentPatientHomeBinding.specializationSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mFragmentPatientHomeBinding.specializationSearchRecyclerView.setAdapter(mSpecializationSearchRecyclerViewAdapter);

    }

    private void initMap(){
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        if(supportMapFragment!=null){
            supportMapFragment.getMapAsync(callback);
        }
    }
    private void initViewModel() {
        mPatientHomeViewModel = new ViewModelProvider(
                this.getActivity(),
                new PatientHomeViewModelFactory(this.getActivity().getApplication(), "test")
        )
                .get(PatientHomeViewModel.class);
    }



}