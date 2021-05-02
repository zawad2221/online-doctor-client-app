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
        "askedQueryId",
        "askedQueryAnswer",
        "chamberId",
        "query"
})

public class AskedQuery {

    @JsonProperty("askedQueryId")
    private Integer askedQueryId;
    @JsonProperty("askedQueryAnswer")
    private String askedQueryAnswer;
    @JsonProperty("chamberId")
    private Chamber chamber;
    @JsonProperty("query")
    private PatientQuery query;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("askedQueryId")
    public Integer getAskedQueryId() {
        return askedQueryId;
    }

    @JsonProperty("askedQueryId")
    public void setAskedQueryId(Integer askedQueryId) {
        this.askedQueryId = askedQueryId;
    }

    @JsonProperty("askedQueryAnswer")
    public String getAskedQueryAnswer() {
        return askedQueryAnswer;
    }

    @JsonProperty("askedQueryAnswer")
    public void setAskedQueryAnswer(String askedQueryAnswer) {
        this.askedQueryAnswer = askedQueryAnswer;
    }

    @JsonProperty("chamberId")
    public Chamber getChamber() {
        return chamber;
    }

    @JsonProperty("chamberId")
    public void setChamber(Chamber chamber) {
        this.chamber = chamber;
    }

    @JsonProperty("query")
    public PatientQuery getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(PatientQuery query) {
        this.query = query;
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