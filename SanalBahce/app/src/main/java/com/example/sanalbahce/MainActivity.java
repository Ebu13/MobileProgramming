package com.example.sanalbahce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    PlantAdapter plantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        ListView listView = findViewById(R.id.listViewPlants);
        plantAdapter = new PlantAdapter(this, dbHelper.getAllPlants());
        listView.setAdapter(plantAdapter);
    }

    public void addPlant(View view) {
        Intent intent = new Intent(this, AddPlantActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        plantAdapter.updatePlants(dbHelper.getAllPlants());
    }
}
