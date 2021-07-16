package com.example.drinq.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.drink.R;
import com.example.drinq.data.database.DrinqRepository;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.plant.PlantEditActivity;

import java.util.List;

public class PlantListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private PlantListViewModel mPlantListViewModel;
    public static final int NEW_PLANT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        RecyclerView recyclerView = findViewById(R.id.plant_list_recyclerview);
        final PlantListAdapter adapter = new PlantListAdapter(new PlantListAdapter.PlantDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlantListViewModel = new ViewModelProvider(this).get(PlantListViewModel.class);

        // Update the cached copy of the words in the adapter.
        mPlantListViewModel.getAllPlants().observe(this, adapter::submitList);

        LiveData<List<PlantEntity>> allPlants = mPlantListViewModel.getAllPlants();

        }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            PlantEntity plant = data.getExtras().getParcelable("plantSaved");
            mPlantListViewModel.insert(plant);
            Toast.makeText(this,"Plant Saved",Toast.LENGTH_LONG).show();
        }
    }

        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.plant_list_menu, menu);
            MenuItem searchItem = menu.findItem(R.id.plant_search_menu);
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();


            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.new_plant) {
                Intent intent = new Intent(this, PlantEditActivity.class);
                this.startActivityForResult(intent, 1);
            }

            return super.onOptionsItemSelected(item);
        }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
