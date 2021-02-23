package com.example.onlinedoctor.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Pathology {
    @JsonProperty("pathologyId")
    private Integer pathologyId;
    @JsonProperty("Location")
    private Location pathologyLocation;
    @JsonProperty("User")
    private User pathologyUser;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pathologyId")
    public Integer getPathologyId() {
        return pathologyId;
    }

    @JsonProperty("pathologyId")
    public void setPathologyId(Integer pathologyId) {
        this.pathologyId = pathologyId;
    }

    @JsonProperty("Location")
    public Location getPathologyLocation() {
        return pathologyLocation;
    }

    @JsonProperty("Location")
    public void setPathologyLocation(Location pathologyLocation) {
        this.pathologyLocation = pathologyLocation;
    }

    @JsonProperty("User")
    public User getPathologyUser() {
        return pathologyUser;
    }

    @JsonProperty("User")
    public void setPathologyUser(User pathologyUser) {
        this.pathologyUser = pathologyUser;
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
