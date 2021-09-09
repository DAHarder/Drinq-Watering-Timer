package com.example.drinq.ui.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink.R;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.data.entity.ReportEntity;
import com.example.drinq.ui.main.PlantListAdapter;
import com.example.drinq.ui.plant.PlantEditActivity;
import com.example.drinq.util.PlantWaterNotice;

import org.jetbrains.annotations.NotNull;
/**
 * Holds the logic to fill the RecyclerView with objects
 */
public class ReportListAdapter extends ListAdapter<ReportEntity, ReportListAdapter.ReportListViewHolder> {

    private final Context context;
    private String plantNameIn;

    public ReportListAdapter(@NonNull DiffUtil.ItemCallback<ReportEntity> diffCallback, String plantNameIn, Context context) {
        super(diffCallback);
        this.plantNameIn = plantNameIn;
        this.context = context;
    }

    @NotNull
    @Override
    public ReportListAdapter.ReportListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_report_item, parent, false);
        return new ReportListAdapter.ReportListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportListAdapter.ReportListViewHolder holder, int position) {
        ReportEntity current = getItem(position);
        holder.bind(current);

    }

    static class PlantDiff extends DiffUtil.ItemCallback<ReportEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull ReportEntity oldItem, @NonNull ReportEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReportEntity oldItem, @NonNull ReportEntity newItem) {
            return oldItem.getPlantID() == newItem.getPlantID();
        }
    }

    //---VIEW HOLDER---
    class ReportListViewHolder extends RecyclerView.ViewHolder {
        private final TextView plantName;
        private final TextView plantWateredDate;

        private ReportListViewHolder(View itemView) {
            super(itemView);
            plantName = itemView.findViewById(R.id.plant_item_text_view);
            plantWateredDate = itemView.findViewById(R.id.last_watered);
        }

        public void bind(ReportEntity plant) {
            plantName.setText(plantNameIn);
            plantWateredDate.setText(plant.getLastWateredDate());
        }
    }

}
