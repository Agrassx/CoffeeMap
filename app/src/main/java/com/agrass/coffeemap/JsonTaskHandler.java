package com.agrass.coffeemap;

import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public interface JsonTaskHandler {
    void taskSuccessful(ArrayList<OverlayItem> overlayItemArrayList);
    void taskFaild();
}
