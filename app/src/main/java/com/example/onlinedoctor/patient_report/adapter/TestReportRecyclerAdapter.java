package com.example.onlinedoctor.patient_report.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.databinding.PatientReportRecyclerItemBinding;
import com.example.onlinedoctor.model.TestReport;

import java.util.List;

public class TestReportRecyclerAdapter extends RecyclerView.Adapter<TestReportRecyclerAdapter.TestReportViewHolder>{
    List<TestReport> testReportList;
    public OnClickListener onClickListener;

    public TestReportRecyclerAdapter(List<TestReport> testReportList, OnClickListener onClickListener) {
        this.testReportList = testReportList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TestReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestReportViewHolder(
                PatientReportRecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TestReportViewHolder holder, int position) {
        final TestReport testReport = testReportList.get(position);
        holder.patientReportRecyclerItemBinding.setTestReport(testReport);
    }

    @Override
    public int getItemCount() {
        if(testReportList==null) return 0;
        else return testReportList.size();
    }

    public interface OnClickListener{
        public void onItemClick(int position);
    }

    public class TestReportViewHolder extends RecyclerView.ViewHolder{
        PatientReportRecyclerItemBinding patientReportRecyclerItemBinding;


        public TestReportViewHolder(PatientReportRecyclerItemBinding patientReportRecyclerItemBinding) {
            super(patientReportRecyclerItemBinding.getRoot());
            this.patientReportRecyclerItemBinding=patientReportRecyclerItemBinding;
            patientReportRecyclerItemBinding.reportItemConstraintLayout.setOnClickListener(v -> {
                onClickListener.onItemClick(getAdapterPosition());
            });

        }
    }
}
