package com.ciphers.lifesource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rashed on 16/04/2016.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
