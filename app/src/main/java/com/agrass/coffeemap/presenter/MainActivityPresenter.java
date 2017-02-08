package com.agrass.coffeemap.presenter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.AddCafeFragment;
import com.agrass.coffeemap.view.MainActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.agrass.coffeemap.view.base.FragmentView;
import com.agrass.coffeemap.view.map.MapFragment2;

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

    public void onBackPressed() {
        fragmentView.onBackPressed();
    }

}
