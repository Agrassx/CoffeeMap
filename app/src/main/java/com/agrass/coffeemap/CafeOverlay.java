package com.agrass.coffeemap;

import android.graphics.drawable.Drawable;

import com.agrass.coffeemap.model.CafeItem;

import org.osmdroid.ResourceProxy;
import org.osmdroid.views.overlay.ItemizedIconOverlay;

import java.util.ArrayList;
import java.util.List;

public class CafeOverlay extends ItemizedIconOverlay<CafeItem> {

    protected List<CafeItem> itemList;
    protected Drawable defaultMarker;

    public CafeOverlay(ArrayList<CafeItem> pList, Drawable pDefaultMarker, OnItemGestureListener<CafeItem> pOnItemGestureListener, ResourceProxy pResourceProxy) {
        super(pList, pDefaultMarker, pOnItemGestureListener, pResourceProxy);
        this.itemList = pList;
        this.defaultMarker = pDefaultMarker;
        populate();
    }

    @Override
    protected Drawable getDefaultMarker(int state) {
        CafeItem.setState(defaultMarker, state);
        return defaultMarker;
    }

    @Override
    public boolean addItem(CafeItem item) {
        return super.addItem(item);
    }

    @Override
    public boolean addItems(List<CafeItem> overlayItems) {
        return super.addItems(overlayItems);
    }

    @Override
    public boolean removeItem(CafeItem item) {
        return super.removeItem(item);
    }
}
