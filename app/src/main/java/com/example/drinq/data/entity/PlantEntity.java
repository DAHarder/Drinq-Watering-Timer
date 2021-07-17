package com.example.drinq.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plant_table")
public class PlantEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int plantID;
    @NonNull
    private String plantName;
    private String plantDescription;
    private String lastWateredDate;
    private int wateringInterval;
    private boolean needsWater;

    public PlantEntity(@NonNull String plantName, String plantDescription, String lastWateredDate, int wateringInterval, boolean needsWater) {
        this.plantName = plantName;
        this.plantDescription = plantDescription;
        this.lastWateredDate = lastWateredDate;
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

    public String getLastWateredDate() {
        return lastWateredDate;
    }

    public void setLastWateredDate(String lastWateredDate) {
        this.lastWateredDate = lastWateredDate;
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


    protected PlantEntity(Parcel in) {
        plantID = in.readInt();
        plantName = in.readString();
        plantDescription = in.readString();
        lastWateredDate = in.readString();
        wateringInterval = in.readInt();
        needsWater = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(plantID);
        dest.writeString(plantName);
        dest.writeString(plantDescription);
        dest.writeString(lastWateredDate);
        dest.writeInt(wateringInterval);
        dest.writeByte((byte) (needsWater ? 0x01 : 0x00));
    }

    public static final Parcelable.Creator<PlantEntity> CREATOR = new Parcelable.Creator<PlantEntity>() {
        @Override
        public PlantEntity createFromParcel(Parcel in) {
            return new PlantEntity(in);
        }

        @Override
        public PlantEntity[] newArray(int size) {
            return new PlantEntity[size];
        }
    };

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        PlantEntity p1 = (PlantEntity) obj;

        return this.plantName.equals(p1.plantName)
                && this.plantID == p1.plantID
                && this.plantDescription.equals(p1.plantDescription)
                && this.lastWateredDate.equals(p1.lastWateredDate)
                && this.wateringInterval == p1.wateringInterval
                && this.needsWater == p1.needsWater;
    }
}
