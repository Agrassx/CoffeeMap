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

    private ArrayList CoffeeList = new ArrayList<OverlayItem>();
    private String urlMain = "http://78.47.49.234:9000/api/points?";


    public void setCoffeeOverlay(Context context, BoundingBoxE6 boundingBox) {

        Double north = boundingBox.getLatNorthE6()/1E6;
        Double south = boundingBox.getLatSouthE6()/1E6;
        Double west =  boundingBox.getLonWestE6()/1E6;
        Double east =  boundingBox.getLonEastE6()/1E6;
        String url = urlMain + "s="+south.toString()+"&"+"n="+north.toString()+"&"+"w="+west.toString()+"&"+"e="+east.toString();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray JsonCoffeeArray = response.getJSONArray("points");
                    for (int i = 0; i < JsonCoffeeArray.length() - 1; i++) {
                        JSONObject jsonObject = JsonCoffeeArray.getJSONObject(i);
                        CoffeeList.add(new OverlayItem("Title","Snippet", new GeoPoint(jsonObject.getDouble("lat"), jsonObject.getDouble("lon"))));
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

    public ArrayList<OverlayItem> getOverlay() {
            return CoffeeList;
    }
    public void clear() {
        CoffeeList.clear();
    }
}
