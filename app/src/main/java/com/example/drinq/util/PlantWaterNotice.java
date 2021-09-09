package com.example.drinq.util;

import com.example.drinq.data.entity.PlantEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Calculates the time between the lastWateredDate of a plant
 * and the current date and returns whether it has been longer
 * than the listed waterInterval on the plant object.
 *
 * To be used with logic on activity pages.
 */
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
