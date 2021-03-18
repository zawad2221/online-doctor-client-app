package com.example.onlinedoctor.model;

import java.util.HashMap;
        import java.util.Map;
        import com.fasterxml.jackson.annotation.JsonAnyGetter;
        import com.fasterxml.jackson.annotation.JsonAnySetter;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "visitingScheduleId",
        "startAt",
        "endAt",
        "maxPatient",
        "isCanceled",
        "isDeleted",
        "visitingScheduleFee",
        "visitingScheduleDaysOfWeek",
        "visitingScheduleDoctor",
        "visitingScheduleChamber"
})
public class VisitingSchedule {

    @JsonProperty("visitingScheduleId")
    private Integer visitingScheduleId;
    @JsonProperty("startAt")
    private String startAt;
    @JsonProperty("endAt")
    private String endAt;
    @JsonProperty("maxPatient")
    private Integer maxPatient;
    @JsonProperty("isCanceled")
    private Boolean isCanceled;
    @JsonProperty("isDeleted")
    private Boolean isDeleted;
    @JsonProperty("visitingScheduleFee")
    private VisitingScheduleFee visitingScheduleFee;
    @JsonProperty("visitingScheduleDaysOfWeek")
    private DaysOfWeek visitingScheduleDaysOfWeek;
    @JsonProperty("visitingScheduleDoctor")
    private Doctor visitingScheduleDoctor;
    @JsonProperty("visitingScheduleChamber")
    private Chamber visitingScheduleChamber;
    private int numberOfPatientBooked;
    private String appointmentDate;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("visitingScheduleId")
    public Integer getVisitingScheduleId() {
        return visitingScheduleId;
    }

    @JsonProperty("visitingScheduleId")
    public void setVisitingScheduleId(Integer visitingScheduleId) {
        this.visitingScheduleId = visitingScheduleId;
    }

    @JsonProperty("startAt")
    public String getStartAt() {
        return startAt;
    }

    @JsonProperty("startAt")
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    @JsonProperty("endAt")
    public String getEndAt() {
        return endAt;
    }

    @JsonProperty("endAt")
    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    @JsonProperty("maxPatient")
    public Integer getMaxPatient() {
        return maxPatient;
    }

    @JsonProperty("maxPatient")
    public void setMaxPatient(Integer maxPatient) {
        this.maxPatient = maxPatient;
    }

    @JsonProperty("isCanceled")
    public Boolean getIsCanceled() {
        return isCanceled;
    }

    @JsonProperty("isCanceled")
    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    @JsonProperty("isDeleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @JsonProperty("isDeleted")
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonProperty("visitingScheduleFee")
    public VisitingScheduleFee getVisitingScheduleFee() {
        return visitingScheduleFee;
    }

    @JsonProperty("visitingScheduleFee")
    public void setVisitingScheduleFee(VisitingScheduleFee visitingScheduleFee) {
        this.visitingScheduleFee = visitingScheduleFee;
    }

    @JsonProperty("visitingScheduleDaysOfWeek")
    public DaysOfWeek getVisitingScheduleDaysOfWeek() {
        return visitingScheduleDaysOfWeek;
    }

    @JsonProperty("visitingScheduleDaysOfWeek")
    public void setVisitingScheduleDaysOfWeek(DaysOfWeek visitingScheduleDaysOfWeek) {
        this.visitingScheduleDaysOfWeek = visitingScheduleDaysOfWeek;
    }

    @JsonProperty("visitingScheduleDoctor")
    public Doctor getVisitingScheduleDoctor() {
        return visitingScheduleDoctor;
    }

    @JsonProperty("visitingScheduleDoctor")
    public void setVisitingScheduleDoctor(Doctor visitingScheduleDoctor) {
        this.visitingScheduleDoctor = visitingScheduleDoctor;
    }

    @JsonProperty("visitingScheduleChamber")
    public Chamber getVisitingScheduleChamber() {
        return visitingScheduleChamber;
    }

    @JsonProperty("visitingScheduleChamber")
    public void setVisitingScheduleChamber(Chamber visitingScheduleChamber) {
        this.visitingScheduleChamber = visitingScheduleChamber;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public int getNumberOfPatientBooked() {
        return numberOfPatientBooked;
    }

    public void setNumberOfPatientBooked(int numberOfPatientBooked) {
        this.numberOfPatientBooked = numberOfPatientBooked;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}