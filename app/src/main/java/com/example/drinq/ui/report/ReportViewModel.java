package com.example.drinq.ui.report;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.drinq.data.database.DrinqRepository;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.data.entity.ReportEntity;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {

    private DrinqRepository mRepository;

    public ReportViewModel(Application application) {
        super(application);
        mRepository = new DrinqRepository(application);
    }

    public LiveData<List<ReportEntity>> getAllPlants(int id) {
        return mRepository.getAllReports(id);
    }

    public LiveData<Integer> getCount(int plantID) {
        return mRepository.getReportCount(plantID); }

    public void insert(ReportEntity plant) {
        mRepository.insert(plant);
    }

    public void delete(ReportEntity plant) {
        mRepository.delete(plant);
    }

}
