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

/**
 * Created by Agrass- on 22.09.15.
 */
public class CoffeeAPI {

    private JSONArray CoffeeArray;


    public CoffeeAPI(Context context, String url) {

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    CoffeeArray = response.getJSONArray("points");
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
        return CoffeeArray.getJSONObject(index).get("name").toString();
    }

    public double getLon(int index) throws JSONException {
        return CoffeeArray.getJSONObject(index).getDouble("lon");
    }

    public double getLat(int index) throws JSONException {
        return CoffeeArray.getJSONObject(index).getDouble("lat");
    }

    public int getId(int index) throws JSONException {
        return CoffeeArray.getJSONObject(index).getInt("id");
    }
}
