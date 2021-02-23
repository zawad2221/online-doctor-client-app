package com.example.onlinedoctor.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Doctor {
    @JsonProperty("doctorId")
    private Integer doctorId;
    @JsonProperty("doctorBmdcId")
    private String doctorBmdcId;
    @JsonProperty("doctorDesignation")
    private String doctorDesignation;
    @JsonProperty("doctorSpecialization")
    private Specialization doctorSpecialization;
    @JsonProperty("doctorUser")
    private User doctorUser;
    @JsonProperty("response")
    private String response;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("doctorId")
    public Integer getDoctorId() {
        return doctorId;
    }

    @JsonProperty("doctorId")
    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    @JsonProperty("doctorBmdcId")
    public String getDoctorBmdcId() {
        return doctorBmdcId;
    }

    @JsonProperty("doctorBmdcId")
    public void setDoctorBmdcId(String doctorBmdcId) {
        this.doctorBmdcId = doctorBmdcId;
    }

    @JsonProperty("doctorDesignation")
    public String getDoctorDesignation() {
        return doctorDesignation;
    }

    @JsonProperty("doctorDesignation")
    public void setDoctorDesignation(String doctorDesignation) {
        this.doctorDesignation = doctorDesignation;
    }

    @JsonProperty("doctorSpecialization")
    public Specialization getDoctorSpecialization() {
        return doctorSpecialization;
    }

    @JsonProperty("doctorSpecialization")
    public void setDoctorSpecialization(Specialization doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    @JsonProperty("doctorUser")
    public User getDoctorUser() {
        return doctorUser;
    }

    @JsonProperty("doctorUser")
    public void setDoctorUser(User doctorUser) {
        this.doctorUser = doctorUser;
    }

    @JsonProperty("response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(String response) {
        this.response = response;
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
