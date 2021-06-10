package com.example.onlinedoctor.visiting_schedule;

import dagger.Component;

@Component(modules = {VisitingScheduleApiModule.class})
public interface VisitingScheduleApiComponent {
    public VisitingScheduleApi getVisitingScheduleApi();
}
