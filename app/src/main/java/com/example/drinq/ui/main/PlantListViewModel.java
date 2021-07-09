package com.example.drinq.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.drinq.data.database.DrinqRepository;
import com.example.drinq.data.entity.PlantEntity;

import java.util.List;

public class PlantListViewModel extends AndroidViewModel {
    private DrinqRepository mRepository;

    private final LiveData<List<PlantEntity>> mAllPlants;

    public PlantListViewModel(Application application) {
        super(application);
        mRepository = new DrinqRepository(application);
        mAllPlants = mRepository.getAllPlants();
    }

    public LiveData<List<PlantEntity>> getAllPlants() {
        return mAllPlants;
    }

    public void insert(PlantEntity plant) {
        mRepository.insert(plant);
    }

    public void delete(PlantEntity plant) {
        mRepository.delete(plant);
    }
}