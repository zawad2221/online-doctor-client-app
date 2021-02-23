package com.example.onlinedoctor.registration.fragment;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.registration.view_model.RegisterViewModel;
import com.example.onlinedoctor.registration.view_model.RegisterViewModelFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SelectLocationFormFragment extends Fragment{
    private String DEBUGING_TAG = "DEBUGING_TAG";
    private RegisterViewModel mRegisterViewModel;

    private GoogleMap mMap;

    private ImageView mImageViewMarker;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {


            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.setOnCameraIdleListener(cameraIdleListener);
            googleMap.setOnCameraMoveListener(cameraMoveListener);

            mMap = googleMap;
            if(mRegisterViewModel.getSelectedUser()==RegisterViewModel.AllUserType.CHAMBER){
                saveChamberLocationInViewModel();
            }
            if(mRegisterViewModel.getSelectedUser()==RegisterViewModel.AllUserType.PATHOLOGY){
                savePathologyLocationInViewModel();
            }

        }
    };

    private GoogleMap.OnCameraMoveListener cameraMoveListener = new GoogleMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {
            mMap.clear();
            mImageViewMarker.setVisibility(View.VISIBLE);
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
            mImageViewMarker.setVisibility(View.GONE);
            MarkerOptions markerOption = new MarkerOptions()
                    .position(mMap.getCameraPosition().target)
                    .icon(bitmapDescriptorFromVector(getActivity().getApplicationContext(),R.drawable.location_picker));
            mMap.addMarker(markerOption);
            if(mRegisterViewModel.getSelectedUser()==RegisterViewModel.AllUserType.CHAMBER){
                saveChamberLocationInViewModel();
            }
            if(mRegisterViewModel.getSelectedUser()==RegisterViewModel.AllUserType.PATHOLOGY){
                savePathologyLocationInViewModel();
            }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_location_form, container, false);
        mImageViewMarker = view.findViewById(R.id.imgLocationPinUp);
        initViewModel();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.selectLocationMap);
        if (mapFragment!=null)
            mapFragment.getMapAsync(callback);


    }
    private void saveChamberLocationInViewModel(){
        mRegisterViewModel.setChamberLocationLatLan(mMap.getCameraPosition().target);
    }
    private void savePathologyLocationInViewModel(){
        mRegisterViewModel.setPathologyLocationLatLan(mMap.getCameraPosition().target);
    }
    private void initViewModel(){
        if(mRegisterViewModel==null)
            mRegisterViewModel = new ViewModelProvider(this.getActivity(),
                    new RegisterViewModelFactory(this.getActivity().getApplication(),"test"))
                    .get(RegisterViewModel.class);
    }
}