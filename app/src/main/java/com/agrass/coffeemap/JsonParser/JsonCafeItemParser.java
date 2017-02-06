package com.agrass.coffeemap.JsonParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.agrass.coffeemap.model.cafe.CafeItem;
import com.agrass.coffeemap.MarkerColors;
import com.agrass.coffeemap.model.parsers.OpenHourParser;
import com.agrass.coffeemap.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.util.Calendar;


@Deprecated
public class JsonCafeItemParser implements MarkerColors {

    private Drawable greenMarker;
    private Drawable greyMarker;
    private Drawable redMarker;

    public JsonCafeItemParser(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            greenMarker = context.getResources().getDrawable(R.drawable.ic_place_green_36dp, null);
            redMarker = context.getResources().getDrawable(R.drawable.ic_place_red_36dp, null);
            greyMarker = context.getResources().getDrawable(R.drawable.ic_place_grey_36dp, null);
        } else {
            greenMarker = context.getResources().getDrawable(R.drawable.ic_place_green_36dp);
            redMarker = context.getResources().getDrawable(R.drawable.ic_place_red_36dp);
            greyMarker = context.getResources().getDrawable(R.drawable.ic_place_grey_36dp);
        }
    }

    public CafeItem getCafeItem(JSONObject jsonItem) throws JSONException {
        return new CafeItem(
                jsonItem.getString("id"),
                jsonItem.getString("name"),
                new OpenHourParser().getOpenHours(
                        jsonItem.getString("opening_hours"),
                        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                ),
                jsonItem.getString("opening_hours"),
                new GeoPoint(
                        jsonItem.getJSONObject("location").getDouble("lat"),
                        jsonItem.getJSONObject("location").getDouble("lon")
                ),
                getMarkerColor(
                        new OpenHourParser().getMarkerColor(
                                jsonItem.getString("opening_hours"),
                                Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                        )
                )
        );
    }

    @Override
    public Drawable getMarkerColor(int color) {
        switch (color) {
            case MARKER_COLOR_GREEN: return greenMarker;
            case MARKER_COLOR_GREY: return greyMarker;
            case MARKER_COLOR_RED: return redMarker;
            default: return greyMarker;
        }
    }

    @Override
    public Bitmap getMarker(int color) {
        return null;
    }
}
