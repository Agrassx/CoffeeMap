package com.agrass.coffeemap.model.cafe;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Cafe implements ClusterItem {

    private String id;
    private String name;
    private String opening_hours;
    private LocationCafe location;

    public LocationCafe getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOpeningHours() {
        return opening_hours;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(location.getLat(), location.getLon());
    }
}
