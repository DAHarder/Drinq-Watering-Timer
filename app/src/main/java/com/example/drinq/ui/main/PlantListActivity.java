package com.example.drinq.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drink.R;
import com.example.drinq.data.database.DrinqRepository;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.plant.PlantEditActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class PlantListActivity extends AppCompatActivity {

    private PlantListViewModel mPlantListViewModel;
    private LiveData<List<PlantEntity>> allPlants;
    private PlantListAdapter adapter;
    private TextView plantCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plant_list);

        RecyclerView recyclerView = findViewById(R.id.plant_list_recyclerview);
        adapter = new PlantListAdapter(new PlantListAdapter.PlantDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlantListViewModel = new ViewModelProvider(this).get(PlantListViewModel.class);

        mPlantListViewModel.getAllPlants("").observe(this, adapter::submitList);
        plantCount = findViewById(R.id.plant_count);

        mPlantListViewModel.getCount().observe(this, integer -> plantCount.setText(String.valueOf(integer)));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mPlantListViewModel.delete(adapter.getPlantAt(viewHolder.getAdapterPosition()));
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Plant deleted", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }).attachToRecyclerView(recyclerView);

        }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            PlantEntity plant = data.getExtras().getParcelable("plantSaved");
            PlantEntity plantOld = data.getExtras().getParcelable("plantOld");
            mPlantListViewModel.insert(plant);
            Toast.makeText(this, "Plant Saved", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plant_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.plant_search_menu);

        if (searchItem != null) {
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(onQueryTextListener);
        }

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

    private androidx.appcompat.widget.SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Update the cached copy of the words in the adapter.
                        allPlants = mPlantListViewModel.getAllPlants(newText.trim());
                        allPlants.observe(PlantListActivity.this, adapter::submitList);
                    return false;
                }
            };
}
