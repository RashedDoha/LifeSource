package com.ciphers.lifesource;

import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ciphers.lifesource.model.UserData;
import com.ciphers.lifesource.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;


public class DataInputFragment extends Fragment implements View.OnClickListener{
    private final String LOG_TAG = DataInputFragment.class.getSimpleName();
    ArrayAdapter<CharSequence> spinnerAdapter;
    private CheckBox coughCheckbox, wheezingCheckbox, sneezingCheckbox,
    shortnessCheckbox, nasalCheckbox,itchyCheckbox,feverCheckbox,
    fluCheckbox, asthmaCheckbox;
    private Button submitButton;
    private EditText allergyEditText, additionalEditText, feedbackEditText;
    private Spinner spinner;
    private Firebase rootRef;
    private Firebase userRef;
    private String uid;
    private Location userLocation;
    private GeoFire userGeofire;


    public DataInputFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static DataInputFragment newInstance() {
        DataInputFragment fragment = new DataInputFragment();
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
        rootRef = new Firebase(Constants.FIREBASE_URL);
        rootRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    uid = authData.getUid();
                    if (uid == null) {
                        getActivity().finish();
                    } else {
                        userRef = rootRef.child(uid);
                        Log.d(LOG_TAG, authData.getUid());
                    }

                }
            }
        });
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initialize UI elements
         */
        View rootView = inflater.inflate(R.layout.fragment_data_input, container, false);
        initializeScreen(rootView);



        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        coughCheckbox = (CheckBox) rootView.findViewById(R.id.coughCheckbox);
        shortnessCheckbox = (CheckBox) rootView.findViewById(R.id.shortnessCheckbox);
        wheezingCheckbox = (CheckBox) rootView.findViewById(R.id.wheezingCheckbox);
        sneezingCheckbox = (CheckBox) rootView.findViewById(R.id.sneezingCheckbox);
        nasalCheckbox = (CheckBox) rootView.findViewById(R.id.nasalCheckbox);
        itchyCheckbox = (CheckBox) rootView.findViewById(R.id.itcyCheckbox);
        feverCheckbox = (CheckBox) rootView.findViewById(R.id.feverCheckbox);
        fluCheckbox = (CheckBox) rootView.findViewById(R.id.fluCheckbox);
        asthmaCheckbox = (CheckBox) rootView.findViewById(R.id.asthmaCheckbox);
        submitButton = (Button) rootView.findViewById(R.id.submitButton);
//        allergyEditText = (EditText) rootView.findViewById(R.id.allergyEditText);
        additionalEditText = (EditText) rootView.findViewById(R.id.additionalEditText);
        feedbackEditText = (EditText) rootView.findViewById(R.id.feedbackEditText);
        spinner = (Spinner) rootView.findViewById(R.id.allergy_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.allergen_array, simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        submitButton.setOnClickListener(this);
//        spinner.setOnItemSelectedListener(new AllergySelectionListener());

    }

    @Override
    public void onClick(View v) {
        UserData data = new UserData(
                coughCheckbox.isChecked(),
                shortnessCheckbox.isChecked(),
                wheezingCheckbox.isChecked(),
                sneezingCheckbox.isChecked(),
                nasalCheckbox.isChecked(),
                itchyCheckbox.isChecked(),
                feverCheckbox.isChecked(),
                fluCheckbox.isChecked(),
                asthmaCheckbox.isChecked(),
                additionalEditText.getText().toString(),
                feedbackEditText.getText().toString(),
                userLocation.getLatitude(),
                userLocation.getLongitude()
        );

        HashMap<String, Object> updatedProperties = new HashMap<>();
        HashMap<String, Object> listItemMap = (HashMap<String, Object>) new ObjectMapper().convertValue(data, Map.class);

        updatedProperties.put("/" + Constants.FIREBASE_LOCATION_USERS + "/" + uid, listItemMap);
        rootRef.updateChildren(updatedProperties);

        userGeofire = new GeoFire(rootRef.child(Constants.FIREBASE_LOCATION_LATLNG));
        userGeofire.setLocation(uid, new GeoLocation(userLocation.getLatitude(), userLocation.getLongitude()), new GeoFire.CompletionListener(){
            @Override
            public void onComplete(String key, FirebaseError error) {
                if(error != null) Log.d(LOG_TAG, "There was an error saving the location to Geofire: " + error);
                else Log.d(LOG_TAG, "Location saved on server successfully!");
            }
        });

    }

    public void sendLocation(Location mUserLocation) {
        userLocation = mUserLocation;
        //Toast.makeText(this.getActivity(), "Called from Fragment", Toast.LENGTH_LONG).show();
    }

    private class AllergySelectionListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //if(position == 4) allergyEditText.setEnabled(true);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }




}
