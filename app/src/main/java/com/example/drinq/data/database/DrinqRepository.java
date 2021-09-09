package com.example.drinq.data.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.drinq.data.dao.PlantDao;
import com.example.drinq.data.dao.ReportDao;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.data.entity.ReportEntity;

import java.util.List;

import kotlinx.coroutines.flow.MutableStateFlow;
/**
 * Repository used to hold and access data
 */
public class DrinqRepository {

    private PlantDao mPlantDao;
    private ReportDao mReportDao;


    public DrinqRepository(Application application) {

        DrinqDatabase db = DrinqDatabase.getDatabase(application);
        mPlantDao = db.plantDao();
        mReportDao = db.reportDao();
    }

    public LiveData<List<PlantEntity>> getAllPlants(String query) {
        return mPlantDao.getAllPlants(query);
    }

    public LiveData<Integer> getCount() {
        return mPlantDao.getCount();
    }

    public void insert(PlantEntity plant) {
        DrinqDatabase.databaseWriteExecutor.execute(() -> mPlantDao.insert(plant));
    }

     public void delete (PlantEntity plant) {
         DrinqDatabase.databaseWriteExecutor.execute(()-> mPlantDao.delete(plant));
        }


    public LiveData<List<ReportEntity>> getAllReports(int id) {
        return mReportDao.getAllPlants(id);
    }

    public LiveData<Integer> getReportCount(int plantID) {
        return mReportDao.getCount(plantID);
    }

    public void insert(ReportEntity plant) {
        DrinqDatabase.databaseWriteExecutor.execute(() -> mReportDao.insert(plant));
    }

    public void delete (ReportEntity plant) {
        DrinqDatabase.databaseWriteExecutor.execute(()-> mReportDao.delete(plant));
    }

}

