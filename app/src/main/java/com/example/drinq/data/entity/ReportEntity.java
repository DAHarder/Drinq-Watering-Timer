package com.example.drinq.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Report class POJO for reports table in database using ROOM
 */
@Entity(tableName = "report_table")
public class ReportEntity {

    @PrimaryKey(autoGenerate = true)
    private int reportID;
    private String lastWateredDate;
    private int plantID;

    public ReportEntity(@NonNull String lastWateredDate, int plantID) {
        this.lastWateredDate = lastWateredDate;
        this.plantID = plantID;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }


    public String getLastWateredDate() {
        return lastWateredDate;
    }

    public void setLastWateredDate(String lastWateredDate) {
        this.lastWateredDate = lastWateredDate;
    }

    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }
}
