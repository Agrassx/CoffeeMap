package com.agrass.coffeemap.rest;

import com.android.volley.Response;

@Deprecated
public class GetPointsRequest extends GsonRequest {

    public GetPointsRequest(String url, Class clazz,
                            Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, clazz, listener, errorListener);

    }
}
