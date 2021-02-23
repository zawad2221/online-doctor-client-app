package com.example.onlinedoctor.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Chamber {

    @JsonProperty("chamberId")
    private Integer chamberId;
    @JsonProperty("Location")
    private Location chamberLocation;
    @JsonProperty("User")
    private User chamberUser;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("chamberId")
    public Integer getChamberId() {
        return chamberId;
    }

    @JsonProperty("chamberId")
    public void setChamberId(Integer chamberId) {
        this.chamberId = chamberId;
    }

    @JsonProperty("Location")
    public Location getChamberLocation() {
        return chamberLocation;
    }

    @JsonProperty("Location")
    public void setChamberLocation(Location chamberLocation) {
        this.chamberLocation = chamberLocation;
    }

    @JsonProperty("User")
    public User getChamberUser() {
        return chamberUser;
    }

    @JsonProperty("User")
    public void setChamberUser(User chamberUser) {
        this.chamberUser = chamberUser;
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
