package com.agrass.coffeemap.model.map;

import android.graphics.drawable.Drawable;

import com.agrass.coffeemap.model.cafe.CafeItem;

import org.osmdroid.views.overlay.ItemizedIconOverlay;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class CafeOverlay extends ItemizedIconOverlay<CafeItem> {

    protected List<CafeItem> itemList;
    protected Drawable defaultMarker;

    public CafeOverlay(ArrayList<CafeItem> pList, Drawable pDefaultMarker, OnItemGestureListener<CafeItem> pOnItemGestureListener) {
        super(pList, pDefaultMarker, pOnItemGestureListener, null);
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
