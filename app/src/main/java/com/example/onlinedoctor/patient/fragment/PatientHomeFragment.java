package com.example.onlinedoctor.patient.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientHomeBinding;
import com.example.onlinedoctor.login.LoginActivity;
import com.example.onlinedoctor.model.Chamber;
import com.example.onlinedoctor.model.Specialization;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.ChamberVisitingScheduleForPatientActivity;
import com.example.onlinedoctor.patient.MainActivity;
import com.example.onlinedoctor.patient.adapter.SpecializationSearchRecyclerViewAdapter;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModelFactory;
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
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PatientHomeFragment extends Fragment {
    private String DEBUGING_TAG="DEBUGING_TAG";

    private PatientHomeViewModel mPatientHomeViewModel;
    private SpecializationSearchRecyclerViewAdapter mSpecializationSearchRecyclerViewAdapter;
    FragmentPatientHomeBinding mFragmentPatientHomeBinding;

    private SupportMapFragment supportMapFragment;

    private GoogleMap mMap;

    private NavController mNavController;
    private ProgressDialog progressDialog;

    private ProgressDialog loadingSpecializationDataProgressDialog;



    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {


            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(getLastKnownLocation()));
            googleMap.setOnMarkerClickListener(onMarkerClickListener);
            //googleMap.setOnCameraIdleListener(cameraIdleListener);
            //googleMap.setOnCameraMoveListener(cameraMoveListener);

            mMap = googleMap;
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.d(DEBUGING_TAG,"location: "+latLng);

                }
            });




        }
    };

    private GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            int chamberIndex = Integer.parseInt(marker.getTag().toString());
            Log.d(DEBUGING_TAG,"chamber index: "+chamberIndex);

            //mPatientHomeViewModel.selectedChamberId.setValue(Integer.parseInt(chamberId));
            Intent visitingScheduleIntent = new Intent(getActivity(), ChamberVisitingScheduleForPatientActivity.class);
            visitingScheduleIntent.putExtra(
                    getActivity().getString(R.string.chamber_visiting_schedule_activity_chamber_id_extra_value),
                    mPatientHomeViewModel.getChamberList().getValue().get(chamberIndex).getChamberId().toString()
            );
            visitingScheduleIntent.putExtra(
                    getActivity().getString(R.string.chamber_visiting_schedule_activity_chamber_name_extra_value),
                    mPatientHomeViewModel.getChamberList().getValue().get(chamberIndex).getChamberUser().getUserName()
            );
            visitingScheduleIntent.putExtra(
                    getActivity().getString(R.string.chamber_visiting_schedule_activity_chamber_address_extra_value),
                    mPatientHomeViewModel.getChamberList().getValue().get(chamberIndex).getChamberLocation().getLocationAdderssDetail()
            );
            startActivity(visitingScheduleIntent);
            return false;
        }
    };



    private GoogleMap.OnCameraMoveListener cameraMoveListener = new GoogleMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {
            //mMap.clear();
//            mImageViewMarker.setVisibility(View.VISIBLE);
            //Log.d(DEBUGING_TAG,"camera moving");

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

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleOnBackPressed() {
                Log.d(DEBUGING_TAG,"back button click");
                Log.d(DEBUGING_TAG,"back button click event :"+mFragmentPatientHomeBinding.searchView.isFocused());
                if(!mFragmentPatientHomeBinding.searchView.isIconified()){
                    mFragmentPatientHomeBinding.searchView.clearFocus();
                    setQueryOnSearchView(null,false);
                    setSearchViewIconifiedState(true);
                    changeSpecializationRecyclerViewVisibility(View.VISIBLE);
                    clearMap();
                }
                else {
                    closeFragment();
                }




            }
            private void closeFragment() {
                // Disable to close fragment
                this.setEnabled(false);
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentPatientHomeBinding = FragmentPatientHomeBinding.inflate(inflater, container, false);
        View view = mFragmentPatientHomeBinding.getRoot();
        initViewModel();
        setSelectedFragmentInViewModel();
        mPatientHomeViewModel.initSpecialization();
        showLoadingSpecializationDataProgressDialog();
        initRecyclerView();
        specializationLiveDataObserver();
        searchViewOnClick();
        searchViewOnClose();


        return view;

    }

    private void searchViewOnClick(){
        mFragmentPatientHomeBinding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUGING_TAG,"search view click");
                setSearchViewIconifiedState(false);
            }
        });
    }



    private void searchViewOnClose(){
        mFragmentPatientHomeBinding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                changeSpecializationRecyclerViewVisibility(View.VISIBLE);

                Log.d(DEBUGING_TAG,"search view close");
                clearMap();
                initRecyclerView();
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMap();
        searchViewOnQueryTextListener();
        mNavController = Navigation.findNavController(this.getView());
        onProfileClick();

    }
    private void setSelectedFragmentInViewModel() {
        mPatientHomeViewModel.bottomNavSelectedItem.setValue(getString(R.string.PATIENT_FRAGMENT_HOME));
    }

    private void specializationLiveDataObserver(){
        mPatientHomeViewModel.getSpecializationList().observe(this.getActivity(), new Observer<List<Specialization>>() {
            @Override
            public void onChanged(List<Specialization> specializations) {
                initRecyclerView();
                dismissLoadingSpecializationDataProgressDialog();
                Log.d(DEBUGING_TAG,"data from change: "+specializations.size());
                Log.d(DEBUGING_TAG,"data from change view model: "+mPatientHomeViewModel.getSpecializationList().getValue().size());

            }
        });
    }

    private void chamberLiveDataObserver(){
        mPatientHomeViewModel.getChamberList().observe(this.getActivity(), new Observer<List<Chamber>>() {
            @Override
            public void onChanged(List<Chamber> chambers) {
                Log.d(DEBUGING_TAG,"loaded chamber data: "+ chambers.get(0).getChamberUser().getUserName());
                addChamberMarkerInMap();

//                MarkerOptions markerOption = new MarkerOptions()
//                        .position(new LatLng(-34, 151))
//                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.location_picker));
//                mMap.addMarker(markerOption);
            }
        });
    }



    private void initRecyclerView() {
        
//        List<Specialization> specializationArrayList = new ArrayList<>();
//        Specialization specialization=new Specialization(1,"Medicine");
//        Specialization specialization1=new Specialization(1,"Cardiologist");
//        Specialization specialization2=new Specialization(1,"Neuro");
//        Specialization specialization3=new Specialization(1,"Pharmacy");
//        Specialization specialization4=new Specialization(1,"Pharmacy");
//        Specialization specialization5=new Specialization(1,"Pharmacy");
//
//        specializationArrayList.add(specialization);
//        specializationArrayList.add(specialization1);
//        specializationArrayList.add(specialization2);
//        specializationArrayList.add(specialization3);
//        specializationArrayList.add(specialization4);
//        specializationArrayList.add(specialization5);
        mSpecializationSearchRecyclerViewAdapter=new SpecializationSearchRecyclerViewAdapter(mPatientHomeViewModel.getSpecializationList().getValue(), new SpecializationSearchRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(DEBUGING_TAG,"tap: "+position);
                specializationOnItemClick(position);
                //mFragmentPatientHomeBinding.searchView.clearFocus();
            }

        });
        mFragmentPatientHomeBinding.specializationSearchRecyclerView.setHasFixedSize(true);
        mFragmentPatientHomeBinding.specializationSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mFragmentPatientHomeBinding.specializationSearchRecyclerView.setAdapter(mSpecializationSearchRecyclerViewAdapter);

    }

    private void setQueryOnSearchView(String text, boolean submit){
        mFragmentPatientHomeBinding.searchView.setQuery(
                text,
                submit
        );
    }

    private void specializationOnItemClick(int position){
        String selectedSpecialization = mPatientHomeViewModel
                .getSpecializationList()
                .getValue()
                .get(position)
                .getSpecializationName();
        int selectedSpecializationId = mPatientHomeViewModel
                .getSpecializationList()
                .getValue()
                .get(position)
                .getSpecializationId();
        setQueryOnSearchView(
                selectedSpecialization,
                true
        );
        changeSpecializationRecyclerViewVisibility(View.GONE);
        setSearchViewIconifiedState(false);
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(12),2000,null);
        showLoadingProgressDialog();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getLastKnownLocation(),12.0f));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Double[][] cameraLocation = getCameraPositionLocation();//double 2d array, 0 contain latitude range(0 low, 1 upper), 1 contain longitude range(0 low, 1 upper)
                Log.d(DEBUGING_TAG,"camera position: "+cameraLocation);

                getAndObserveChamberList(selectedSpecializationId, cameraLocation);
                progressDialog.dismiss();
            }
        }, 3000);




    }

    private void showLoadingProgressDialog(){
        progressDialog = new ProgressDialog(getContext(),android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
    }

    private void getAndObserveChamberList(int specializationId, Double[][] locationRange){
        mPatientHomeViewModel.initChamber(specializationId, locationRange);
        chamberLiveDataObserver();
    }

    private void clearMap(){
        mMap.clear();
    }

    private void addChamberMarkerInMap(){
        mMap.clear();
        int index=0;
        for(Chamber chamber : mPatientHomeViewModel.getChamberList().getValue()){
            LatLng location = new LatLng(
                    Double.parseDouble(chamber.getChamberLocation().getLocationLatitude()),
                    Double.parseDouble(chamber.getChamberLocation().getLocationLongitude())
            );
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title(chamber.getChamberUser().getUserName());
            markerOptions.icon(
                    BitmapDescriptorFactory
                    .fromBitmap(createCustomMarker(getActivity(), chamber.getChamberUser().getUserName()))
            );




            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(index);
            Log.d(DEBUGING_TAG,"marker: "+marker.getPosition());
            index++;

        }
    }

    //return double 2d array, 0 contain latitude range(0 low, 1 upper), 1 contain longitude range(0 low, 1 upper)
    private Double[][] getCameraPositionLocation(){
        //get camera position location
        CameraPosition cameraPosition=mMap.getCameraPosition();
        float zoomLevel = cameraPosition.zoom;
        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        LatLng nearLeft = visibleRegion.nearLeft;
        LatLng nearRight = visibleRegion.nearRight;
        LatLng farLeft = visibleRegion.farLeft;
        LatLng farRight = visibleRegion.farRight;
        Log.d(DEBUGING_TAG,"camera move, \nnearLeft:"+nearLeft+" \nnearRigth: "+nearRight+" \nfarLeft: "
                +farLeft+" \nfarRight: "+farRight);
        LatLng latLng = new LatLng(nearLeft.latitude,farRight.longitude);

        Double[][] locationRange = new Double[2][];
        Double[] latRange = new Double[]{
                nearLeft.latitude,
                farLeft.latitude
        };
        Arrays.sort(latRange);
        Double[] lonRange = new Double[]{
                nearRight.longitude,
                farLeft.longitude
        };
        Arrays.sort(lonRange);
        locationRange[0] = latRange;
        locationRange[1] = lonRange;
        Log.d(DEBUGING_TAG,"range "+locationRange[0][1]);

        return locationRange;
    }

    private void setSearchViewIconifiedState(boolean state){
        mFragmentPatientHomeBinding.searchView.setIconified(state);
    }

    private void changeSpecializationRecyclerViewVisibility(int visibility){
        mFragmentPatientHomeBinding.specializationSearchRecyclerView.setVisibility(visibility);
    }

    private void searchViewOnQueryTextListener(){


        mFragmentPatientHomeBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(DEBUGING_TAG,"onQueryTextListener text submit");



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(DEBUGING_TAG,"onQueryTextListener text change");
                return false;
            }
        });
    }

    private void initMap(){
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        if(supportMapFragment!=null){
            supportMapFragment.getMapAsync(callback);
        }
    }
    private void initViewModel() {
        mPatientHomeViewModel = new ViewModelProvider
                (
                        this.getActivity(),
                        new PatientHomeViewModelFactory(this.getActivity().getApplication(), "test")
                )
                .get(PatientHomeViewModel.class);
    }

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

    public static Bitmap createCustomMarker(Context context,  String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_view_bubble, null);

