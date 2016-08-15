package com.agrass.coffeemap.JsonParser;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.agrass.coffeemap.model.CafeItem;
import com.agrass.coffeemap.MarkerColors;
import com.agrass.coffeemap.OpenHourParser;
import com.agrass.coffeemap.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.util.Calendar;

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
                jsonItem.getString("_id"),
                jsonItem.getJSONObject("_source").getString("name"),
                new OpenHourParser().getOpenHours(
                        jsonItem.getJSONObject("_source").getString("opening_hours"),
                        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                ),
                jsonItem.getJSONObject("_source").getString("opening_hours"),
                new GeoPoint(
                        jsonItem.getJSONObject("_source").getJSONObject("location").getDouble("lat"),
                        jsonItem.getJSONObject("_source").getJSONObject("location").getDouble("lon")
                ),
                getMarkerColor(
                        new OpenHourParser().getMarkerColor(
                                jsonItem.getJSONObject("_source").getString("opening_hours"),
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
}
