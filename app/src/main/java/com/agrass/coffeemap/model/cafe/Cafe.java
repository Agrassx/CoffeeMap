package com.agrass.coffeemap.model.cafe;

public class Cafe {

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
}
