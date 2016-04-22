package com.ciphers.lifesource.model;

/**
 * Created by Rashed on 22/04/2016.
 */
public class UserData {
    private boolean hasCough;
    private boolean hasShortness;
    private boolean hasWheezing;
    private boolean hasSneezing;
    private boolean hasNasalObstruction;
    private boolean hasItchyEyes;
    private boolean hasFever;
    private boolean hasFlu;
    private boolean hasAsthma;
    private double latitude;
    private double longitude;

    private String additionalInfo;
    private String userFeedback;

    public UserData() {
    }

    public UserData(boolean hasCough, boolean hasShortness, boolean hasWheezing, boolean hasSneezing, boolean hasNasalObstruction, boolean hasItchyEyes, boolean hasFever, boolean hasFlu, boolean hasAsthma, String additionalInfo, String userFeedback, double latitude, double longitude) {
        this.hasCough = hasCough;
        this.hasShortness = hasShortness;
        this.hasWheezing = hasWheezing;
        this.hasSneezing = hasSneezing;
        this.hasNasalObstruction = hasNasalObstruction;
        this.hasItchyEyes = hasItchyEyes;
        this.hasFever = hasFever;
        this.hasFlu = hasFlu;
        this.hasAsthma = hasAsthma;
        this.additionalInfo = additionalInfo;
        this.userFeedback = userFeedback;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean getHasCough() {
        return hasCough;
    }

    public boolean getHasShortness() {
        return hasShortness;
    }

    public boolean isHasWheezing() {
        return hasWheezing;
    }

    public boolean getHasSneezing() {
        return hasSneezing;
    }

    public boolean getHasNasalObstruction() {
        return hasNasalObstruction;
    }

    public boolean getHasItchyEyes() {
        return hasItchyEyes;
    }


    public boolean getHasFever() {
        return hasFever;
    }

    public boolean getHasFlu() {
        return hasFlu;
    }

    public boolean getHasAsthma() {
        return hasAsthma;
    }


    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
