package com.agrass.coffeemap;

import android.graphics.drawable.Drawable;

import org.osmdroid.ResourceProxy;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

public class CoffeeOverlay extends ItemizedIconOverlay<OverlayItem> implements ItemizedOverlay.OnFocusChangeListener {

    protected List<OverlayItem> itemList;

    public CoffeeOverlay(List<OverlayItem> pList, Drawable pDefaultMarker, OnItemGestureListener<OverlayItem> pOnItemGestureListener, ResourceProxy pResourceProxy) {
        super(pList, pDefaultMarker, pOnItemGestureListener, pResourceProxy);
        this.itemList = pList;
        populate();
    }



    public boolean isEmpty() {
        if (itemList.isEmpty())  {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addItem(OverlayItem item) {
        return super.addItem(item);
    }

    @Override
    public boolean addItems(List<OverlayItem> overlayItems) {
        return super.addItems(overlayItems);
    }

    @Override
    public boolean removeItem(OverlayItem item) {
        return super.removeItem(item);
    }

    @Override
    public void onFocusChanged(ItemizedOverlay<?> overlay, OverlayItem newFocus) {
        populate();
    }
}
