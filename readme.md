# Description
This code package contains an Android application that displays a Google Map with the ability to add custom markers, edit their titles and snippets, and delete them. The application uses the Google Maps Android API and SQLite for saving the markers. The code is written in Java.

# Code Explanation
The FragmentMaps class is a Fragment that implements the OnMapReadyCallback interface, which provides a callback for when the map is ready to be used. The callback method initializes the GoogleMap object and sets up some default markers on the map.

The setOnMapClickListener method sets a listener that creates a new draggable marker at the clicked coordinates. Another setOnMapClickListener method is used to prompt the user to enter a title and a snippet for the marker before adding it to the map.

The setOnMarkerClickListener method sets a listener that displays a dialog for editing or deleting an existing marker. The dialog includes EditText views for the title and snippet and buttons for OK, Cancel, and Delete.

The MarkerDatabaseHelper class is a helper class for creating and managing the database that stores the markers. It extends the SQLiteOpenHelper class and provides methods for creating and upgrading the database, inserting and deleting markers, and retrieving all markers.

### disclaimer 
you need your own google api key.