package com.example.maps;



import android.provider.BaseColumns;

// This is a final class that defines the contract for the markers table in the database.
public final class MarkerContract {
    private MarkerContract() {}

    public static class MarkerEntry implements BaseColumns {
        public static final String TABLE_NAME = "markers";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SNIPPET = "snippet";
    }
}

