package com.agrass.coffeemap.model.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.agrass.coffeemap.model.MarkerColors;
import com.agrass.coffeemap.model.parsers.OpenHourParser;
import com.agrass.coffeemap.R;
import com.agrass.coffeemap.model.ImageUtil;
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
    private Calendar calendar;
    private Context context;
    private Bitmap greenMarker;
    private Bitmap greyMarker;
    private Bitmap redMarker;

    public ClusterItemCafeRender(Context context, GoogleMap map, ClusterManager<Cafe> clusterManager) {
        super(context, map, clusterManager);

        this.context = context;
        this.parser = new OpenHourParser();
        this.calendar = Calendar.getInstance();

        this.greenMarker = ImageUtil.getBitmapFromSVG(context, R.drawable.ic_place_green_36dp);
        this.greyMarker = ImageUtil.getBitmapFromSVG(context, R.drawable.ic_place_grey_36dp);
        this.redMarker = ImageUtil.getBitmapFromSVG(context, R.drawable.ic_place_red_36dp);

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
}
