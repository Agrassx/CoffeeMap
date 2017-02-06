package com.agrass.coffeemap.presenter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.MainActivityView;
import com.agrass.coffeemap.view.base.FragmentView;
import com.agrass.coffeemap.view.map.MapFragment2;

public class MainActivityPresenter extends BasePresenter {

    private MainActivityView view;
    private FragmentView fragmentView;

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

    public void onBackPressed() {
        fragmentView.onBackPressed();
    }

}
