package com.example.drinq.data.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.drinq.data.dao.PlantDao;
import com.example.drinq.data.entity.PlantEntity;

import java.util.List;

import kotlinx.coroutines.flow.MutableStateFlow;

public class DrinqRepository {

    private PlantDao mPlantDao;


    public DrinqRepository(Application application) {

        DrinqDatabase db = DrinqDatabase.getDatabase(application);
        mPlantDao = db.plantDao();
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

}

