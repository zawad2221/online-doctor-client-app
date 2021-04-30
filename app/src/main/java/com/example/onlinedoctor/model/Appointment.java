package com.example.onlinedoctor.model;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.util.HashMap;
import java.util.Map;

import com.example.onlinedoctor.DateAndTime;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "appointmentId",
        "appointmentPaymentCredential",
        "patientSymptomNote",
        "appointmentTime",
        "appointmentIsConfirmed",
        "appointmentIsCanceled",
        "appointmentIsVisited",
        "appointmentDate",
        "appointmentSerialNumber",
        "appointmentVisitingSchedule",
        "appointmentPatient"
})
public class Appointment {

    @JsonProperty("appointmentId")
    private Integer appointmentId;
    @JsonProperty("appointmentPaymentCredential")
    private String appointmentPaymentCredential;
    @JsonProperty("patientSymptomNote")
    private String patientSymptomNote;
    @JsonProperty("appointmentTime")
    private String appointmentTime;
    @JsonProperty("appointmentIsConfirmed")
    private Boolean appointmentIsConfirmed;
    @JsonProperty("appointmentIsCanceled")
    private Boolean appointmentIsCanceled;
    @JsonProperty("appointmentIsVisited")
    private Boolean appointmentIsVisited;
    @JsonProperty("appointmentDate")
    private String appointmentDate;
    @JsonProperty("appointmentSerialNumber")
    private Integer appointmentSerialNumber;
    @JsonProperty("appointmentVisitingSchedule")
    private VisitingSchedule appointmentVisitingSchedule;
    @JsonProperty("appointmentPatient")
    private Patient appointmentPatient;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonProperty("appointmentId")
    public Integer getAppointmentId() {
        return appointmentId;

    }

    @JsonProperty("appointmentId")
    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    @JsonProperty("appointmentPaymentCredential")
    public String getAppointmentPaymentCredential() {
        return appointmentPaymentCredential;
    }

    @JsonProperty("appointmentPaymentCredential")
    public void setAppointmentPaymentCredential(String appointmentPaymentCredential) {
        this.appointmentPaymentCredential = appointmentPaymentCredential;
    }

    @JsonProperty("patientSymptomNote")
    public String getPatientSymptomNote() {
        return patientSymptomNote;
    }

    @JsonProperty("patientSymptomNote")
    public void setPatientSymptomNote(String patientSymptomNote) {
        this.patientSymptomNote = patientSymptomNote;
    }

    @JsonProperty("appointmentTime")
    public String getAppointmentTime() {
        return appointmentTime;
    }

    @JsonProperty("appointmentTime")
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    @JsonProperty("appointmentIsConfirmed")
    public Boolean getAppointmentIsConfirmed() {
        return appointmentIsConfirmed;
    }

    @JsonProperty("appointmentIsConfirmed")
    public void setAppointmentIsConfirmed(Boolean appointmentIsConfirmed) {
        this.appointmentIsConfirmed = appointmentIsConfirmed;
    }

    @JsonProperty("appointmentIsCanceled")
    public Boolean getAppointmentIsCanceled() {
        return appointmentIsCanceled;
    }

    @JsonProperty("appointmentIsCanceled")
    public void setAppointmentIsCanceled(Boolean appointmentIsCanceled) {
        this.appointmentIsCanceled = appointmentIsCanceled;
    }

    @JsonProperty("appointmentIsVisited")
    public Boolean getAppointmentIsVisited() {
        return appointmentIsVisited;
    }

    @JsonProperty("appointmentIsVisited")
    public void setAppointmentIsVisited(Boolean appointmentIsVisited) {
        this.appointmentIsVisited = appointmentIsVisited;
    }

    @JsonProperty("appointmentDate")
    public String getAppointmentDate() {
        return appointmentDate;
    }

    @JsonProperty("appointmentDate")
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @JsonProperty("appointmentSerialNumber")
    public Integer getAppointmentSerialNumber() {
        return appointmentSerialNumber;
    }

    @JsonProperty("appointmentSerialNumber")
    public void setAppointmentSerialNumber(Integer appointmentSerialNumber) {
        this.appointmentSerialNumber = appointmentSerialNumber;
    }

    @JsonProperty("appointmentVisitingSchedule")
    public VisitingSchedule getAppointmentVisitingSchedule() {
        return appointmentVisitingSchedule;
    }

    @JsonProperty("appointmentVisitingSchedule")
    public void setAppointmentVisitingSchedule(VisitingSchedule appointmentVisitingSchedule) {
        this.appointmentVisitingSchedule = appointmentVisitingSchedule;
    }

    @JsonProperty("appointmentPatient")
    public Patient getAppointmentPatient() {
        return appointmentPatient;
    }

    @JsonProperty("appointmentPatient")
    public void setAppointmentPatient(Patient appointmentPatient) {
        this.appointmentPatient = appointmentPatient;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @BindingAdapter("android:setTimeIn12Format")
    public static void setAppointmentTimeInTextViewView(TextView textView, String timeIn24Format){
        String timeIn12Format = DateAndTime.convert24to12(timeIn24Format);
        textView.setText(timeIn12Format);
    }

}