package com.example.drinq.ui.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drink.R;
import com.example.drinq.data.entity.PlantEntity;
import com.example.drinq.ui.main.PlantListViewModel;
import com.example.drinq.util.DateUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PlantEditActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.Drinqsql.REPLY";

    private PlantListViewModel plantListViewModel;

    PlantEntity passedPlant;

    int plantID;
    EditText plantName;
    EditText plantDescription;
    TextView plantWaterDate;
    EditText plantWaterInterval;
    TextView plantWaterNeeded;
    Button plantWaterBtn;

    long wateredDateDiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_edit);

        plantWaterNeeded = findViewById(R.id.plant_needs_watered_text);
        plantName = findViewById(R.id.plant_name_edit);
        plantDescription = findViewById(R.id.plant_description_edit_text);
        plantWaterDate = findViewById(R.id.plant_last_watered_date_text);
        plantWaterInterval = findViewById(R.id.plant_watering_interval_edit_text);
        plantWaterBtn = findViewById(R.id.plant_watered_button);


        plantListViewModel = new PlantListViewModel(getApplication());
        //--- Fill in plant edit fields if needed----//
        if (getIntent().getExtras() != null) {
            getSupportActionBar().setTitle("Edit Plant"); //Edit menu Title

            passedPlant = getIntent().getExtras().getParcelable("plant"); //Get plant object from main activity

            wateredDateDiff = ChronoUnit.DAYS.between(LocalDate.parse(passedPlant.getLastWateredDate()), LocalDate.now()); //Calculate time difference between now and last watered date

            plantName.setText(passedPlant.getPlantName());
            plantDescription.setText(passedPlant.getPlantDescription());
            plantWaterDate.setText(DateUtils.formatDate(passedPlant.getLastWateredDate()));
            plantWaterInterval.setText(String.valueOf(passedPlant.getWateringInterval()));

            if (wateredDateDiff <= passedPlant.getWateringInterval())
                plantWaterNeeded.setVisibility(View.GONE);
            else
                plantWaterNeeded.setVisibility(View.VISIBLE);

        }
        else {
            getSupportActionBar().setTitle("Add Plant"); //Edit menu Title
            plantWaterNeeded.setVisibility(View.GONE);
            plantWaterBtn.setVisibility(View.GONE);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Saving water date. For use with Watered Button
        savedInstanceState.putString("MyString", plantWaterDate.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String myString = savedInstanceState.getString("MyString"); //Retrieve saved water date string
        plantWaterDate.setText(myString);
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
        plantWaterInterval.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled prior to saving plant", Toast.LENGTH_LONG).show();
            return;
        }

        if (passedPlant != null){
            passedPlant.setPlantName(plantName.getText().toString().trim());
            passedPlant.setPlantDescription(plantDescription.getText().toString().trim());
            passedPlant.setWateringInterval(Integer.parseInt(plantWaterInterval.getText().toString().trim()));

            Intent intent = new Intent();
            intent.putExtra("plantSaved", passedPlant);
            setResult(RESULT_OK, intent);
        }
        else {
            PlantEntity newPlant = new PlantEntity(plantName.getText().toString().trim(), plantDescription.getText().toString().trim(), LocalDate.now().toString(), Integer.parseInt(plantWaterInterval.getText().toString().trim()), false );

            Intent intent = new Intent();
            intent.putExtra("plantSaved", newPlant);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public void waterPlantBtn(View view) {
        plantWaterDate.setText(DateUtils.formatDate(LocalDate.now().toString()));
        passedPlant.setLastWateredDate(LocalDate.now().toString());

        plantWaterNeeded.setVisibility(View.GONE);

        plantListViewModel.insert(passedPlant);

    }
}