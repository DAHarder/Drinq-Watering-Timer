package com.example.drinq.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.drink.R;

public class PlantListActivity extends AppCompatActivity {

    private PlantListViewModel mPlantListViewModel;
    public static final int NEW_PLANT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        RecyclerView recyclerView = findViewById(R.id.plant_list_recyclerview);
        final PlantListAdapter adapter = new PlantListAdapter(new PlantListAdapter.PlantDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlantListViewModel = new ViewModelProvider(this).get(PlantListViewModel.class);

        mPlantListViewModel.getAllPlants().observe(this, plantEntities ->  {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(plantEntities);
        });
        }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == NEW_PLANT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            PlantEntity plant = new PlantEntity(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
//            mWordViewModel.insert(word);
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
    }
