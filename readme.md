# Android Maps Application

A native Android application that provides interactive mapping functionality with persistent marker management using Google Maps API and SQLite database.

## Features

- **Interactive Map Navigation**: Full Google Maps integration with zoom, pan, and gesture controls
- **Custom Marker Management**: Add, edit, and delete markers with custom titles and descriptions
- **Persistent Storage**: SQLite database integration for marker data persistence across app sessions
- **Location Search**: Geocoding functionality for location-based searches
- **Real-time Updates**: Dynamic marker manipulation with immediate visual feedback

## Technical Stack

- **Language**: Java 8
- **Minimum SDK**: API 30 (Android 11)
- **Target SDK**: API 33 (Android 13)
- **Architecture**: Fragment-based UI with SQLite data layer
- **Dependencies**:
  - Google Play Services Maps 18.0.2
  - Google Play Services Location 17.0.0
  - AndroidX AppCompat 1.6.1
  - Material Design Components 1.8.0

## Core Components

### MainActivity

Application entry point handling permission management and fragment container.

### FragmentMaps

Map interaction and marker management UI with Google Maps integration and marker CRUD operations.

### MarkerDatabaseHelper

Data persistence layer with SQLite database operations for marker storage.

## Setup Instructions

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 8 or higher
- Google Maps API key

### Configuration

1. **API Key Setup**:

   ```xml
   <!-- Add to AndroidManifest.xml -->
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="${MAPS_API_KEY}" />
   ```

2. **Local Properties**:

   ```properties
   # Add to local.properties
   MAPS_API_KEY=your_google_maps_api_key_here
   ```

3. **Required Permissions**:
   ```xml
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
   ```

## Known Issues

- Multiple map click listeners that override each other
- Database connections need proper resource management
- Limited error handling for network operations
- Basic dialog implementations without input validation
