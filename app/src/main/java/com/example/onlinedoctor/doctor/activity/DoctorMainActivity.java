package com.example.onlinedoctor.doctor.activity;

import android.os.Bundle;

import com.example.onlinedoctor.databinding.ActivityDoctorMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;

import com.example.onlinedoctor.R;

public class DoctorMainActivity extends AppCompatActivity {
    ActivityDoctorMainBinding activityDoctorMainBinding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDoctorMainBinding = ActivityDoctorMainBinding.inflate(getLayoutInflater());
        setContentView(activityDoctorMainBinding.getRoot());
        initNavController();
        navControllerDestinationListener();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void navControllerDestinationListener(){
        navController.addOnDestinationChangedListener((NavController controller, NavDestination destination, Bundle arguments) -> {
            switch (destination.getId()){
                case R.id.doctorScheduleAppointmentFragment:
                    setVisibilityOfAppBar(View.GONE);
                    break;
                default:
                    setVisibilityOfAppBar(View.VISIBLE);
                    break;

            }
        });
    }
    private void setVisibilityOfAppBar(int visibility){
        activityDoctorMainBinding.appToolBar.setVisibility(visibility);
    }

    private void initNavController(){
        navController = ((NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.doctorHomeFragmentHolder)).getNavController();
    }
}