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

public class CoffeeAPI extends IntentService {

    private String urlMain = "http://78.47.49.234:9000/api/points?";
    private ArrayList<OverlayItem> coffeeList;
    private Double north;
    private Double south;
    private Double west;
    private Double east;
    private Context context;

    public CoffeeAPI() {
        super("CoffeeAPI");
        coffeeList = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void getBbox(Context context, BoundingBoxE6 boundingBox) {
        this.north = boundingBox.getLatNorthE6()/1E6;
        this.south = boundingBox.getLatSouthE6()/1E6;
        this.west =  boundingBox.getLonWestE6()/1E6;
        this.east =  boundingBox.getLonEastE6()/1E6;
        this.context = context;
    }

    private void refreshCoffeeList() {

        String url = urlMain + "n="+north.toString()+"&"+"s="+south.toString()+"&"+"w="+west.toString()+"&"+"e="+east.toString();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    coffeeList.clear();
                    JSONArray JsonCoffeeArray = response.getJSONArray("points");
                    for (int i = 0; i < JsonCoffeeArray.length() - 1; i++) {
                        JSONObject jsonObject = JsonCoffeeArray.getJSONObject(i);
                        coffeeList.add(new OverlayItem(jsonObject.getString("name"), "Snippet", new GeoPoint(jsonObject.getDouble("lat"), jsonObject.getDouble("lon"))));
                    }
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
        Log.wtf("API", "Request ended");

    }

    public ArrayList<OverlayItem> getOverlayList() {
            return coffeeList;
    }

    public void clear() {
        coffeeList.clear();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        refreshCoffeeList();
    }


}
