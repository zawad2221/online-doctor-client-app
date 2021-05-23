package com.example.onlinedoctor.model;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.util.HashMap;
import java.util.Map;

import com.example.onlinedoctor.DateAndTime;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import okhttp3.ResponseBody;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "testReportId",
        "testReportDetails",
        "issueDate",
        "isDone",
        "filePath",
        "prescription",
        "test",
        "testType"
})
public class TestReport {

    public static ResponseBody responseBody;

    @JsonProperty("testReportId")
    private Integer testReportId;
    @JsonProperty("testReportDetails")
    private String testReportDetails;
    @JsonProperty("issueDate")
    private String issueDate;
    @JsonProperty("isDone")
    private Boolean isDone;
    @JsonProperty("filePath")
    private String filePath;
    @JsonProperty("prescription")
    private Prescription prescription;
    @JsonProperty("test")
    private Test test;
    @JsonProperty("testType")
    private TestType testType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("testReportId")
    public Integer getTestReportId() {
        return testReportId;
    }

    @JsonProperty("testReportId")
    public void setTestReportId(Integer testReportId) {
        this.testReportId = testReportId;
    }

    @JsonProperty("testReportDetails")
    public String getTestReportDetails() {
        return testReportDetails;
    }

    @JsonProperty("testReportDetails")
    public void setTestReportDetails(String testReportDetails) {
        this.testReportDetails = testReportDetails;
    }

    @JsonProperty("issueDate")
    public String getIssueDate() {
        return issueDate;
    }

    @JsonProperty("issueDate")
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @JsonProperty("isDone")
    public Boolean getIsDone() {
        return isDone;
    }

    @JsonProperty("isDone")
    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    @JsonProperty("filePath")
    public String getFilePath() {
        return filePath;
    }

    @JsonProperty("filePath")
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @JsonProperty("prescription")
    public Prescription getPrescription() {
        return prescription;
    }

    @JsonProperty("prescription")
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @JsonProperty("test")
    public Test getTest() {
        return test;
    }

    @JsonProperty("test")
    public void setTest(Test test) {
        this.test = test;
    }

    @JsonProperty("testType")
    public TestType getTestType() {
        return testType;
    }

    @JsonProperty("testType")
    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @BindingAdapter("android:setVisibilityOnIsDone")
    public static void setVisibilityOnIsDone(LinearLayout linearLayout, boolean isDone){
        if(isDone) linearLayout.setVisibility(View.VISIBLE);
        else linearLayout.setVisibility(View.GONE);
    }

}
