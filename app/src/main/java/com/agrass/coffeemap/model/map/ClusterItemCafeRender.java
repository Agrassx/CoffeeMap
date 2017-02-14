package com.agrass.coffeemap.model.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.agrass.coffeemap.model.parsers.OpenHourParser;
import com.agrass.coffeemap.R;
import com.agrass.coffeemap.model.util.ImageUtil;
import com.agrass.coffeemap.model.cafe.Cafe;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.Calendar;


public class ClusterItemCafeRender extends DefaultClusterRenderer<Cafe> implements MarkerColors {

    private OpenHourParser parser;
    private Bitmap greenMarker;
    private Bitmap greyMarker;
    private Bitmap redMarker;
    private Bitmap blueMarker;

    public ClusterItemCafeRender(Context context, GoogleMap map, ClusterManager<Cafe> clusterManager) {
        super(context, map, clusterManager);

        this.parser = new OpenHourParser();

        this.greenMarker = ImageUtil.getBitmapFromSVG(context, R.drawable.ic_place_green_36dp);
        this.greyMarker = ImageUtil.getBitmapFromSVG(context, R.drawable.ic_place_grey_36dp);
        this.redMarker = ImageUtil.getBitmapFromSVG(context, R.drawable.ic_place_red_36dp);
        this.blueMarker = ImageUtil.getBitmapFromSVG(context, R.drawable.ic_place_36dp);

    }


    @Override
    public void setOnClusterClickListener(ClusterManager.OnClusterClickListener<Cafe> listener) {
        super.setOnClusterClickListener(listener);
    }

    @Override
    public void setOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<Cafe> listener) {
        super.setOnClusterItemClickListener(listener);
    }

    @Override
    protected void onBeforeClusterItemRendered(Cafe item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarker(
                        parser.getMarkerColor(item.getOpeningHours(), Calendar.DAY_OF_WEEK)
                )));
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    protected void onClusterItemRendered(Cafe clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<Cafe> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

    @Override
    protected void onClusterRendered(Cluster<Cafe> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);
    }

    @Override
    public Drawable getMarkerColor(int color) {
        return null;
    }

    @Override
    public Bitmap getMarker(int color) {
        switch (color) {
            case MARKER_COLOR_GREEN: return greenMarker;
            case MARKER_COLOR_GREY: return greyMarker;
            case MARKER_COLOR_RED: return redMarker;
            default: return greyMarker;
        }
    }

    public void setMarkerSelect(Cafe cafe) {
        getMarker(cafe).setIcon(BitmapDescriptorFactory.fromBitmap(blueMarker));
    }

    public void cancelSelection(Cafe cafe) {
        getMarker(cafe).setIcon(BitmapDescriptorFactory.fromBitmap(getMarker(
                parser.getMarkerColor(cafe.getOpeningHours(), Calendar.DAY_OF_WEEK)
        )));
    }
}
