package com.agrass.coffeemap;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class PostNewPoint extends IntentService implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String URL_MAIN = BuildConfig.ServerAdress + "addPoint";
    private Context context;
    private JSONObject jsonNewPoint;
    private TaskPostNewPointHandler taskPostNewPointHandler;

    @Override
    public void onCreate() {
        super.onCreate();
    }

     public PostNewPoint(Context context, JSONObject jsonNewPoint) {
         super("postNewPoint");
         this.context = context;
         this.jsonNewPoint = jsonNewPoint;
     }

    @Override
    public void onHandleIntent(Intent intent) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL_MAIN,
                jsonNewPoint, this, this);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    public void setTaskPostNewPointHandler(TaskPostNewPointHandler taskPostNewPointHandler) {
        this.taskPostNewPointHandler = taskPostNewPointHandler;
    }
}
