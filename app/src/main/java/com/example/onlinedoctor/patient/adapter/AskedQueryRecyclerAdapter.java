package com.example.onlinedoctor.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinedoctor.databinding.QueryRecyclerItemBinding;
import com.example.onlinedoctor.model.AskedQuery;

import java.util.ArrayList;
import java.util.List;

public class AskedQueryRecyclerAdapter extends RecyclerView.Adapter<AskedQueryRecyclerAdapter.ViewHolder>{
    List<AskedQuery> askedQueries;
    OnClickListener onClickListener;

    public AskedQueryRecyclerAdapter(List<AskedQuery> askedQueries, OnClickListener onClickListener) {
        this.askedQueries = askedQueries;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(QueryRecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext())
                        ,parent,
                        false

        ));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AskedQuery askedQuery = askedQueries.get(position);
        holder.queryRecyclerItemBinding.setQuery(askedQuery);
        holder.queryRecyclerItemBinding.queryItem.setOnClickListener(v -> {
            onClickListener.onItemClick(position);
        });

    }

    @Override
    public int getItemCount() {
        if(askedQueries==null) return 0;
        else return askedQueries.size();
    }

    public interface OnClickListener{
        public void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        QueryRecyclerItemBinding queryRecyclerItemBinding;
        public ViewHolder(QueryRecyclerItemBinding queryRecyclerItemBinding) {
            super(queryRecyclerItemBinding.getRoot());
            this.queryRecyclerItemBinding=queryRecyclerItemBinding;
        }
    }
}
