package com.example.drinq.data.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.drinq.data.dao.PlantDao;
import com.example.drinq.data.entity.PlantEntity;

import java.util.List;

public class DrinqRepository {

    private PlantDao mPlantDao;
    private LiveData<List<PlantEntity>> mAllPlants;


    public DrinqRepository(Application application) {

        DrinqDatabase db = DrinqDatabase.getDatabase(application);
        mPlantDao = db.plantDao();
        mAllPlants = mPlantDao.getAllPlants();
    }

    public LiveData<List<PlantEntity>> getAllPlants() {
        return mAllPlants;
    }

    public void insert(PlantEntity plant) {
        DrinqDatabase.databaseWriteExecutor.execute(() -> mPlantDao.insert(plant));
    }

     public void delete (PlantEntity plant) {
         DrinqDatabase.databaseWriteExecutor.execute(()-> mPlantDao.delete(plant));
        }

}

