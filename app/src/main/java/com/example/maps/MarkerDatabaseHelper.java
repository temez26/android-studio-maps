package com.example.maps;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

// Define a class for managing a database of MarkerOptions objects
public class MarkerDatabaseHelper extends SQLiteOpenHelper {

    // Define constants for the database name and version
    private static final String DATABASE_NAME = "marker_database.db";
    private static final int DATABASE_VERSION = 1;

    // Define a constant for the SQL statement to create the table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + MarkerContract.MarkerEntry.TABLE_NAME + " (" +
                    MarkerContract.MarkerEntry._ID + " INTEGER PRIMARY KEY," +
                    MarkerContract.MarkerEntry.COLUMN_NAME_LATITUDE + " REAL," +
                    MarkerContract.MarkerEntry.COLUMN_NAME_LONGITUDE + " REAL," +
                    MarkerContract.MarkerEntry.COLUMN_NAME_TITLE + " TEXT," +
                    MarkerContract.MarkerEntry.COLUMN_NAME_SNIPPET + " TEXT)";

    // Define a constant for the SQL statement to drop the table
    private static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + MarkerContract.MarkerEntry.TABLE_NAME;

    // Define a constructor that takes a Context object and passes it to the superclass constructor
    public MarkerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Override the onCreate() method to create the database schema
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    // Override the onUpgrade() method to upgrade the database schema
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

    // Method to get the ID of a marker based on its latitude and longitude
    @SuppressLint("Range")
    public int getMarkerId(MarkerOptions markerOptions) {
        SQLiteDatabase db = getReadableDatabase();
        int id = -1;
        String[] columns = {MarkerContract.MarkerEntry._ID};
        String selection = MarkerContract.MarkerEntry.COLUMN_NAME_LATITUDE + " = ? AND " +
                MarkerContract.MarkerEntry.COLUMN_NAME_LONGITUDE + " = ?";
        String[] selectionArgs = {String.valueOf(markerOptions.getPosition().latitude),
                String.valueOf(markerOptions.getPosition().longitude)};
        Cursor cursor = db.query(MarkerContract.MarkerEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(MarkerContract.MarkerEntry._ID));
        }
        cursor.close();
        db.close();
        return id;
    }

    // Method to delete a marker by ID
    public void deleteMarker(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(MarkerContract.MarkerEntry.TABLE_NAME,
                MarkerContract.MarkerEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
        // Print debug information about the deletion to the logcat
        Log.d("Delete Marker", "Title: " + id + " Rows deleted: " + rowsDeleted);
        db.close();
    }


    // This method inserts a new marker into the database
    public void insertMarker(double latitude, double longitude, String title, String snippet) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MarkerContract.MarkerEntry.COLUMN_NAME_LATITUDE, latitude);
        values.put(MarkerContract.MarkerEntry.COLUMN_NAME_LONGITUDE, longitude);
        values.put(MarkerContract.MarkerEntry.COLUMN_NAME_TITLE, title);
        values.put(MarkerContract.MarkerEntry.COLUMN_NAME_SNIPPET, snippet);

        db.insert(MarkerContract.MarkerEntry.TABLE_NAME, null, values);
    }

    // This method retrieves all markers from the database and returns a List of MarkerOptions
    public List<MarkerOptions> getAllMarkers() {

        List<MarkerOptions> markerOptionsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                MarkerContract.MarkerEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        // Iterate over the Cursor to extract each marker and add it to the List
        while (cursor.moveToNext()) {

            @SuppressLint("Range") double latitude = cursor.getDouble(cursor.getColumnIndex(MarkerContract.MarkerEntry.COLUMN_NAME_LATITUDE));
            @SuppressLint("Range") double longitude = cursor.getDouble(cursor.getColumnIndex(MarkerContract.MarkerEntry.COLUMN_NAME_LONGITUDE));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MarkerContract.MarkerEntry.COLUMN_NAME_TITLE));
            @SuppressLint("Range") String snippet = cursor.getString(cursor.getColumnIndex(MarkerContract.MarkerEntry.COLUMN_NAME_SNIPPET));

            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latitude, longitude)).title(title).snippet(snippet);

            markerOptionsList.add(markerOptions);
        }
        // Close the Cursor and the database
        cursor.close();
        db.close();
        // Return the List of MarkerOptions
        return markerOptionsList;
    }
}