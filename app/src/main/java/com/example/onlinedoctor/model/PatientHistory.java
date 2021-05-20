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
        "patientHistoryId",
        "patientHistoryDetails",
        "patientId"
})
public class PatientHistory {

    @JsonProperty("patientHistoryId")
    private Integer patientHistoryId;
    @JsonProperty("patientHistoryDetails")
    private String patientHistoryDetails;
    @JsonProperty("patientId")
    private Integer patientId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("patientHistoryId")
    public Integer getPatientHistoryId() {
        return patientHistoryId;
    }

    @JsonProperty("patientHistoryId")
    public void setPatientHistoryId(Integer patientHistoryId) {
        this.patientHistoryId = patientHistoryId;
    }

    @JsonProperty("patientHistoryDetails")
    public String getPatientHistoryDetails() {
        return patientHistoryDetails;
    }

    @JsonProperty("patientHistoryDetails")
    public void setPatientHistoryDetails(String patientHistoryDetails) {
        this.patientHistoryDetails = patientHistoryDetails;
    }

    @JsonProperty("patientId")
    public Integer getPatientId() {
        return patientId;
    }

    @JsonProperty("patientId")
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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
