package com.ciphers.lifesource;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Rashed on 16/04/2016.
 */
public class LifeSourceApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Firebase.setAndroidContext(this);
    }
}
