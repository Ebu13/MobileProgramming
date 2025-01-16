package com.example.sanalbahce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sanalbahce.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE plants (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "plant_code TEXT UNIQUE, " +
                "type TEXT, " +
                "plant_date TEXT, " +
                "last_water_date TEXT, " +
                "water_amount REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS plants");
        onCreate(db);
    }

    public void insertPlant(String plantCode, String type, String plantDate, String lastWaterDate, double waterAmount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("plant_code", plantCode);
        values.put("type", type);
        values.put("plant_date", plantDate);
        values.put("last_water_date", lastWaterDate);
        values.put("water_amount", waterAmount);
        db.insert("plants", null, values);
    }

    public Cursor getAllPlants() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM plants ORDER BY last_water_date DESC", null);
    }
}
