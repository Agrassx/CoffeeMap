package com.agrass.coffeemap.model;


public class CafeInfo {
    private String id;
    private float rating;
    private boolean isHaveRating = false;

    public CafeInfo(String id, float rating) {
        this.id = id;
        this.rating = rating;
        this.isHaveRating = true;
    }

    public CafeInfo(String id) {
        this.isHaveRating = false;
        this.id = id;
    }



    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.isHaveRating = true;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public boolean isHaveRating() {
        return isHaveRating;
    }
}
