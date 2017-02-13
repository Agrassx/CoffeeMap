package com.agrass.coffeemap.presenter;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.app.CoffeeApplication;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.AddCafeFragment;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.agrass.coffeemap.view.base.FragmentView;
import com.agrass.coffeemap.view.map.MapFragment2;
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
