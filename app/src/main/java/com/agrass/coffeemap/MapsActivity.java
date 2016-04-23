package com.agrass.coffeemap;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import layout.MapFragment;


public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    protected FragmentManager fragmentManager;
    public static GoogleSignInAccount account;
    private static final String TAG = "SignInActivity";
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private ProgressDialog mProgressDialog;

    private int mSignInError;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;


    private static final int STATE_SIGN_IN = 0;
    private static final int STATE_SIGNED_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final int RC_SIGN_IN = 9001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        if (findViewById(R.id.fragment_layout) != null) {
            if (savedInstanceState != null) {
                return;
            } else {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_layout, new MapFragment());
                fragmentTransaction.commit();
            }
        }
        mGoogleApiClient = buildGoogleApiClient();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.wtf(TAG,"OnStart()");
        if (mGoogleApiClient == null) {
            mGoogleApiClient = buildGoogleApiClient();
            silentSignIn(mGoogleApiClient);
            Log.wtf(TAG,"mGoogleApiClient is null");
        } else {
            silentSignIn(mGoogleApiClient);
            Log.wtf(TAG,"mGoogleApiClient not null");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.wtf(TAG,"OnStop()");
//        if (mGoogleApiClient != null) {
//            if (mGoogleApiClient.isConnected()) {
//                mGoogleApiClient.disconnect();
//            }
//            Log.wtf(TAG,"mGoogleApiClient not null");
//        } else {
//            Log.wtf(TAG,"mGoogleApiClient is null");
//        }
    }

    private GoogleApiClient buildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        return new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.wtf(TAG,"onConnected()");
        mSignInProgress = STATE_SIGN_IN;
//        Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        Toast.makeText(getApplication(), account.getDisplayName(), Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.wtf(TAG,"onConnectionSuspended()" + cause);
//        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.wtf(TAG,"onConnectionFailed" + connectionResult);
//        if (mSignInError != STATE_IN_PROGRESS) {
//            mSignInIntent = connectionResult.getResolution();
//            mSignInError = connectionResult.getErrorCode();
//            if (mSignInError == STATE_SIGN_IN) {
//                resolveSignInError();
//            }
//        }
    }

    private void resolveSignInError() {
        Log.wtf(TAG,"resolveSignInError()");
//        if (mSignInIntent != null) {
//            try {
//                mSignInProgress = STATE_IN_PROGRESS;
//                startIntentSenderForResult(mSignInIntent.getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
//            } catch (IntentSender.SendIntentException e) {
//                mSignInProgress = STATE_SIGN_IN;
//                mGoogleApiClient.connect();
//            }
//        } else {
//            Log.wtf(TAG,"resolveSignInError() Something wrong!");
//        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.wtf(TAG,"onActivityResult()");
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
//        switch (requestCode) {
//            case RC_SIGN_IN:
//                if (resultCode == RESULT_OK) {
//                    mSignInProgress = STATE_SIGN_IN;
//                } else {
//                    mSignInProgress = STATE_SIGNED_IN;
//                }
//
//                if (!mGoogleApiClient.isConnecting()) {
//                    mGoogleApiClient.connect();
//                }
//                break;
//        }
    }

    public void silentSignIn(GoogleApiClient googleApiClient) {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(getApplication(), acct.getDisplayName(), Toast.LENGTH_LONG ).show();
            signInButton.setVisibility(View.GONE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                resolveSignInError();
                Log.wtf(TAG, "Sign in button was clicked");
                break;
        }
    }
}
