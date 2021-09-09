package com.example.drinq.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.data.entity.ReportEntity;

import java.util.List;
/**
 * Reports Table Data Access Object Class using ROOM implementation
 */
@Dao
public interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReportEntity plant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReportEntity> plant);

    @Delete
    void delete(ReportEntity plant);

    @Query("DELETE FROM report_table")
    void deleteAllPlants();

    @Query("SELECT COUNT(*) FROM report_table WHERE plantID = :plantID")
    LiveData<Integer> getCount(int plantID);

    @Query("SELECT * FROM report_table WHERE plantID = :plantID")
    LiveData<List<ReportEntity>> getAllPlants(int plantID);
}
