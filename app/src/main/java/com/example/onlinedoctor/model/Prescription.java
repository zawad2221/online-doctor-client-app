package com.example.onlinedoctor.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "prescriptionId",
        "appointment",
        "suggestion",
        "patientHistory",
        "prescribedMedicine"
})
public class Prescription {

    @JsonProperty("prescriptionId")
    private Integer prescriptionId;
    @JsonProperty("appointment")
    private Appointment appointment;
    @JsonProperty("suggestion")
    private Suggestion suggestion;
    @JsonProperty("patientHistory")
    private PatientHistory patientHistory;
    @JsonProperty("prescribedMedicine")
    private List<PrescribedMedicine> prescribedMedicine = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("prescriptionId")
    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    @JsonProperty("prescriptionId")
    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @JsonProperty("appointment")
    public Appointment getAppointment() {
        return appointment;
    }

    @JsonProperty("appointment")
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @JsonProperty("suggestion")
    public Suggestion getSuggestion() {
        return suggestion;
    }

    @JsonProperty("suggestion")
    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    @JsonProperty("patientHistory")
    public PatientHistory getPatientHistory() {
        return patientHistory;
    }

    @JsonProperty("patientHistory")
    public void setPatientHistory(PatientHistory patientHistory) {
        this.patientHistory = patientHistory;
    }

    @JsonProperty("prescribedMedicine")
    public List<PrescribedMedicine> getPrescribedMedicine() {
        return prescribedMedicine;
    }

    @JsonProperty("prescribedMedicine")
    public void setPrescribedMedicine(List<PrescribedMedicine> prescribedMedicine) {
        this.prescribedMedicine = prescribedMedicine;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
