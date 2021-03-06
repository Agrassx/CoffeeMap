package com.agrass.coffeemap.REST;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.agrass.coffeemap.BuildConfig;
import com.agrass.coffeemap.CafeItem;
import com.agrass.coffeemap.MarkerColors;
import com.agrass.coffeemap.OpenHourParser;
import com.agrass.coffeemap.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class ClientIntentRequest extends IntentService implements Response.Listener<JSONObject>, Response.ErrorListener, MarkerColors {

    private static final String URL_MAIN = BuildConfig.ServerAdress;
    private static final String URL_ADD_POINT = BuildConfig.ServerAdress + "addPoint";
    private static final String URL_STATUS = BuildConfig.ServerAdress + "status";
    private static final String URL_TOKEN = BuildConfig.ServerAdress + "testValidate?token=";
    private static String JSON_ARRAY_NAME = "points";
    private ThreadLocal<Double> north = new ThreadLocal<>();
    private ThreadLocal<Double> south = new ThreadLocal<>();
    private ThreadLocal<Double> west = new ThreadLocal<>();
    private ThreadLocal<Double> east = new ThreadLocal<>();
    private ArrayList<CafeItem> coffeeList;
    private TaskGetPointsHandler taskGetPointsHandler;
    private TaskStatusHandler taskStatusHandler;
    private Context context;
    private Drawable greenMarker;
    private Drawable greyMarker;
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
            greyMarker = context.getResources().getDrawable(R.drawable.ic_place_grey_36dp, null);
        } else {
            greenMarker = context.getResources().getDrawable(R.drawable.ic_place_green_36dp);
            redMarker = context.getResources().getDrawable(R.drawable.ic_place_red_36dp);
            greyMarker = context.getResources().getDrawable(R.drawable.ic_place_grey_36dp);
        }

    }

    @Override
    public void onHandleIntent(Intent intent) {

    }

    private Drawable getMarkerColor(int color) {
        switch (color) {
            case MARKER_COLOR_GREEN: return greenMarker;
            case MARKER_COLOR_GREY: return greyMarker;
            case MARKER_COLOR_RED: return redMarker;
            default: return greyMarker;
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL_ADD_POINT,
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

    public void validateToken(String token) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_TOKEN + token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.wtf("Token Answer", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("Status Error", error.getMessage());
            }
        });
        getQueue().add(jsonObjectRequest);
    }

    public void getApiVersion() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_STATUS,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    taskStatusHandler.taskSuccessful(response.getString("version"));
                } catch (JSONException e) {
                    taskStatusHandler.taskFailed();
                }
                Log.wtf("Status Answer", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                taskStatusHandler.taskFailed();
                Log.wtf("Status Error", error.getMessage());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500, 0, 0));
        getQueue().add(jsonObjectRequest);
    }

    public void setTaskGetPointsHandler(TaskGetPointsHandler taskHandler) {
        this.taskGetPointsHandler = taskHandler;
    }

    public void setTaskStatusHandler(TaskStatusHandler taskStatusHandler) {
        this.taskStatusHandler = taskStatusHandler;
    }

    private String getFinaleUrl(BoundingBoxE6 boundingBox) {
        this.north.set(boundingBox.getLatNorthE6() / 1E6);
        this.south.set(boundingBox.getLatSouthE6() / 1E6);
        this.west.set(boundingBox.getLonWestE6() / 1E6);
        this.east.set(boundingBox.getLonEastE6() / 1E6);
        return URL_MAIN + "points?" + "n=" + north.get().toString() + "&s=" +
                south.get().toString() + "&w=" + west.get().toString() + "&e=" + east.get().toString();
    }

    public void setArrayName(String NameJsonArray) {
        JSON_ARRAY_NAME = NameJsonArray;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.wtf("Error!", error);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            coffeeList.clear();
            JSONArray jsonCoffeeArray = response.getJSONArray(JSON_ARRAY_NAME);
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
