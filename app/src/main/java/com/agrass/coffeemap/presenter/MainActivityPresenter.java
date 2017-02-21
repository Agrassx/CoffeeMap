package com.agrass.coffeemap.presenter;

import android.util.Log;

import com.agrass.coffeemap.app.CoffeeApplication;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class MainActivityPresenter extends BasePresenter {

    private static final String TAG = MainActivityPresenter.class.getName();
    private MainActivityView view;
//    private FragmentManager fragmentManager;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }


    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            CoffeeApplication.getInstance().updateAccount(result.getSignInAccount());
            view.afterSignIn(result.getSignInAccount());
            view.showMessage("Сделано! :)");
        }
    }

    public void updateUserInfo(GoogleSignInAccount account) {

    }

}
