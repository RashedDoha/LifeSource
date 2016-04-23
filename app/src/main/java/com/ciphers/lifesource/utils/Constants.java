package com.ciphers.lifesource.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Rashed on 16/04/2016.
 */
public class Constants {

    /* Constants for Auth */

    public static final String USER_ID = "UID";


    /* Shared Preferences */

    public static final String SHARED_PREF = "SHARED_PREF";


    /* KEYS FOR BUNDLES */
    public static final String KEY_LATITUDE = "USER_LATITUDE";
    public static final String KEY_LONGITUDE = "USER_LONGITUDE";
    public static final String KEY_USER_DATA = "USER_DATA";


    /* Firebase references*/

    public static final String FIREBASE_URL = "https://lifesource.firebaseio.com/";
    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_LOCATION_LATLNG = "locations";

    public static final String USER_LAT = "USER_LATITUDE";
    public static final String USER_LON = "USER_LONGITUDE";

    /* Geofencing constants */

    public static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    //public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    public static final float GEOFENCE_RADIUS_IN_METERS = 200; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("Area of Allergic Threat (Pollen)", new LatLng(23.8144277, 90.4263004));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("Area of Allergic Threat (Dust)", new LatLng(23.8153488, 90.4277751));

        // Test
        BAY_AREA_LANDMARKS.put("Area of Allergic Threat (Mold)", new LatLng(23.8153489, 90.4278329));
    }

}
