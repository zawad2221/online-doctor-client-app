package com.example.onlinedoctor.appointment;

import dagger.Component;

@Component(modules = {AppointmentApiModule.class})
public interface AppointmentApiComponent {
    AppointmentApi getAppointmentApi();
}
