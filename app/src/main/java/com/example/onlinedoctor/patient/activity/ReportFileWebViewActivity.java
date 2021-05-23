package com.example.onlinedoctor.patient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.example.onlinedoctor.R;
import com.example.onlinedoctor.databinding.ActivityReportFileWebViewBinding;
import com.example.onlinedoctor.model.TestReport;

public class ReportFileWebViewActivity extends AppCompatActivity {
    ActivityReportFileWebViewBinding activityReportFileWebViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReportFileWebViewBinding = ActivityReportFileWebViewBinding.inflate(getLayoutInflater());

        setContentView(activityReportFileWebViewBinding.getRoot());
        iniWebView();

    }
    private void iniWebView(){
        WebSettings webSettings = activityReportFileWebViewBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        activityReportFileWebViewBinding.webView.loadUrl(
                        TestReport.responseBody.toString()
        );


    }
    private String getUrlFromIntent(){
        Intent intent = getIntent();
        String url = "";
        try{
            url = intent.getStringExtra(getString(R.string.report_file_url_intent_exrta_tag));
        }
        catch (Exception e){

        }
        return url;
    }
}