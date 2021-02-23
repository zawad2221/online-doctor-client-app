package com.example.onlinedoctor.model;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class User {
    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("userPhoneNumber")
    private String userPhoneNumber;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("userRole")
    private String userRole;
    @JsonProperty("password")
    private String password;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("userPhoneNumber")
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    @JsonProperty("userPhoneNumber")
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("userRole")
    public String getUserRole() {
        return userRole;
    }

    @JsonProperty("userRole")
    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
