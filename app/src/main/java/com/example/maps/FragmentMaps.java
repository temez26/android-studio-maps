package com.example.maps;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

// Import necessary classes and interfaces
public class FragmentMaps extends Fragment {

    private MarkerDatabaseHelper mMarkerDbHelper;
    private SQLiteDatabase mDb;
    GoogleMap mMap;


    SearchView searchView;

    // Create a new instance of the OnMapReadyCallback interface
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        // Override the onMapReady method to handle map initialization
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng lahti = new LatLng(60.98267, 25.66151);
            Marker mr = googleMap.addMarker(new MarkerOptions().position(lahti).title("Lahti"));
            mr.setDraggable(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(lahti));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            MarkerDatabaseHelper dbHelper = new MarkerDatabaseHelper(getContext());

            List<MarkerOptions> markerOptionsList = dbHelper.getAllMarkers();
            for (MarkerOptions markerOptions : markerOptionsList) {
                mMap.addMarker(markerOptions);
            }

            // Set a click listener for the map that adds a new draggable marker at the clicked coordinates
            mMap.setOnMapClickListener(latLng -> {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title("Custom Marker Title")
                        .snippet("Click here to edit the title and snippet");
                Marker m = mMap.addMarker(markerOptions);
                m.setDraggable(true);
            });


            mMap.setOnMapClickListener(latLng -> {
                // Build an AlertDialog to prompt the user to enter a title and a snippet for the marker
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Custom Marker");

                // Create the EditText views for the title and snippet
                final EditText titleEditText = new EditText(getContext());
                titleEditText.setHint("Title");
                builder.setView(titleEditText);

                final EditText snippetEditText = new EditText(getContext());
                snippetEditText.setHint("add-info");
                builder.setView(snippetEditText);

                // Set up the buttons
                builder.setPositiveButton("OK", (dialog, which) -> {
                    String title = titleEditText.getText().toString();
                    String snippet = snippetEditText.getText().toString();

                    // Add the marker to the map with the user-provided title and snippet
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng)
                            .title(title)
                            .snippet(snippet);
                    Marker m = mMap.addMarker(markerOptions);
                    m.setDraggable(true);
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                // Show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            });


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Custom Marker");

                    // Create the EditText views for the title and snippet
                    final EditText titleEditText = new EditText(getActivity());
                    titleEditText.setText(marker.getTitle());
                    builder.setView(titleEditText);

                    final EditText snippetEditText = new EditText(getActivity());
                    snippetEditText.setText(marker.getSnippet());
                    builder.setView(snippetEditText);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String title = titleEditText.getText().toString();
                            String snippet = snippetEditText.getText().toString();

                            // Update the marker's title and snippet
                            marker.setTitle(title);
                            marker.setSnippet(snippet);
                            marker.showInfoWindow();
                        }
                    });

                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MarkerOptions markerOptions = new MarkerOptions().position(marker.getPosition()).title(marker.getTitle()).snippet(marker.getSnippet());
                            int markerId = dbHelper.getMarkerId(markerOptions); // Get the ID of the marker to be deleted
                            marker.remove();
                            dbHelper.deleteMarker(markerId); // Pass the ID of the marker to the deleteMarker() method
                        }
                    });


                    builder.setNegativeButton("Cancel", null);

                    // Disable custom marker option
                    builder.setCancelable(false);

                    // Show the dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
            });
            mMap.setOnMapClickListener(latLng -> {
                // Build an AlertDialog to prompt the user to enter a title and a snippet for the marker
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Custom Marker");

                // Create the EditText views for the title and snippet
                final EditText titleEditText = new EditText(getContext());
                titleEditText.setHint("Title");
                builder.setView(titleEditText);

                final EditText snippetEditText = new EditText(getContext());
                snippetEditText.setHint("add-info");
                builder.setView(snippetEditText);

                // Set up the buttons
                builder.setPositiveButton("OK", (dialog, which) -> {
                    String title = titleEditText.getText().toString();
                    String snippet = snippetEditText.getText().toString();

                    // Add the marker to the map with the user-provided title and snippet
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng)
                            .title(title)
                            .snippet(snippet);
                    Marker m = mMap.addMarker(markerOptions);
                    m.setDraggable(true);

                    // Add the marker to the SQLite database
                    MarkerDatabaseHelper databaseHelper = new MarkerDatabaseHelper(getContext());
                    databaseHelper.insertMarker(latLng.latitude, latLng.longitude, title, snippet);
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                // Show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            });

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Create a new instance of the database helper
        mMarkerDbHelper = new MarkerDatabaseHelper(getContext());
        // Get a writable instance of the database
        mDb = mMarkerDbHelper.getWritableDatabase();
        // Inflate the layout for the map fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    private void showMarkerInfoDialog(Marker marker) {
        // Create a new instance of the AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set the dialog title
        builder.setTitle("Marker Info");

        // Create the EditText views for the title and snippet
        final EditText titleEditText = new EditText(getContext());
        titleEditText.setText(marker.getTitle());
        builder.setView(titleEditText);

        final EditText snippetEditText = new EditText(getContext());
        snippetEditText.setText(marker.getSnippet());
        builder.setView(snippetEditText);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Get the text from the title and snippet EditText views
            String title = titleEditText.getText().toString();
            String snippet = snippetEditText.getText().toString();
            // Set the new title and snippet for the marker
            marker.setTitle(title);
            marker.setSnippet(snippet);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Override the onViewCreated method from the Fragment class
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        // If the SupportMapFragment is not null, set a callback to asynchronously load the map
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


        searchView = getActivity().findViewById(R.id.searchLocation);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                Geocoder geocoder = new Geocoder(getActivity());


                List<Address> addressList;


                String location = searchView.getQuery().toString().trim();

                try {

                    addressList = geocoder.getFromLocationName(location, 1);


                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Location"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }
                } catch (IOException e) {

                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

    }
}

