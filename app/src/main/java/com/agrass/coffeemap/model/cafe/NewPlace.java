package com.agrass.coffeemap.model.cafe;

import android.net.Uri;

public class NewPlace {

    private LocationCafe location;
    private String name;
    private float rating;
    private String opening_hours;
    private String comment;
    private String user_id;
    private String user_name;
    private String user_photo_url;
    private String access_token;

    public NewPlace(LocationCafe location, String name, float rating, String opening_hours,
                    String comment, String user_id, String user_name, Uri user_photo_url,
                    String access_token) {
        this.location = location;
        this.name = name;
        this.rating = rating;
        this.opening_hours = opening_hours;
        this.comment = comment;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_id = user_id;
        this.access_token = access_token;
        if (user_photo_url != null) {
            this.user_photo_url = user_photo_url.toString();
        }
    }

    public float getRating() {
        return rating;
    }

    public String getOpeningHours() {
        return opening_hours;
    }

    public LocationCafe getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
