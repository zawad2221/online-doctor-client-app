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
        "feeId",
        "oldFee",
        "newFee",
        "reportFee"
})
public class VisitingScheduleFee {

    @JsonProperty("feeId")
    private Integer feeId;
    @JsonProperty("oldFee")
    private Integer oldFee;
    @JsonProperty("newFee")
    private Integer newFee;
    @JsonProperty("reportFee")
    private Integer reportFee;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("feeId")
    public Integer getFeeId() {
        return feeId;
    }

    @JsonProperty("feeId")
    public void setFeeId(Integer feeId) {
        this.feeId = feeId;
    }

    @JsonProperty("oldFee")
    public Integer getOldFee() {
        return oldFee;
    }

    @JsonProperty("oldFee")
    public void setOldFee(Integer oldFee) {
        this.oldFee = oldFee;
    }

    @JsonProperty("newFee")
    public Integer getNewFee() {
        return newFee;
    }

    @JsonProperty("newFee")
    public void setNewFee(Integer newFee) {
        this.newFee = newFee;
    }

    @JsonProperty("reportFee")
    public Integer getReportFee() {
        return reportFee;
    }

    @JsonProperty("reportFee")
    public void setReportFee(Integer reportFee) {
        this.reportFee = reportFee;
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