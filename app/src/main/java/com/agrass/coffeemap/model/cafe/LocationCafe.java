package com.agrass.coffeemap.model.cafe;

public class LocationCafe {

    private double lat;
    private double lon;

    public LocationCafe() {

    }

    public LocationCafe(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
