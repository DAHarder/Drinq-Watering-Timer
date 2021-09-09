package com.example.drinq.ui.main;

/**
 * @author Dan Adams
 * @author dada141@wgu.edu
 * @version 1.0
 * @since 1.0
 *
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drink.R;
import com.example.drinq.data.database.DrinqRepository;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.plant.PlantEditActivity;
import com.example.drinq.util.DateUtils;
import com.example.drinq.util.PlantWaterNotice;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
/**
 * Application start - loads the activity_plant_list.fxml page
 */
public class PlantListActivity extends AppCompatActivity {
// Variables for use by class
    private PlantListViewModel mPlantListViewModel;
    private LiveData<List<PlantEntity>> allPlants;
    private PlantListAdapter adapter;
    private TextView plantCount;
    private PlantEntity plantUndo;

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

        //----SWIPE CODE----
        /**Allows the ability to swipe an object left or right within the RecyclerView.
         * The code allows an object to be deleted on a Left swipe
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    plantUndo = adapter.getPlantAt(viewHolder.getAdapterPosition());
                    mPlantListViewModel.delete(adapter.getPlantAt(viewHolder.getAdapterPosition()));
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Plant deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new UndoListener());
                    snackbar.show();
                }
                else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Swiped Right", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(PlantListActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.RED)
                        .addSwipeLeftLabel("Delete")
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .addActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        }).attachToRecyclerView(recyclerView);

        }

    //Implements undo option for use by the onSwiped function.
    class UndoListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mPlantListViewModel.insert(plantUndo);
        }
    }

        //Checks for a saved item from the plantEdit activity and saves it if found.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            PlantEntity plant = data.getExtras().getParcelable("plantSaved");
            PlantEntity plantOld = data.getExtras().getParcelable("plantOld");
            mPlantListViewModel.insert(plant);
            Snackbar.make(findViewById(R.id.main), "Plant saved", Snackbar.LENGTH_SHORT).show();
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
//Adds logic for the menu items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.new_plant) {
            Intent intent = new Intent(this, PlantEditActivity.class);
            this.startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }
//Login for the Search bar within the menu
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
