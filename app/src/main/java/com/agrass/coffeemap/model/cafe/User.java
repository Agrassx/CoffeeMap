package com.agrass.coffeemap.model.cafe;

public class User {
    private String user_id;
    private String user_name;
    private String user_photo_url;

    public User (String userID, String userName, String userPhotoUrl) {
        this.user_id = userID;
        this.user_name = userName;
        this.user_photo_url = userPhotoUrl;
    }

    public String getUserID() {
        return user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public String getUserPhotoUrl() {
        return user_photo_url;
    }
}
