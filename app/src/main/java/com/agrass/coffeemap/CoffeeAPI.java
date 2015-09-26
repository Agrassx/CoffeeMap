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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CoffeeAPI {

    private JSONArray JsonCoffeeArray;
    private ArrayList CoffeeList = new ArrayList<OverlayItem>();
    private String urlMain = "http://78.47.49.234:9000/api/points?";


    public void setCoffeeOverlay(Context context, BoundingBoxE6 boundingBox) {

        Double N = round(boundingBox.getLatNorthE6()/1E6, 2);
        Double S = round(boundingBox.getLatSouthE6()/1E6, 2);
        Double W = round(boundingBox.getLonWestE6()/1E6, 2);
        Double E = round(boundingBox.getLonEastE6()/1E6, 2);
        Log.wtf("N",N.toString());
        String url1 = urlMain + "s="+S.toString()+"&"+"n="+N.toString()+"&"+"w="+W.toString()+"&"+"e="+E.toString();
        String url = "http://78.47.49.234:9000/api/points?s=55.75&n=55.74&w=37.60&e=37.66";
        Log.wtf("URL", url);
        Log.wtf("URL1", url1);

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url1, new Response.Listener<JSONObject>() {

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

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public ArrayList<OverlayItem> getOverlay() {
            return CoffeeList;
    }
}
