package com.ciphers.lifesource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ciphers.lifesource.login.LoginActivity;
import com.ciphers.lifesource.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends BaseActivity{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClientLocation;
    private GoogleApiClient.ConnectionCallbacks mCallbacksLocation;
    private GoogleApiClient.OnConnectionFailedListener mFailedListenerLocation;
    private LocationListener locationListener;
    private Location mUserLocation;
    private Firebase rootRef;
    private SharedPreferences prefs;
    private DataInputFragment dataInputFragment;

    public static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        prefs = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);


        /**
         * Link layout elements from XML and setup the toolbar
         */
        authWithFirebase();
        initializeScreen();
        setupListners();
        buildGoogleApiClient();

    }

    private void authWithFirebase() {
        rootRef = new Firebase(Constants.FIREBASE_URL);
        rootRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

            }
        });
    }

    private void setupListners() {
        mCallbacksLocation = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                Log.d(LOG_TAG, "Connected!");
                mUserLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClientLocation);
                if(mUserLocation != null)
                {

                    dataInputFragment.sendLocation(mUserLocation);
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d(LOG_TAG, "Connection suspended!");
            }
        };

        mFailedListenerLocation = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.d(LOG_TAG, "Connection Failed!");
            }
        };


    }

    private void buildGoogleApiClient() {
        mGoogleApiClientLocation = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mCallbacksLocation)
                .addOnConnectionFailedListener(mFailedListenerLocation)
                .build();
    }


    /**
     * Override onOptionsItemSelected to use main_menu instead of BaseActivity menu
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Override onOptionsItemSelected to add action_settings only to the MainActivity
     *
     * @param item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_logout:
                rootRef.unauth();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClientLocation.connect();
    }

    @Override
    protected void onStop() {
        if(mGoogleApiClientLocation.isConnected()) mGoogleApiClientLocation.disconnect();
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Link layout elements from XML and setup the toolbar
     */
    public void initializeScreen() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        /**
         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(2)
         **/
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        /**
         * Setup the mTabLayout with view pager
         */
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    /**
     * SectionPagerAdapter class that extends FragmentStatePagerAdapter to save fragments state
     */
    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Use positions (0 and 1) to find and instantiate fragments with newInstance()
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
            switch (position) {
                case 0:
                    fragment = DataInputFragment.newInstance();
                    dataInputFragment = (DataInputFragment) fragment;
                    break;
                case 1:
                    fragment = MapsFragment.newInstance();

                    break;
                default:
                    fragment = DataInputFragment.newInstance();
                    break;
            }

            return fragment;
        }




        @Override
        public int getCount() {
            return 2;
        }

        /**
         * Set string resources as titles for each fragment by it's position
         *
         * @param position
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.pager_title_data_input);
                case 1:
                default:
                    return getString(R.string.pager_title_maps);
            }
        }
    }

}
