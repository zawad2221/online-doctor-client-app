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
        "medicineId",
        "medicineName",
        "medicineForm",
        "medicineCompany"
})
public class Medicine {

    @JsonProperty("medicineId")
    private Integer medicineId;
    @JsonProperty("medicineName")
    private String medicineName;
    @JsonProperty("medicineForm")
    private MedicineForm medicineForm;
    @JsonProperty("medicineCompany")
    private Company medicineCompany;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("medicineId")
    public Integer getMedicineId() {
        return medicineId;
    }

    @JsonProperty("medicineId")
    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    @JsonProperty("medicineName")
    public String getMedicineName() {
        return medicineName;
    }

    @JsonProperty("medicineName")
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    @JsonProperty("medicineForm")
    public MedicineForm getMedicineForm() {
        return medicineForm;
    }

    @JsonProperty("medicineForm")
    public void setMedicineForm(MedicineForm medicineForm) {
        this.medicineForm = medicineForm;
    }

    @JsonProperty("medicineCompany")
    public Company getMedicineCompany() {
        return medicineCompany;
    }

    @JsonProperty("medicineCompany")
    public void setMedicineCompany(Company medicineCompany) {
        this.medicineCompany = medicineCompany;
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
