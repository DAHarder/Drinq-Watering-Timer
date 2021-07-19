package com.example.drinq.util;

import com.example.drinq.data.entity.PlantEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PlantWaterNotice {

    static long wateredDateDiff;
    static long plantTimeUntilWater;

    static public boolean plantWaterNotice(PlantEntity plant) {
    wateredDateDiff = ChronoUnit.DAYS.between(LocalDate.parse(plant.getLastWateredDate()), LocalDate.now());
        if (wateredDateDiff >= plant.getWateringInterval())
                return true;
        else
                return false;
}
    static public long getPlantWaterTime(PlantEntity plant){
        wateredDateDiff = ChronoUnit.DAYS.between(LocalDate.parse(plant.getLastWateredDate()), LocalDate.now());
        plantTimeUntilWater = plant.getWateringInterval() - wateredDateDiff;
        return plantTimeUntilWater;
    }

}
