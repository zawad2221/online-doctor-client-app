package com.example.onlinedoctor.prescription.api;



import dagger.Component;

@Component(modules = {PrescriptionApiModule.class})
public interface PrescriptionApiComponent {
    PrescriptionApi getPrescriptionApi();
}
