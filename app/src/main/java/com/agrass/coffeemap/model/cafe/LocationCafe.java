package com.agrass.coffeemap.model.cafe;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class LocationCafe {

    private double lat;
    private double lon;

    public LocationCafe(LatLng location) {
        this.lat = location.latitude;
        this.lon = location.longitude;
    }

    public LocationCafe(String jsonLatLng) {
        LatLng point = new Gson().fromJson(jsonLatLng, LatLng.class);
        new LocationCafe(point);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
