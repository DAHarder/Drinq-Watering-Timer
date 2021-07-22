package com.example.drinq.ui.report;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.widget.TextView;

import com.example.drink.R;
import com.example.drinq.data.dao.ReportDao;
import com.example.drinq.data.database.DrinqRepository;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.main.PlantListAdapter;
import com.example.drinq.ui.main.PlantListViewModel;

public class ReportActivity extends AppCompatActivity {

    private ReportViewModel mReportViewModel;
    private ReportListAdapter adapter;
    private TextView watercount;
    private int plantID;
    private String plantName;

    static public PlantEntity passedPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        passedPlant = getIntent().getExtras().getParcelable("plant");

        plantID = passedPlant.getPlantID();
        plantName = passedPlant.getPlantName();


        RecyclerView recyclerView = findViewById(R.id.plant_report_recycleview);
        adapter = new ReportListAdapter(new ReportListAdapter.PlantDiff(), plantName,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        mReportViewModel.getAllPlants(plantID).observe(this, adapter::submitList);
        watercount = findViewById(R.id.plant_report_count);

//        plant_report_count
        mReportViewModel.getCount(plantID).observe(this, integer -> watercount.setText(integer + " times"));
    }
}