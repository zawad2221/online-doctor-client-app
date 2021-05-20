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
        "formId",
        "formName"
})
public class MedicineForm {

    @JsonProperty("formId")
    private Integer formId;
    @JsonProperty("formName")
    private String formName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("formId")
    public Integer getFormId() {
        return formId;
    }

    @JsonProperty("formId")
    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    @JsonProperty("formName")
    public String getFormName() {
        return formName;
    }

    @JsonProperty("formName")
    public void setFormName(String formName) {
        this.formName = formName;
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
