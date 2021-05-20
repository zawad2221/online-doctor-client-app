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
        "companyId",
        "companyName"
})
public class Company {

    @JsonProperty("companyId")
    private Integer companyId;
    @JsonProperty("companyName")
    private String companyName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("companyId")
    public Integer getCompanyId() {
        return companyId;
    }

    @JsonProperty("companyId")
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
