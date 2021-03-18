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
        "datsOfWeekId",
        "day"
})
public class DaysOfWeek {

    @JsonProperty("datsOfWeekId")
    private Integer datsOfWeekId;
    @JsonProperty("day")
    private String day;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("datsOfWeekId")
    public Integer getDatsOfWeekId() {
        return datsOfWeekId;
    }

    @JsonProperty("datsOfWeekId")
    public void setDatsOfWeekId(Integer datsOfWeekId) {
        this.datsOfWeekId = datsOfWeekId;
    }

    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
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