package com.example.onlinedoctor.doctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.databinding.DoctorChamberSearchItemBinding;
import com.example.onlinedoctor.model.Chamber;

import java.util.List;

public class SearchChamberRecyclerAdapter extends RecyclerView.Adapter<SearchChamberRecyclerAdapter.SearchChamberRecyclerViewHolder>{
    List<Chamber> chamberList;
    SearchChamberRecyclerItemClickListener searchChamberRecyclerItemClickListener;

    public SearchChamberRecyclerAdapter(List<Chamber> chamberList, SearchChamberRecyclerItemClickListener searchChamberRecyclerItemClickListener) {
        this.chamberList = chamberList;
        this.searchChamberRecyclerItemClickListener = searchChamberRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public SearchChamberRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchChamberRecyclerViewHolder(
                DoctorChamberSearchItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SearchChamberRecyclerViewHolder holder, int position) {
        final Chamber chamber = chamberList.get(position);
        holder.doctorChamberSearchItemBinding.setChamber(chamber);
    }

    @Override
    public int getItemCount() {
        if(chamberList==null) return 0;
        else return chamberList.size();
    }

    public interface SearchChamberRecyclerItemClickListener{
        public void itemOnClick(int position);
    }

    public class SearchChamberRecyclerViewHolder extends RecyclerView.ViewHolder {
        DoctorChamberSearchItemBinding doctorChamberSearchItemBinding;
        public SearchChamberRecyclerViewHolder(DoctorChamberSearchItemBinding doctorChamberSearchItemBinding) {
            super(doctorChamberSearchItemBinding.getRoot());
            this.doctorChamberSearchItemBinding=doctorChamberSearchItemBinding;
            doctorChamberSearchItemBinding.searchItem.setOnClickListener(v->{
                searchChamberRecyclerItemClickListener.itemOnClick(getAdapterPosition());
            });
        }
    }
}