//        ImageView markerImage = (ImageView) marker.findViewById(R.id.marker_icon);5
//
//        markerImage.setImageResource(resource);
        TextView txt_name = (TextView)marker.findViewById(R.id.marker_title);
        txt_name.setText(_name);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    private LatLng getLastKnownLocation(){
        try{
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(
                    getString(R.string.LAST_KNOWN_LOCATION_FILE_NAME),
                    MODE_PRIVATE
            );
            String latitude = sharedPreferences.getString(getString(R.string.LAST_KNOWN_LOCATION_LATITUDE_PREFERENCE_KEY),"null");
            String longitude = sharedPreferences.getString(getString(R.string.LAST_KNOWN_LOCATION_LONGITUDE_PREFERENCE_KEY),"null");
            LatLng lastKnownLocation = new LatLng(
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude)
            );
            return lastKnownLocation;
        }
        catch (Exception e){
            Log.d(DEBUGING_TAG,"patient home frag get location exception: "+e.getMessage());
            return new LatLng(27.2046, 77.4977);
        }

    }


    private void showLoadingSpecializationDataProgressDialog(){
        loadingSpecializationDataProgressDialog = new ProgressDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        loadingSpecializationDataProgressDialog.setIndeterminate(true);
        loadingSpecializationDataProgressDialog.setCancelable(false);
        loadingSpecializationDataProgressDialog.setMessage("Loading...");
        loadingSpecializationDataProgressDialog.show();
    }
    private void dismissLoadingSpecializationDataProgressDialog(){
        loadingSpecializationDataProgressDialog.dismiss();
    }
    private void onProfileClick(){
        mFragmentPatientHomeBinding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.loginUser==null || User.loginUser.getUserId()==null){
                    showLoginMenu(v,R.menu.login_menu);
                }
                else {
                    showLogoutMenu(v,R.menu.logout_menu);
                }

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

    private void showLoginMenu(View view, int menuRes){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        popupMenu.getMenuInflater().inflate(menuRes,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
            switch (item.getItemId()){
                case R.id.loginOption:
                    User.profileClick(getActivity());
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