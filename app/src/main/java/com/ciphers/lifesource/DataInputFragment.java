package com.ciphers.lifesource;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;


public class DataInputFragment extends Fragment {
    ArrayAdapter<CharSequence> spinnerAdapter;

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
        Spinner spinner = (Spinner) rootView.findViewById(R.id.allergy_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.allergen_array, simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }


}