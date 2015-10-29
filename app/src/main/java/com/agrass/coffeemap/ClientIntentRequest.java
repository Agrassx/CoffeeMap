package com.agrass.coffeemap;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
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
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class ClientIntentRequest extends IntentService {

    private ThreadLocal<Double> north = new ThreadLocal<>();
    private ThreadLocal<Double> south = new ThreadLocal<>();
    private ThreadLocal<Double> west = new ThreadLocal<>();
    private ThreadLocal<Double> east = new ThreadLocal<>();
    private BoundingBoxE6 boundingBox;
    private ArrayList<OverlayItem> coffeeList;
    private Context context;
    private JsonTaskHandler taskHandler;
    private static String TAG_JSON_ARRAY_NAME = "points";
    private static final String TAG_URL_MAIN = "http://78.47.49.234:9000/api/";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ClientIntentRequest(Context context) {
        super("ClientIntentRequest");
        this.context = context;
        coffeeList = new ArrayList<>();
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
                    for (int i = 0; i < 30; i++) { //JsonCoffeeArray.length() - 1
                        coffeeList.add(new OverlayItem(JsonCoffeeArray.getJSONObject(i).getString("name"),
                                "Snippet", new GeoPoint(JsonCoffeeArray.getJSONObject(i).getDouble("lat"),
                                JsonCoffeeArray.getJSONObject(i).getDouble("lon"))));
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


    public void setJsonTaskHandler(JsonTaskHandler taskHandler) {
        this.taskHandler = taskHandler;

    }

    public void setBbox(BoundingBoxE6 boundingBox) {
        this.boundingBox = boundingBox;
    }

    private String getFinaleUrl(BoundingBoxE6 bbox) {
        this.north.set(bbox.getLatNorthE6() / 1E6);
        this.south.set(bbox.getLatSouthE6() / 1E6);
        this.west.set(bbox.getLonWestE6() / 1E6);
        this.east.set(bbox.getLonEastE6() / 1E6);
        return TAG_URL_MAIN + TAG_JSON_ARRAY_NAME + "?" + "n=" + north.get().toString() + "&s=" +
                south.get().toString() + "&w=" + west.get().toString() + "&e=" + east.get().toString();
    }

    public void setArrayName(String NameJsonArray) {
        TAG_JSON_ARRAY_NAME = NameJsonArray;
    }

}
