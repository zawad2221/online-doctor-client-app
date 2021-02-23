package com.example.onlinedoctor.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Patient {
    @JsonProperty("patientId")
    private Integer patientId;
    @JsonProperty("patientNid")
    private String patientNid;
    @JsonProperty("patientGender")
    private String patientGender;
    @JsonProperty("patientBloodGroup")
    private String patientBloodGroup;
    @JsonProperty("patientDateOfBirth")
    private String patientDateOfBirth;
    @JsonProperty("User")
    private User patientUser;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("patientId")
    public Integer getPatientId() {
        return patientId;
    }

    @JsonProperty("patientId")
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    @JsonProperty("patientNid")
    public String getPatientNid() {
        return patientNid;
    }

    @JsonProperty("patientNid")
    public void setPatientNid(String patientNid) {
        this.patientNid = patientNid;
    }

    @JsonProperty("patientGender")
    public String getPatientGender() {
        return patientGender;
    }

    @JsonProperty("patientGender")
    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    @JsonProperty("patientBloodGroup")
    public String getPatientBloodGroup() {
        return patientBloodGroup;
    }

    @JsonProperty("patientBloodGroup")
    public void setPatientBloodGroup(String patientBloodGroup) {
        this.patientBloodGroup = patientBloodGroup;
    }

    @JsonProperty("patientDateOfBirth")
    public String getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    @JsonProperty("patientDateOfBirth")
    public void setPatientDateOfBirth(String patientDateOfBirth) {
        this.patientDateOfBirth = patientDateOfBirth;
    }

    @JsonProperty("User")
    public User getPatientUser() {
        return patientUser;
    }

    @JsonProperty("User")
    public void setPatientUser(User patientUser) {
        this.patientUser = patientUser;
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
