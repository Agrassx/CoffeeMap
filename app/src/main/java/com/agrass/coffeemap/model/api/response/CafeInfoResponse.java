package com.agrass.coffeemap.model.api.response;

import com.agrass.coffeemap.model.cafe.User;

public class CafeInfoResponse {

    private String id;
    private float rating;
    private User added_by;
    private boolean ok;
    private boolean found;

    public boolean isFound() {
        return found;
    }

    public boolean isOk() {
        return ok;
    }

    public float getRating() {
        return rating;
    }

    public String getId() {
        return id;
    }

    public User getAddedBy() {
        return added_by;
    }
}
