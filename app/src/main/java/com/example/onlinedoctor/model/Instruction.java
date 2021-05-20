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
        "instructionId",
        "instructionDetails"
})
public class Instruction {

    @JsonProperty("instructionId")
    private Integer instructionId;
    @JsonProperty("instructionDetails")
    private String instructionDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("instructionId")
    public Integer getInstructionId() {
        return instructionId;
    }

    @JsonProperty("instructionId")
    public void setInstructionId(Integer instructionId) {
        this.instructionId = instructionId;
    }

    @JsonProperty("instructionDetails")
    public String getInstructionDetails() {
        return instructionDetails;
    }

    @JsonProperty("instructionDetails")
    public void setInstructionDetails(String instructionDetails) {
        this.instructionDetails = instructionDetails;
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
