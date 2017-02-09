package com.agrass.coffeemap.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.app.CoffeeApplication;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.AddCafeFragment;
import com.agrass.coffeemap.view.MainActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.agrass.coffeemap.view.base.FragmentView;
import com.agrass.coffeemap.view.map.MapFragment2;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivityPresenter extends BasePresenter {

    private MainActivityView view;
    private FragmentView fragmentView;
//    private FragmentManager fragmentManager;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }

    public void redirectToMapFragment(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapFragment2 mapFragment = MapFragment2.newInstance(view);
        fragmentView = mapFragment.getIView();
        fragmentTransaction.replace(R.id.fragment_container, mapFragment);
        fragmentTransaction.commit();
    }

    public void redirectToAddCafeFragment(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddCafeFragment addCafeFragment = AddCafeFragment.newInstance(view);
        fragmentView = addCafeFragment.getIView();
        fragmentTransaction.replace(R.id.fragment_container, addCafeFragment);
        fragmentTransaction.commit();
    }

    public void redirectTo(FragmentManager fragmentManager, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentView = fragment.getIView();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void redirectTo(FragmentManager fragmentManager, BaseFragment fragment, boolean isBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentView = fragment.getIView();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (isBackStack) fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    public void redirectTo(FragmentManager fragmentManager,
                           BaseFragment fragment, Bundle args, boolean isBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentView = fragment.getIView();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (isBackStack) fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    public void showBottomSheetDialogFragment(FragmentManager fragmentManager,
                                              BottomSheetDialogFragment bottomSheetDialogFragment) {

        bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.getTag());
    }


    public GoogleApiClient buildGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, view)
                .addApi(Auth.GOOGLE_SIGN_IN_API, buildGoogleSignInOptions())
                .build();
    }

    private GoogleSignInOptions buildGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CoffeeApplication.getInstance().getString(R.string.server_client_id))
                .requestEmail()
                .build();
    }

    public void onBackPressed() {
        fragmentView.onBackPressed();
    }

}
