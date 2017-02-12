package com.agrass.coffeemap.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.view.MainActivityView;
import com.agrass.coffeemap.view.base.ActivityView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class CoffeeApplication extends Application {
    public static final String TAG = CoffeeApplication.class.getName();
    private static CoffeeApplication mInstance;
    private GoogleSignInAccount googleSignInAccount;
    private GoogleApiClient googleApiClient;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized CoffeeApplication getInstance() {
        return mInstance;
    }


    public void updateAccount(GoogleSignInAccount googleSignInAccount) {
        this.googleSignInAccount = googleSignInAccount;
    }

    public GoogleApiClient getGoogleApiClient(Context context, MainActivityView activityView) {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage((FragmentActivity) context, activityView)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, buildGoogleSignInOptions())
                    .build();
        }
        return googleApiClient;
    }

    public GoogleSignInAccount getAccount() {
        return googleSignInAccount;
    }

    private GoogleSignInOptions buildGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CoffeeApplication.getInstance().getString(R.string.server_client_id))
                .requestEmail()
                .build();
    }

    @Deprecated
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    @Deprecated
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    @Deprecated
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    @Deprecated
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
