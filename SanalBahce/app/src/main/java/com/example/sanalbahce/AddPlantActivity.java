package com.example.sanalbahce;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddPlantActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        dbHelper = new DatabaseHelper(this);
    }

    public void savePlant(View view) {
        EditText editPlantCode = findViewById(R.id.editPlantCode);
        Spinner spinnerType = findViewById(R.id.spinnerType);
        EditText editPlantDate = findViewById(R.id.editPlantDate);
        EditText editWaterDate = findViewById(R.id.editWaterDate);
        EditText editWaterAmount = findViewById(R.id.editWaterAmount);

        String plantCode = editPlantCode.getText().toString();
        String type = spinnerType.getSelectedItem().toString();
        String plantDate = editPlantDate.getText().toString();
        String waterDate = editWaterDate.getText().toString();
        String waterAmount = editWaterAmount.getText().toString();

        if (plantCode.isEmpty() || waterAmount.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.insertPlant(plantCode, type, plantDate, waterDate, Double.parseDouble(waterAmount));
        Toast.makeText(this, "Bitki başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
