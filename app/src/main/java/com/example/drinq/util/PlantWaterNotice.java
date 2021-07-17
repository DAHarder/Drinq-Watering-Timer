package com.example.drinq.util;

import com.example.drinq.data.entity.PlantEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PlantWaterNotice {

    static long wateredDateDiff;

    static public boolean plantWaterNotice(PlantEntity plant) {
    wateredDateDiff = ChronoUnit.DAYS.between(LocalDate.parse(plant.getLastWateredDate()), LocalDate.now());
        if (wateredDateDiff + 1 >= plant.getWateringInterval())
                return true;
        else
                return false;
}
}
