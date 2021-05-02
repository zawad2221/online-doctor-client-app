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
        "queryId",
        "queryDetails",
        "patient"
})

public class PatientQuery {

    @JsonProperty("queryId")
    private Integer queryId;
    @JsonProperty("queryDetails")
    private String queryDetails;
    @JsonProperty("patient")
    private Patient patient;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("queryId")
    public Integer getQueryId() {
        return queryId;
    }

    @JsonProperty("queryId")
    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    @JsonProperty("queryDetails")
    public String getQueryDetails() {
        return queryDetails;
    }

    @JsonProperty("queryDetails")
    public void setQueryDetails(String queryDetails) {
        this.queryDetails = queryDetails;
    }

    @JsonProperty("patient")
    public Patient getPatient() {
        return patient;
    }

    @JsonProperty("patient")
    public void setPatient(Patient patient) {
        this.patient = patient;
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
