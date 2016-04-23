package com.ciphers.lifesource;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * Created by Rashed on 23/04/2016.
 */
public class NasaViewFragment extends Fragment{
    private Location userLocation;
    private WebView webView;

    public NasaViewFragment() {
    }

    public static NasaViewFragment newInstance() {
        NasaViewFragment fragment = new NasaViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initialize UI elements
         */
        View rootView = inflater.inflate(R.layout.fragment_nasa, container, false);

        webView = (WebView)rootView.findViewById(R.id.webView);

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);



        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initializeScreen(View rootView) {


    }

    public void sendLocation(Location mUserLocation) {
        userLocation = mUserLocation;
        String baseURL = "http://earth.nullschool.net/#current/wind/surface/level/patterson=";
        StringBuilder builder = new StringBuilder();
        builder.append(baseURL);
        builder.append(userLocation.getLongitude() + "," + userLocation.getLatitude() + ",1000" + "/loc="+
        userLocation.getLongitude() + "," + userLocation.getLatitude());
        webView.loadUrl(builder.toString());
    }
}
