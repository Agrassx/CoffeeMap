package com.agrass.coffeemap;

import android.graphics.drawable.Drawable;

@Deprecated
public interface MarkerColors {
    int MARKER_COLOR_RED = 0;
    int MARKER_COLOR_GREEN = 1;
    int MARKER_COLOR_GREY = 2;

    Drawable getMarkerColor(int color);
}
