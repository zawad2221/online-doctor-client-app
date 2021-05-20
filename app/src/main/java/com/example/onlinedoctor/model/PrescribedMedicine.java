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
        "prescribedMedicineId",
        "medicine",
        "quantity",
        "frequency",
        "duration",
        "instruction"
})
public class PrescribedMedicine {

    @JsonProperty("prescribedMedicineId")
    private Integer prescribedMedicineId;
    @JsonProperty("medicine")
    private Medicine medicine;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("frequency")
    private String frequency;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("instruction")
    private Instruction instruction;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("prescribedMedicineId")
    public Integer getPrescribedMedicineId() {
        return prescribedMedicineId;
    }

    @JsonProperty("prescribedMedicineId")
    public void setPrescribedMedicineId(Integer prescribedMedicineId) {
        this.prescribedMedicineId = prescribedMedicineId;
    }

    @JsonProperty("medicine")
    public Medicine getMedicine() {
        return medicine;
    }

    @JsonProperty("medicine")
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("frequency")
    public String getFrequency() {
        return frequency;
    }

    @JsonProperty("frequency")
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("instruction")
    public Instruction getInstruction() {
        return instruction;
    }

    @JsonProperty("instruction")
    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
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
