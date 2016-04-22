package com.ciphers.lifesource;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciphers.lifesource.utils.Constants;

/**
 * Created by Rashed on 21/04/2016.
 */
public class MapsFragment extends Fragment{

    double userLat, userLong;


    public MapsFragment() {

    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static MapsFragment newInstance(Location mLocation) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
//        args.putDouble(Constants.KEY_LATITUDE, mLocation.getLatitude());
//        args.putDouble(Constants.KEY_LONGITUDE, mLocation.getLongitude());
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

        if (getArguments() != null) {
            userLat = getArguments().getDouble(Constants.KEY_LATITUDE);
            userLong = getArguments().getDouble(Constants.KEY_LONGITUDE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initialize UI elements
         */
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        initializeScreen(rootView);



        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initializeScreen(View rootView) {

    }
}
