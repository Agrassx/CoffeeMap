package com.agrass.coffeemap;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class CoffeeItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> coffeeList;

    public CoffeeItemizedOverlay(Drawable pDefaultMarker, ResourceProxy pResourceProxy) {
        super(pDefaultMarker, pResourceProxy);
        coffeeList = new ArrayList<>();
        populate();
    }

    @Override
    protected OverlayItem createItem(int index) {
        return coffeeList.get(index);
    }

    public void addAll(ArrayList list) {
        this.coffeeList = list;
        populate();
    }

    @Override
    public int size() {
        return coffeeList.size();
    }

    @Override
    public boolean onSnapToItem(int x, int y, Point snapPoint, IMapView mapView) {
        return false;
    }

    public void removeItem(int index){
        coffeeList.remove(index);
        populate();
    }

    public void clear() {
        coffeeList.clear();
        populate();
    }
}
