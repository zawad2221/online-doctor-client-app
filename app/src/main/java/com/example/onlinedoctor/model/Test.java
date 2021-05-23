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
        "testId",
        "testRate",
        "testType",
        "pathologyCenter"
})
public class Test {

    @JsonProperty("testId")
    private Integer testId;
    @JsonProperty("testRate")
    private Integer testRate;
    @JsonProperty("testType")
    private TestType testType;
    @JsonProperty("pathologyCenter")
    private Pathology pathologyCenter;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("testId")
    public Integer getTestId() {
        return testId;
    }

    @JsonProperty("testId")
    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    @JsonProperty("testRate")
    public Integer getTestRate() {
        return testRate;
    }

    @JsonProperty("testRate")
    public void setTestRate(Integer testRate) {
        this.testRate = testRate;
    }

    @JsonProperty("testType")
    public TestType getTestType() {
        return testType;
    }

    @JsonProperty("testType")
    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    @JsonProperty("pathologyCenter")
    public Pathology getPathologyCenter() {
        return pathologyCenter;
    }

    @JsonProperty("pathologyCenter")
    public void setPathologyCenter(Pathology pathologyCenter) {
        this.pathologyCenter = pathologyCenter;
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