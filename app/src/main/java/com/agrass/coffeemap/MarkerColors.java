package com.agrass.coffeemap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;



public interface MarkerColors {
    int MARKER_COLOR_RED = 0;
    int MARKER_COLOR_GREEN = 1;
    int MARKER_COLOR_GREY = 2;

    @Deprecated
    Drawable getMarkerColor(int color);

    Bitmap getMarker(int color);
}
