package com.agrass.coffeemap;

import android.graphics.drawable.Drawable;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class CafeItem extends OverlayItem {
    private Drawable aMarker;

    public CafeItem(String aName, String aEndTimeWork, String aSchedule, IGeoPoint aGeoPoint, Drawable marker) {
        super(aName, aEndTimeWork, aSchedule, aGeoPoint);
        setMarker(marker);
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

}
