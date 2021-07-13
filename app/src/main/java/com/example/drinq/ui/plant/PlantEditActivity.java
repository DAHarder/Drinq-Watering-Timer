package com.example.drinq.ui.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drink.R;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.main.PlantListActivity;
import com.example.drinq.ui.main.PlantListViewModel;

import java.time.LocalDate;

public class PlantEditActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.Drinqsql.REPLY";

    private PlantListViewModel plantListViewModel;

    PlantEntity passedPlant;

    int plantID;
    EditText plantName;
    EditText plantDescription;
    TextView waterDate;
    EditText waterInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_edit);

        plantListViewModel = new PlantListViewModel(getApplication());
        //--- Fill in plant edit fields if needed----//
        if (getIntent().getExtras() != null) {
            getSupportActionBar().setTitle("Edit Plant"); //Edit menu Title
            passedPlant = getIntent().getExtras().getParcelable("plant");

            plantName = findViewById(R.id.plant_name_edit);
            plantName.setText(passedPlant.getPlantName());

            plantDescription = findViewById(R.id.plant_description_edit_text);
            plantDescription.setText(passedPlant.getPlantDescription());

            waterDate = findViewById(R.id.plant_last_watered_date_text);
            waterDate.setText(passedPlant.getLastWateredDate());

            waterInterval = findViewById(R.id.plant_watering_interval_edit_text);
            waterInterval.setText(String.valueOf(passedPlant.getWateringInterval()));
        }
        else {
            getSupportActionBar().setTitle("Add Plant"); //Edit menu Title
            plantName = findViewById(R.id.plant_name_edit);
            plantDescription = findViewById(R.id.plant_description_edit_text);
            waterDate = findViewById(R.id.plant_last_watered_date_text);
            waterInterval = findViewById(R.id.plant_watering_interval_edit_text);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plant_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        savePlant();
        return super.onOptionsItemSelected(item);
    }

    private void savePlant() {
        if (plantName.getText().toString().trim().isEmpty() ||
        plantDescription.getText().toString().trim().isEmpty() ||
        waterInterval.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled prior to saving plant", Toast.LENGTH_LONG).show();
            return;
        }

        if (passedPlant != null){
            passedPlant.setPlantName(plantName.getText().toString().trim());
            passedPlant.setPlantDescription(plantDescription.getText().toString().trim());
            passedPlant.setWateringInterval(Integer.parseInt(waterInterval.getText().toString().trim()));

            Intent intent = new Intent();
            intent.putExtra("plantSaved", passedPlant);
            setResult(RESULT_OK, intent);
        }
        else {
            PlantEntity newPlant = new PlantEntity(plantName.getText().toString().trim(), plantDescription.getText().toString().trim(), LocalDate.now().toString(), Integer.parseInt(waterInterval.getText().toString().trim()), false );

            Intent intent = new Intent();
            intent.putExtra("plantSaved", newPlant);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public void waterPlantBtn(View view) {
    }
}