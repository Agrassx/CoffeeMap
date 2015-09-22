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
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class CoffeeAPI {

    private JSONArray JsonCoffeeArray;
    private ArrayList CoffeeList = new ArrayList<OverlayItem>();


    public void setCoffeeOverlay(Context context, String url) {

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JsonCoffeeArray = response.getJSONArray("points");
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

    public String getName(int index) throws JSONException {
        return JsonCoffeeArray.getJSONObject(index).get("name").toString();
    }

    public double getLon(int index) throws JSONException {
        return JsonCoffeeArray.getJSONObject(index).getDouble("lon");
    }

    public double getLat(int index) throws JSONException {
        return JsonCoffeeArray.getJSONObject(index).getDouble("lat");
    }

    public int getId(int index) throws JSONException {
        return JsonCoffeeArray.getJSONObject(index).getInt("id");
    }

    public int length(){
        return JsonCoffeeArray.length();
    }
    public ArrayList<OverlayItem> getOverlay() {
            return CoffeeList;
    }
}
