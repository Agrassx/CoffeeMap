package com.agrass.coffeemap.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.app.CoffeeApplication;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.MainActivityView;
import com.agrass.coffeemap.view.SignInView;


public class SignInFragmentPresenter extends BasePresenter {

    private SignInView view;
    private MainActivityView activityView;


    public SignInFragmentPresenter(SignInView view, MainActivityView activityView) {
        this.view = view;
        this.activityView = activityView;
    }

    public void onSignInButtonClick(Context activityContext) {
        view.hide();
        activityView.onSignIn(CoffeeApplication.getInstance().getGoogleApiClient(
                activityContext,
                activityView
        ));
    }

}
