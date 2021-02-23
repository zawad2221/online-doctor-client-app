package com.example.onlinedoctor.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Specialization {
    @JsonProperty("specializationId")
    private Integer specializationId;
    @JsonProperty("specializationName")
    private String specializationName;
    @JsonProperty("specializationDetail")
    private String specializationDetail;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Specialization(Integer specializationId, String specializationName) {
        this.specializationId = specializationId;
        this.specializationName = specializationName;
    }

    @JsonProperty("specializationId")
    public Integer getSpecializationId() {
        return specializationId;
    }

    @JsonProperty("specializationId")
    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    @JsonProperty("specializationName")
    public String getSpecializationName() {
        return specializationName;
    }

    @JsonProperty("specializationName")
    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    @JsonProperty("specializationDetail")
    public String getSpecializationDetail() {
        return specializationDetail;
    }

    @JsonProperty("specializationDetail")
    public void setSpecializationDetail(String specializationDetail) {
        this.specializationDetail = specializationDetail;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    public String toString(){
        return specializationName;
    }
}
