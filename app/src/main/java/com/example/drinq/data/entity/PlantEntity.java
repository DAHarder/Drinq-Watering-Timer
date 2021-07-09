package com.example.drinq.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plant_table")
public class PlantEntity {

    @PrimaryKey
    @NonNull
    private int plantID;
    @NonNull
    private String plantName;
    private String plantDescription;
    private String setDate;
    private int wateringInterval;
    private boolean needsWater;

    public PlantEntity(@NonNull int plantID, @NonNull String plantName, String plantDescription, String setDate, int wateringInterval, boolean needsWater) {
        this.plantID = plantID;
        this.plantName = plantName;
        this.plantDescription = plantDescription;
        this.setDate = setDate;
        this.wateringInterval = wateringInterval;
        this.needsWater = needsWater;
    }

    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantDescription() {
        return plantDescription;
    }

    public void setPlantDescription(String plantDescription) {
        this.plantDescription = plantDescription;
    }

    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public int getWateringInterval() {
        return wateringInterval;
    }

    public void setWateringInterval(int wateringInterval) {
        this.wateringInterval = wateringInterval;
    }

    public boolean isNeedsWater() {
        return needsWater;
    }

    public void setNeedsWater(boolean needsWater) {
        this.needsWater = needsWater;
    }
}
