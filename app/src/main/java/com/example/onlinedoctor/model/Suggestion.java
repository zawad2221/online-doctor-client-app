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
        "suggestionId",
        "suggestionDetails"
})

public class Suggestion {

    @JsonProperty("suggestionId")
    private Integer suggestionId;
    @JsonProperty("suggestionDetails")
    private String suggestionDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("suggestionId")
    public Integer getSuggestionId() {
        return suggestionId;
    }

    @JsonProperty("suggestionId")
    public void setSuggestionId(Integer suggestionId) {
        this.suggestionId = suggestionId;
    }

    @JsonProperty("suggestionDetails")
    public String getSuggestionDetails() {
        return suggestionDetails;
    }

    @JsonProperty("suggestionDetails")
    public void setSuggestionDetails(String suggestionDetails) {
        this.suggestionDetails = suggestionDetails;
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