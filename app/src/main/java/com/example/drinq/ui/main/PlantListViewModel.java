package com.example.drinq.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.drinq.data.database.DrinqRepository;
import com.example.drinq.data.entity.PlantEntity;

import java.util.List;

import kotlinx.coroutines.flow.MutableStateFlow;

public class PlantListViewModel extends AndroidViewModel {

    private DrinqRepository mRepository;

    public PlantListViewModel(Application application) {
        super(application);
        mRepository = new DrinqRepository(application);
    }

    public LiveData<List<PlantEntity>> getAllPlants(String query) {
        return mRepository.getAllPlants(query);
    }

    //Returns plant count
    public LiveData<Integer> getCount() { return mRepository.getCount(); }

    public void insert(PlantEntity plant) {
        mRepository.insert(plant);
    }

    public void delete(PlantEntity plant) {
        mRepository.delete(plant);
    }
}