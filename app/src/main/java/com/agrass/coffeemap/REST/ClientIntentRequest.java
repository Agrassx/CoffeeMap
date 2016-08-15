package com.agrass.coffeemap.REST;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.agrass.coffeemap.BuildConfig;
import com.agrass.coffeemap.JsonParser.JsonCafeInfoParser;
import com.agrass.coffeemap.model.CafeItem;
import com.agrass.coffeemap.JsonParser.JsonCafeItemParser;
import com.android.volley.DefaultRetryPolicy;
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

import java.util.ArrayList;


public class ClientIntentRequest extends IntentService implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private static final String URL_MAIN = BuildConfig.ServerAdress;
    private static final String URL_ADD_POINT = BuildConfig.ServerAdress + "addPoint";
    private static final String URL_STATUS = BuildConfig.ServerAdress + "status";
    private static final String URL_TOKEN = BuildConfig.ServerAdress + "testValidate?token=";
    private static final String URL_CAFE_INFO = BuildConfig.ServerAdress + "cafeinfo?id=";
    private static String JSON_ARRAY_NAME = "points";
    private ThreadLocal<Double> north = new ThreadLocal<>();
    private ThreadLocal<Double> south = new ThreadLocal<>();
    private ThreadLocal<Double> west = new ThreadLocal<>();
    private ThreadLocal<Double> east = new ThreadLocal<>();
    private ArrayList<CafeItem> coffeeList;
    private TaskGetPointsHandler taskGetPointsHandler;
    private TaskStatusHandler taskStatusHandler;
    private TaskGetCafeInfoHandler getCafeInfoHandler;
    private Context context;
    private RequestQueue queue;
    private JsonCafeInfoParser jsonCafeInfoParser;

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
        jsonCafeInfoParser = new JsonCafeInfoParser();
    }

    @Override
    public void onHandleIntent(Intent intent) {

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
                Log.wtf("ClientIntentRequest.PostNewPoint", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("ClientIntentRequest.PostNewPoint.Error", error.getMessage());
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 0));
        getQueue().add(jsonObjectRequest);
    }

    public void getCafeInfo(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_CAFE_INFO+id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getCafeInfoHandler.taskSuccessful(jsonCafeInfoParser.getCafeInfo(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.wtf("Status Answer", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCafeInfoHandler.taskFailed();
                Log.wtf("Status Error", error.getMessage());
            }
        });
        getQueue().add(jsonObjectRequest);
    }

    public void setTaskGetPointsHandler(TaskGetPointsHandler taskHandler) {
        this.taskGetPointsHandler = taskHandler;
    }

    public void setTaskStatusHandler(TaskStatusHandler taskStatusHandler) {
        this.taskStatusHandler = taskStatusHandler;
    }

    public void setTaskGetCafeInfoHandler(TaskGetCafeInfoHandler getCafeInfoHandler) {
        this.getCafeInfoHandler = getCafeInfoHandler;
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
            JsonCafeItemParser cafeItemParser = new JsonCafeItemParser(context);
            JSONArray jsonCoffeeArray = response.getJSONArray(JSON_ARRAY_NAME);
            int length = jsonCoffeeArray.length();
            for (int i = 0; i < length; i++) {
                coffeeList.add(
                        cafeItemParser.getCafeItem(
                                jsonCoffeeArray.getJSONObject(i)
                        )
                );
            }
            taskGetPointsHandler.taskSuccessful(coffeeList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
