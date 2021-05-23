package com.example.onlinedoctor.patient.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.FragmentPatientReportListBinding;
import com.example.onlinedoctor.model.TestReport;
import com.example.onlinedoctor.model.User;
import com.example.onlinedoctor.patient.activity.ReportFileWebViewActivity;
import com.example.onlinedoctor.patient.view_model.PatientHomeViewModel;
import com.example.onlinedoctor.patient_report.adapter.TestReportRecyclerAdapter;
import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;


public class PatientReportListFragment extends Fragment {
    private final int MY_PERMISSION_REQUEST=100;
    PatientHomeViewModel mPatientHomeViewModel;
    FragmentPatientReportListBinding fragmentPatientReportListBinding;
    TestReportRecyclerAdapter testReportRecyclerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPatientReportListBinding = FragmentPatientReportListBinding
                .inflate(inflater,container,false);
        return fragmentPatientReportListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initReportList();
        reportLiveDataObserver();
        reportFilterListener();

        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST);
        }

    }

    private void reportFilterListener(){
        fragmentPatientReportListBinding.reportFilterChipGroup.setOnCheckedChangeListener((ChipGroup group, int checkedId) -> {
            switch (checkedId){
                case R.id.reportFilterDone:
                    initDoneReportList();
                    reportLiveDataObserver();
                    break;
                case R.id.reportFilterNotDone:
                    initNotReportList();
                    reportLiveDataObserver();
                    break;
                case R.id.reportFilterAll:
                    initReportList();
                    reportLiveDataObserver();
                    break;
                default:
                    initReportList();
                    reportLiveDataObserver();
                    fragmentPatientReportListBinding.reportFilterAll.setChecked(true);
                    break;

            }
        });
    }

    private void initViewModel(){
        if(mPatientHomeViewModel==null) mPatientHomeViewModel = new ViewModelProvider(getActivity())
                .get(PatientHomeViewModel.class);
    }
    private void iniReportAdapter(){
        testReportRecyclerAdapter = new TestReportRecyclerAdapter(
                mPatientHomeViewModel.getPatientTestReportList().getValue(),
                new TestReportRecyclerAdapter.OnClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        testReportRecyclerItemClick(position);


                    }
                });
    }

    private void testReportRecyclerItemClick(int position) {
        //Toast.makeText(getContext(),"clicked item"+position,Toast.LENGTH_LONG).show();
        if(mPatientHomeViewModel.getPatientTestReportList().getValue().get(position).getIsDone()){
//            mPatientHomeViewModel.downloadReportFile(getContext(),mPatientHomeViewModel.getPatientTestReportList().getValue().get(position).getTestReportId());
//            reportDownloadObserver();
            //
        }
        else {
            Toast.makeText(this.getActivity(),"Report not done yet.",Toast.LENGTH_LONG).show();
        }


    }

    private void reportDownloadObserver() {
        mPatientHomeViewModel.getTestReportDownloadResponse().observe(getViewLifecycleOwner(), new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                TestReport.responseBody = responseBody;
                startActivity(new Intent(getActivity(), ReportFileWebViewActivity.class));
            }
        });
    }
    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "sample.pdf"
            );

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(getString(R.string.DEBUGING_TAG), "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    private void iniRecyclerView(){
        iniReportAdapter();
        fragmentPatientReportListBinding.patientTestReportRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentPatientReportListBinding.patientTestReportRecyclerView.setHasFixedSize(true);
        fragmentPatientReportListBinding.patientTestReportRecyclerView.setAdapter(testReportRecyclerAdapter);
    }
    private void initReportList(){
        mPatientHomeViewModel.getPatientReportByPatientUserId(getContext(), User.loginUser.getUserId());
    }
    private void initDoneReportList(){
        mPatientHomeViewModel.getDoneReportByPatientUserId(getContext(), User.loginUser.getUserId());
    }
    private void initNotReportList(){
        mPatientHomeViewModel.getNotReportByPatientUserId(getContext(), User.loginUser.getUserId());
    }
    private void reportLiveDataObserver(){
        mPatientHomeViewModel.getPatientTestReportList().observe(getViewLifecycleOwner(), new Observer<List<TestReport>>() {
            @Override
            public void onChanged(List<TestReport> testReports) {
                iniRecyclerView();
                Log.d(getString(R.string.DEBUGING_TAG),"got report list: "+testReports.size());
            }
        });
    }
}