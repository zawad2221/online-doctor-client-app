package com.example.onlinedoctor.doctor.api;

import dagger.Component;

@Component(modules = {DoctorApiModule.class})
public interface DoctorApiComponent {
    DoctorApi getDoctorApi();
}
