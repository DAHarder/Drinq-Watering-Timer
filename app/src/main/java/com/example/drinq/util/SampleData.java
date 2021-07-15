package com.example.drinq.util;

import com.example.drinq.data.entity.PlantEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<PlantEntity> getSamplePlantData() {
        List<PlantEntity> plants = new ArrayList<>();
        plants.add(new PlantEntity("TestPlant1", "Left of TV", "2021-07-10",7, false));
        plants.add(new PlantEntity("TestPlant2", "Left of TV", "2021-07-04",5, false));

        return plants;
    }

}
