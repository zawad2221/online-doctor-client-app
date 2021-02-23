package com.example.onlinedoctor.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private Integer locationId;
    @JsonProperty("locationAdderssDetail")
    private String locationAdderssDetail;
    @JsonProperty("locationLongitude")
    private String locationLongitude;
    @JsonProperty("locationLatitude")
    private String locationLatitude;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("locationId")
    public Integer getLocationId() {
        return locationId;
    }

    @JsonProperty("locationId")
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @JsonProperty("locationAdderssDetail")
    public String getLocationAdderssDetail() {
        return locationAdderssDetail;
    }

    @JsonProperty("locationAdderssDetail")
    public void setLocationAdderssDetail(String locationAdderssDetail) {
        this.locationAdderssDetail = locationAdderssDetail;
    }

    @JsonProperty("locationLongitude")
    public String getLocationLongitude() {
        return locationLongitude;
    }

    @JsonProperty("locationLongitude")
    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    @JsonProperty("locationLatitude")
    public String getLocationLatitude() {
        return locationLatitude;
    }

    @JsonProperty("locationLatitude")
    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
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
