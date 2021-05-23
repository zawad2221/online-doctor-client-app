package com.example.onlinedoctor.patient_report.api;

import dagger.Component;

@Component(modules = {ReportApiModule.class})
public interface ReportApiComponent {
    ReportApi getReportApi();
}
