package com.agrass.coffeemap;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
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


public class ClientIntentRequest extends IntentService implements Response.Listener<JSONObject>, Response.ErrorListener, MarkerColors {

    private static final String TAG_URL_MAIN = BuildConfig.ServerAdress;
    private static final String TAG_URL_ADD_POINT = BuildConfig.ServerAdress + "addPoint";
    private static String TAG_JSON_ARRAY_NAME = "points";
    private ThreadLocal<Double> north = new ThreadLocal<>();
    private ThreadLocal<Double> south = new ThreadLocal<>();
    private ThreadLocal<Double> west = new ThreadLocal<>();
    private ThreadLocal<Double> east = new ThreadLocal<>();
    private ArrayList<CafeItem> coffeeList;
    private TaskGetPointsHandler taskGetPointsHandler;
    private Context context;
    private Drawable greenMarker;
    private Drawable blueMarker;
    private Drawable redMarker;
    private RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ClientIntentRequest() {
        super("");
    }

    public ClientIntentRequest(Context context) {
        super("ClientIntentRequest");
        this.context = context;
        coffeeList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            greenMarker = context.getResources().getDrawable(R.drawable.ic_place_green_36dp, null);
            redMarker = context.getResources().getDrawable(R.drawable.ic_place_red_36dp, null);
            blueMarker = context.getResources().getDrawable(R.drawable.ic_place_36dp, null);
        } else {
            greenMarker = context.getResources().getDrawable(R.drawable.ic_place_green_36dp);
            redMarker = context.getResources().getDrawable(R.drawable.ic_place_red_36dp);
            blueMarker = context.getResources().getDrawable(R.drawable.ic_place_36dp);
        }

    }

    @Override
    public void onHandleIntent(Intent intent) {

    }

    private Drawable getMarkerColor(int color) {
        switch (color) {
            case MARKER_COLOR_GREEN: return greenMarker;
            case MARKER_COLOR_BLUE: return blueMarker;
            case MARKER_COLOR_RED: return redMarker;
            default: return blueMarker;
        }
    }

    public RequestQueue getQueue() {
        if (queue == null) {
            return queue = Volley.newRequestQueue(context);
        } else {
            return queue;
        }
    }

    public void refreshPoints(BoundingBoxE6 boundingBox) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, getFinaleUrl(boundingBox), this, this);
        getQueue().add(jsObjRequest);
    }

    public void addPoint(JSONObject jsonNewPoint) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, TAG_URL_ADD_POINT,
                jsonNewPoint, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.wtf("Post New Point Answer", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("Post New Point Error", error.getMessage());
            }
        });
        getQueue().add(jsonObjectRequest);
    }

    public void setJsonTaskHandler(TaskGetPointsHandler taskHandler) {
        this.taskGetPointsHandler = taskHandler;
    }

    private String getFinaleUrl(BoundingBoxE6 boundingBox) {
        this.north.set(boundingBox.getLatNorthE6() / 1E6);
        this.south.set(boundingBox.getLatSouthE6() / 1E6);
        this.west.set(boundingBox.getLonWestE6() / 1E6);
        this.east.set(boundingBox.getLonEastE6() / 1E6);
        return TAG_URL_MAIN + "points?" + "n=" + north.get().toString() + "&s=" +
                south.get().toString() + "&w=" + west.get().toString() + "&e=" + east.get().toString();
    }

    public void setArrayName(String NameJsonArray) {
        TAG_JSON_ARRAY_NAME = NameJsonArray;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.wtf("Error!", error);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            coffeeList.clear();
            JSONArray jsonCoffeeArray = response.getJSONArray(TAG_JSON_ARRAY_NAME);
            int length = jsonCoffeeArray.length();
            for (int i = 0; i < length; i++) {
                CafeItem cafeItem = new CafeItem(jsonCoffeeArray.getJSONObject(i).getString("name"),
                        new OpenHourParser().getOpenHours(jsonCoffeeArray.getJSONObject(i).getString("opening_hours"),
                                Calendar.getInstance().get(Calendar.DAY_OF_WEEK)),
                        jsonCoffeeArray.getJSONObject(i).getString("opening_hours"),
                        new GeoPoint(jsonCoffeeArray.getJSONObject(i).getJSONObject("location").getDouble("lat"),
                                jsonCoffeeArray.getJSONObject(i).getJSONObject("location").getDouble("lon")),
                        getMarkerColor(new OpenHourParser().getMarkerColor(jsonCoffeeArray.getJSONObject(i).getString("opening_hours"),
                                Calendar.getInstance().get(Calendar.DAY_OF_WEEK)))
                );
                coffeeList.add(cafeItem);
            }
            taskGetPointsHandler.taskSuccessful(coffeeList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
