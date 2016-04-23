package com.ciphers.lifesource;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ciphers.lifesource.model.UserData;
import com.ciphers.lifesource.utils.Constants;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String jsonData = getIntent().getStringExtra(Constants.KEY_USER_DATA);
        UserData data = new Gson().fromJson(jsonData, UserData.class);
        StringBuilder builder = new StringBuilder();
        if(data.getHasCough())
        {
            builder.append("Cough" + "\n");
        }
        if(data.getHasShortness())
        {
            builder.append("Shortness of Breath" + "\n");
        }
        if(data.getHasWheezing())
        {
            builder.append("Wheezing" + "\n");
        }
        if(data.getHasSneezing())
        {
            builder.append("Sneezing" + "\n");
        }
        if(data.getHasNasalObstruction())
        {
            builder.append("Nasal Obstruction" + "\n");
        }
        String symptoms = builder.toString();
        TextView symptomTextView = (TextView) findViewById(R.id.symptomsTextView);
        symptomTextView.setText(symptoms);

        builder = new StringBuilder();
        if(data.getHasFever())
        {
            builder.append("Fever" + "\n");
        }
        if(data.getHasFlu())
        {
            builder.append("Flu" + "\n");
        }
        if(data.getHasAsthma())
        {
            builder.append("Asthma" + "\n");
        }

        symptoms = builder.toString();
        if(symptoms.equals("")) symptoms = "No Input";
        TextView diseasesText = (TextView) findViewById(R.id.diseasesTextView);
        diseasesText.setText(symptoms);

        TextView additionalText = (TextView) findViewById(R.id.additionalTextView);
        if(!data.getAdditionalInfo().equals("")) {
            additionalText.setText(data.getAdditionalInfo());
        } else additionalText.setText("No Additional Info Provided");
        TextView feedbackText = (TextView) findViewById(R.id.feedbackTextView);
        if(!data.getUserFeedback().equals("")) {
            feedbackText.setText(data.getUserFeedback());
        } else feedbackText.setText("No User Feedback Provided");
    }

}
