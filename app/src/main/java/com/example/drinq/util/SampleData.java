package com.example.drinq.util;

import com.example.drinq.data.entity.PlantEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<PlantEntity> getSamplePlantData() {
        List<PlantEntity> plants = new ArrayList<>();
        plants.add(new PlantEntity("Snake Plant", "Back wall, second shelf", "2021-07-08",5, false));
        plants.add(new PlantEntity("Pothos", "Back wall, first shelf", "2021-07-09",5, false));
        plants.add(new PlantEntity("Peace Lily", "Back wall, third shelf", "2021-07-10",5, false));
        plants.add(new PlantEntity("Anthurium", "left wall, first shelf", "2021-07-11",5, false));
        plants.add(new PlantEntity("Parlor Palm", "left wall, second shelf", "2021-07-12",5, false));
        plants.add(new PlantEntity("Meyer Lemon Tree", "left wall, third shelf", "2021-07-13",5, false));
        plants.add(new PlantEntity("Philodendron", "right wall, first shelf", "2021-07-14",5, false));
        plants.add(new PlantEntity("Monstera", "right wall, second shelf", "2021-07-15",5, false));
        plants.add(new PlantEntity("Money Tree\n", "right wall, third shelf", "2021-07-15",5, false));
        plants.add(new PlantEntity("Misc plants", "", "2021-07-12",5, false));

        return plants;
    }

}
