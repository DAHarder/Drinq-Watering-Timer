package com.example.drinq.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drink.R;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.plant.PlantEditActivity;

//---ADAPTER---
public class PlantListAdapter extends ListAdapter<PlantEntity, PlantListAdapter.PlantListViewHolder> {

    private final Context context;

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
        holder.bind(current.getPlantName());
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

        private PlantListViewHolder(View itemView) {
            super(itemView);
            plantItemView = itemView.findViewById(R.id.plant_item_text_view);

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

        public void bind(String text) {
            plantItemView.setText(text);
        }
    }
}


