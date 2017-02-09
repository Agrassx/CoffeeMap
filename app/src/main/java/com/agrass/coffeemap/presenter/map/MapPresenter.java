package com.agrass.coffeemap.presenter.map;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.Status;
import com.agrass.coffeemap.presenter.MainActivityPresenter;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.AddCafeFragment;
import com.agrass.coffeemap.view.BottomSheetSignOnFragment;
import com.agrass.coffeemap.view.MainActivityView;
import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.map.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.BoundingBoxE6;

import rx.Subscriber;
import rx.Subscription;

import static com.agrass.coffeemap.app.Constants.LOCATION;

public class MapPresenter extends BasePresenter {
    private static final String LOG = MapPresenter.class.getName();

    private MapView view;

    public MapPresenter(MapView view) {
        this.view = view;
    }


    public void getPoints(LatLngBounds bounds) {
        Subscription subscription = (Subscription) model.getCafeItemList(bounds)
                .subscribe(new Subscriber<PointsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.getMessage());
                        view.showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(PointsResponse response) {
                        Log.e(LOG, new Gson().toJson(response));
                        view.showMarkers(response.getList());
                    }
                });
        addSubscription(subscription);
    }

    public void getStatus() {
        Subscription subscription = (Subscription) model.getStatus().subscribe(
                new Subscriber<Status>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.getMessage());
                    }

                    @Override
                    public void onNext(Status status) {
                        Log.e(LOG, new Gson().toJson(status));
                    }
                }
        );
        addSubscription(subscription);
    }

    public void addPointClick(MainActivityView activityView) {
//        view.showAddCafeLayout();
        activityView.showBottomSheet(new BottomSheetSignOnFragment());
    }

    public void okButtonClick(MainActivityView view, LatLng point) {
        Bundle args = new Bundle();
        args.putString(LOCATION, new Gson().toJson(point));
        view.redirectTo(AddCafeFragment.newInstance(view), args);
    }

    public void cancelButtonClick() {
        this.view.onBackPressed();
    }
}
