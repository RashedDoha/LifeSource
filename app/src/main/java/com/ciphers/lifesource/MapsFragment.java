package com.ciphers.lifesource;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciphers.lifesource.model.UserData;
import com.ciphers.lifesource.utils.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Rashed on 21/04/2016.
 */
public class MapsFragment extends Fragment{
    private static final String LOG_TAG = MapsFragment.class.getSimpleName();
    private Location userLocation;
    MapView mMapView;
    private GoogleMap googleMap;

    private Firebase locationRef, userRef;
    private GeoFire mGeofire;
    private GeoQuery geoQuery;
    private ArrayList<String> keys;
    private ArrayList<GeoLocation> locations;


    public MapsFragment() {

    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationRef = new Firebase(Constants.FIREBASE_URL).child(Constants.FIREBASE_LOCATION_LATLNG);
        userRef = new Firebase(Constants.FIREBASE_URL).child(Constants.FIREBASE_LOCATION_USERS);
        mGeofire = new GeoFire(locationRef);
        keys = new ArrayList<>();
        locations = new ArrayList<>();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initialize UI elements
         */
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                double lat = marker.getPosition().latitude;
                double lon = marker.getPosition().longitude;
                Query queryRef = userRef.orderByChild("latitude").equalTo(lat);
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        UserData data = dataSnapshot.getValue(UserData.class);
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        String jsonData = new Gson().toJson(data);
                        intent.putExtra(Constants.KEY_USER_DATA, jsonData);
                        startActivity(intent);
                        Log.d(LOG_TAG, "User Feedback: " + data.getUserFeedback());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                Log.d(LOG_TAG, lat + " " + lon);
                return false;
            }
        });
        // latitude and longitude
//        double latitude = 17.385044;
//        double longitude = 78.486671;
//
//        // create marker
//        MarkerOptions marker = new MarkerOptions().position(
//                new LatLng(latitude, longitude)).title("Hello Maps");
//
//        // Changing marker icon
//        marker.icon(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//        // adding marker
//        googleMap.addMarker(marker);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory
//                .newCameraPosition(cameraPosition));

        // Perform any camera updates here
        return v;
    }


    private void initializeScreen(View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void sendLocation(Location mUserLocation) {
        userLocation = mUserLocation;
        setMarker();
    }

    private void setMarker() {
        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = userLocation.getLatitude();
        double longitude = userLocation.getLongitude();

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude));

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(30).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        geoQuery = mGeofire.queryAtLocation(new GeoLocation(userLocation.getLatitude(), userLocation.getLongitude()), 5);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                // create marker
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(location.latitude, location.longitude));

                // Changing marker icon
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));


                googleMap.addMarker(marker);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                for (int i = 0; i < keys.size(); i++) {

                }
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(FirebaseError error) {

            }
        });



    }
}
