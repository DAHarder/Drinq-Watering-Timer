package com.example.drinq.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink.R;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.plant.PlantEditActivity;
import com.example.drinq.util.PlantWaterNotice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//---ADAPTER---
public class PlantListAdapter extends ListAdapter<PlantEntity, PlantListAdapter.PlantListViewHolder> {

    private final Context context;
    long wateredDateDiff;

    public PlantListAdapter(@NonNull DiffUtil.ItemCallback<PlantEntity> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @Override
    public PlantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_list_item, parent, false);
        return new PlantListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantListViewHolder holder, int position) {
        PlantEntity current = getItem(position);
        if (PlantWaterNotice.plantWaterNotice(current))
            holder.bind(current.getPlantName(), current.getPlantDescription(), true);
        else
            holder.bind(current.getPlantName(), current.getPlantDescription(),false);
    }

    static class PlantDiff extends DiffUtil.ItemCallback<PlantEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull PlantEntity oldItem, @NonNull PlantEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlantEntity oldItem, @NonNull PlantEntity newItem) {
            return oldItem.getPlantName().equals(newItem.getPlantName());
        }
    }

//---VIEW HOLDER---
class PlantListViewHolder extends RecyclerView.ViewHolder {
        private final TextView plantItemView;
        private final TextView plantDescriptionView;
        private final ImageView plantNeedsWaterIcon;

        private PlantListViewHolder(View itemView) {
            super(itemView);
            plantItemView = itemView.findViewById(R.id.plant_item_text_view);
            plantDescriptionView = itemView.findViewById(R.id.plant_item_description_view);
            plantNeedsWaterIcon = itemView.findViewById(R.id.water_needed_icon);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick (View v) {
                    int position = getAdapterPosition();
                   final PlantEntity currentPlant = getItem(position);
                    Intent intent = new Intent(context, PlantEditActivity.class);
                    intent.putExtra("plant", currentPlant);
                    ((Activity) context).startActivityForResult(intent, 1);
                }
            });

        }

        public void bind(String plantName, String plantDescription, boolean icon) {
            plantItemView.setText(plantName);

            if (plantDescription != null)
            plantDescriptionView.setText(plantDescription);

            if (!icon)
                plantNeedsWaterIcon.setVisibility(View.GONE);
            else
                plantNeedsWaterIcon.setVisibility(View.VISIBLE);
        }
    }

    public PlantEntity getPlantAt(int position) {
        final PlantEntity currentPlant = getItem(position);
        return currentPlant;
    }

}


