package com.agrass.coffeemap;

public class Location {
    private double lat;
    private double lon;

    public Location (double latitude, double longitude) {
        this.lat = latitude;
        this.lon = longitude;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

}
