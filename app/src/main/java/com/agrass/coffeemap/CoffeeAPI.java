package com.agrass.coffeemap;

import android.content.Context;
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

public class CoffeeAPI {

    private String urlMain = "http://78.47.49.234:9000/api/points?";

    private ArrayList<OverlayItem> coffeeList;

    public void getCoffeeAPI(Context context, BoundingBoxE6 boundingBox) {


        Double north = boundingBox.getLatNorthE6()/1E6;
        Double south = boundingBox.getLatSouthE6()/1E6;
        Double west =  boundingBox.getLonWestE6()/1E6;
        Double east =  boundingBox.getLonEastE6()/1E6;
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

    }

    public ArrayList<OverlayItem> getOverlayList() {
            return coffeeList;
    }

    public void clear() {
        coffeeList.clear();
    }

    public CoffeeAPI() {
        coffeeList = new ArrayList<>();
    }
}
