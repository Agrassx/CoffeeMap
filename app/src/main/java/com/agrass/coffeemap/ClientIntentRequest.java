package com.agrass.coffeemap;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Calendar;


public class ClientIntentRequest extends IntentService implements MarkerColors {

    private static final String TAG_URL_MAIN = "http://78.47.49.234:9000/api/v2/";
    private static String TAG_JSON_ARRAY_NAME = "points";
    private ThreadLocal<Double> north = new ThreadLocal<>();
    private ThreadLocal<Double> south = new ThreadLocal<>();
    private ThreadLocal<Double> west = new ThreadLocal<>();
    private ThreadLocal<Double> east = new ThreadLocal<>();
    private ArrayList<CafeItem> coffeeList;
    private JsonTaskHandler taskHandler;
    private BoundingBoxE6 boundingBox;
    private Context context;
    private Drawable greenMarker;
    private Drawable blueMarker;
    private Drawable redMarker;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ClientIntentRequest(Context context) {
        super("ClientIntentRequest");
        this.context = context;
        coffeeList = new ArrayList<>();
        greenMarker = context.getResources().getDrawable(R.drawable.ic_place_green_36dp, null);
        redMarker = context.getResources().getDrawable(R.drawable.ic_place_red_36dp, null);
        blueMarker = context.getResources().getDrawable(R.drawable.ic_place_36dp, null);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                getFinaleUrl(boundingBox), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    coffeeList.clear();
                    JSONArray JsonCoffeeArray = response.getJSONArray("points");
                    int length = JsonCoffeeArray.length();
                    for (int i = 0; i < length; i++) {
                        CafeItem cafeItem = new CafeItem(JsonCoffeeArray.getJSONObject(i).getString("name"),
                                new OpenHourParser().getOpenHours(JsonCoffeeArray.getJSONObject(i).getString("opening_hours"),
                                        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)),
                                JsonCoffeeArray.getJSONObject(i).getString("opening_hours"),
                                new GeoPoint(JsonCoffeeArray.getJSONObject(i).getDouble("lat"),
                                        JsonCoffeeArray.getJSONObject(i).getDouble("lon")),
                                getMarkerColor(new OpenHourParser().getMarkerColor(JsonCoffeeArray.getJSONObject(i).getString("opening_hours"),
                                        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)))
                        );
                        coffeeList.add(cafeItem);
                    }
                    taskHandler.taskSuccessful(coffeeList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("Error!", error);
            }
        });
        queue.add(jsObjRequest);
    }

    private Drawable getMarkerColor(int color) {
        switch (color) {
            case MARKER_COLOR_GREEN: return greenMarker;
            case MARKER_COLOR_BLUE: return blueMarker;
            case MARKER_COLOR_RED: return redMarker;
            default: return blueMarker;
        }
    }

    public void setJsonTaskHandler(JsonTaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    public void setBoundingBox(BoundingBoxE6 boundingBox) {
        this.boundingBox = boundingBox;
    }

    private String getFinaleUrl(BoundingBoxE6 boundingBox) {
        this.north.set(boundingBox.getLatNorthE6() / 1E6);
        this.south.set(boundingBox.getLatSouthE6() / 1E6);
        this.west.set(boundingBox.getLonWestE6() / 1E6);
        this.east.set(boundingBox.getLonEastE6() / 1E6);
        return TAG_URL_MAIN + TAG_JSON_ARRAY_NAME + "?" + "n=" + north.get().toString() + "&s=" +
                south.get().toString() + "&w=" + west.get().toString() + "&e=" + east.get().toString();
    }

    public void setArrayName(String NameJsonArray) {
        TAG_JSON_ARRAY_NAME = NameJsonArray;
    }



}
