package com.agrass.coffeemap.view.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;

import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

public interface MainActivityView extends ActivityView, GoogleApiClient.OnConnectionFailedListener {
    void redirectTo(BaseFragment fragment);
    void redirectTo(BaseFragment fragment, Bundle args);
    void showBottomSheet (BottomSheetDialogFragment fragment);
    void onSignIn(GoogleApiClient mGoogleApiClient);
    void afterSignIn(GoogleSignInAccount account);
}
