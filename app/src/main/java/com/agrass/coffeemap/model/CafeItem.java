package com.agrass.coffeemap.model;

import android.graphics.drawable.Drawable;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class CafeItem extends OverlayItem {
    private Drawable aMarker;
    private String id;
    private String name;
    private String endTimeWork;
    private String scheduleWork;
    private float rating;

    public CafeItem(String aName, String aEndTimeWork, String aScheduleWork, IGeoPoint aGeoPoint, Drawable marker) {
        super(aName, aEndTimeWork, aScheduleWork, aGeoPoint);
        this.aMarker = marker;
        this.endTimeWork = aEndTimeWork;
        this.scheduleWork = aScheduleWork;
        this.name = aName;
    }

    public CafeItem(String aName, String aEndTimeWork, String aScheduleWork, float rating, IGeoPoint aGeoPoint, Drawable marker) {
        super(aName, aEndTimeWork, aScheduleWork, aGeoPoint);
        this.aMarker = marker;
        this.endTimeWork = aEndTimeWork;
        this.scheduleWork = aScheduleWork;
        this.name = aName;
        this.rating = rating;
    }

    public CafeItem(String id, String aName, String aEndTimeWork, String aScheduleWork, IGeoPoint aGeoPoint, Drawable marker) {
        super(aName, aEndTimeWork, aScheduleWork, aGeoPoint);
        this.id = id;
        this.aMarker = marker;
        this.endTimeWork = aEndTimeWork;
        this.scheduleWork = aScheduleWork;
        this.name = aName;
    }

    @Override
    public void setMarker(Drawable marker) {
        this.aMarker = marker;
    }



    @Override
    public Drawable getMarker(int stateBitset) {
        // marker not specified
        if (aMarker == null) {
            return null;
        }
        // set marker state appropriately
        setState(aMarker, stateBitset);
        return aMarker;
    }

    @Override
    public Drawable getDrawable() {
        return aMarker;
    }

    public String getScheduleWork() {
        return scheduleWork;
    }

    public String getEndTimeWork() {
        return endTimeWork;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public String getId() {
        return id;
    }
}
