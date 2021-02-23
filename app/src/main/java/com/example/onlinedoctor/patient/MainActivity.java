package com.example.onlinedoctor.patient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.onlinedoctor.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private static final int FINE_LOCATION_REQUEST_CODE = 1234;
    private static final String DEBUGING_TAG = "DEBUGING_TAG";
    private static final int REQUEST_CHECK_LOCATION_SETTINGS_CODE = 1111;
    private final String LOCATION_PERMISSION_PREFERENCE_FILE_NAME = "location_permission";
    private final String LOCATION_PERMISSION_PREFERENCE_KEY = "show_location_permission_dialog";

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Doctor doctor= new Doctor();
//        RegistrationRepository.registration(doctor);

//        initFusedLocationClient();
//        fineLocationPermission();
//        Log.d(DEBUGING_TAG,"latest location: "+getLatestLocation());
        //startActivity(new Intent(this, RegisterActivity.class));

    }

    private void initFusedLocationClient(){
        fusedLocationClient = getFusedLocationClient();
    }

    private FusedLocationProviderClient getFusedLocationClient(){
        return LocationServices.getFusedLocationProviderClient(this);
    }

    //check if the fine location permission is already granted
    private boolean isFineLocationPermissionGranted() {
        int fineLocationPermissionGrantedCode = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (fineLocationPermissionGrantedCode == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }



    //should show the dialog why permission needed
    private boolean shouldShowLocationPermissionRequestEducationalDialog() {

        return ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    //get location permission shared preference value
    private boolean getLocationPermissionSharedPreferenceValue() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(LOCATION_PERMISSION_PREFERENCE_FILE_NAME, MODE_PRIVATE);
            String locationPermissionPreferenceValue = sharedPreferences.getString(LOCATION_PERMISSION_PREFERENCE_KEY, "true");
            if (locationPermissionPreferenceValue.equals("false")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    //show the dialog why need permission
    private void showLocationPermissionRequestEducationalDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("We need location permission for better experience")
                .setCancelable(false)
                .setMultiChoiceItems(
                        new String[]{"Do not show again"},
                        new boolean[]{false},
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    saveMultiChoice();
                                }
                            }
                        })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showLocationPermissionRequestDialog();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //save if user don't want to see the location permission educational dialog again
    private void saveMultiChoice() {
        SharedPreferences sharedPreferences =
                MainActivity
                        .this
                        .getApplicationContext()
                        .getSharedPreferences(LOCATION_PERMISSION_PREFERENCE_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCATION_PERMISSION_PREFERENCE_KEY, "false");
        editor.apply();

    }

    private void showLocationPermissionRequestDialog() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_LOCATION_REQUEST_CODE);
        Log.d(DEBUGING_TAG,"show location permission dialog");
    }

    private void fineLocationPermission() {
        if (isFineLocationPermissionGranted()) {
            //permission is already granted
            Log.d(DEBUGING_TAG,"permission already granted");
            checkLocationSetting();

        } else {
            Log.d(DEBUGING_TAG,"permission wasn't granted");
            //permission wasn't granted

            if (getLocationPermissionSharedPreferenceValue()) {
                Log.d(DEBUGING_TAG,"shouldShowLocationPermissionRequestEducationalDialog: "+shouldShowLocationPermissionRequestEducationalDialog());
                if(shouldShowLocationPermissionRequestEducationalDialog()) {
                    showLocationPermissionRequestEducationalDialog();
                }
                else {
                    showLocationPermissionRequestDialog();
                }

            }

        }
    }



    private LatLng getLatestLocation() {
        final LatLng[] latestLocation = new LatLng[1];
        if (!isFineLocationPermissionGranted() && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        latestLocation[0] = new LatLng(location.getLatitude(),location.getLongitude());
                    }
                });
        return latestLocation[0];
    }
    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
    private void checkLocationSetting(){
        LocationRequest locationRequest = createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //get location

                Log.d(DEBUGING_TAG,"check location setting success : "+getLatestLocation().toString());
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    //location setting is not satisfy, can be fix by showing user a dialog
                    Log.d(DEBUGING_TAG,"check location failed ");
                    try{
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                        resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_LOCATION_SETTINGS_CODE);
                    }
                    catch (IntentSender.SendIntentException sendIntentException){
                        //ignore the error
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    checkLocationSetting();

                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CHECK_LOCATION_SETTINGS_CODE:
                Log.d(DEBUGING_TAG,"onActivityResult :"+resultCode+" "+data.getDataString());
        }
    }
}


