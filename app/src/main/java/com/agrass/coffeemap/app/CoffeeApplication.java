package com.agrass.coffeemap.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.view.MainActivityView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class CoffeeApplication extends Application {
    private static CoffeeApplication mInstance;
    private GoogleSignInAccount googleSignInAccount;
    private GoogleApiClient googleApiClient;

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
}
