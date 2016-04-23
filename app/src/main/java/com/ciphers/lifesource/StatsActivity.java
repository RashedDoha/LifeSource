package com.ciphers.lifesource;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ciphers.lifesource.model.UserData;
import com.ciphers.lifesource.utils.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;

import org.w3c.dom.Text;

public class StatsActivity extends BaseActivity {
    int cough, shortness, wheezing, sneezing, nasal,itchy, fever, flu, asthma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        initializeScreen();
    }

    private void initializeScreen() {
        Firebase ref = new Firebase(Constants.FIREBASE_URL).child(Constants.FIREBASE_LOCATION_USERS);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserData data = dataSnapshot.getValue(UserData.class);
                if (data.getHasCough()) {
                    cough++;
                }
                if (data.getHasShortness()) {
                    shortness++;
                }
                if (data.getHasWheezing()) {
                    wheezing++;
                }
                if (data.getHasSneezing()) {
                    sneezing++;
                }
                if (data.getHasNasalObstruction()) {
                    nasal++;
                }
                if (data.getHasAsthma()) {
                    asthma++;
                }
                if (data.getHasFever()) {
                    fever++;
                }
                if (data.getHasFlu()) {
                    flu++;
                }
                if (data.getHasAsthma()) {
                    asthma++;
                }
                updateUI();
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
    }

        private void updateUI() {
            TextView coughText = (TextView) findViewById(R.id.coughStat);
            TextView shortText = (TextView) findViewById(R.id.shortnessStat);
            TextView wheezingText = (TextView) findViewById(R.id.wheezingStat);
            TextView sneezingtext = (TextView) findViewById(R.id.sneezingStat);
            TextView nasalText = (TextView) findViewById(R.id.nasalStat);
            TextView itchyText = (TextView) findViewById(R.id.itchyStat);
            TextView feverText = (TextView) findViewById(R.id.feverStat);
            TextView fluText = (TextView) findViewById(R.id.fluStat);
            TextView asthmaText = (TextView) findViewById(R.id.ashtmaStat);

            coughText.setText(cough + "");
            shortText.setText(shortness + "");
            wheezingText.setText(wheezing + "");
            sneezingtext.setText(sneezing + "");
            nasalText.setText(nasal + "");
            itchyText.setText(itchy + "");
            feverText.setText(fever + "");
            fluText.setText(flu + "");
            asthmaText.setText(asthma + "");
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }


}
