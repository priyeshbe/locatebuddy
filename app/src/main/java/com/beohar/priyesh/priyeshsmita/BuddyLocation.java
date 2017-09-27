package com.beohar.priyesh.priyeshsmita;

/**
 * Created by priyesh on 23/9/17.
 */

public class BuddyLocation {

    private String lat;
    private String lng;

    public BuddyLocation(){    }

    public BuddyLocation(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() { return lat; }
    public void setLat(String lat) { this.lat = lat; }
    public String getLng() { return lng; }
    public void setLng(String lng) { this.lng = lng; }
}
