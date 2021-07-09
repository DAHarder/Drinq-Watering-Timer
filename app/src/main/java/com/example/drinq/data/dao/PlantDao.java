package com.example.drinq.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.drinq.data.entity.PlantEntity;

import java.util.List;

@Dao
public interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlantEntity plant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PlantEntity> plant);

    @Delete
    void delete(PlantEntity plant);

    @Query("DELETE FROM plant_table")
    void deleteAllPlants();

    @Query("SELECT * FROM plant_table ORDER BY plantID ASC")
    LiveData<List<PlantEntity>> getAllPlants();
}
