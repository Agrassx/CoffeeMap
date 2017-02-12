package com.agrass.coffeemap.presenter;

import android.os.Bundle;
import android.util.Log;

import com.agrass.coffeemap.app.CoffeeApplication;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.Status;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.AddCafeFragment;
import com.agrass.coffeemap.view.BottomSheetSignOnFragment;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.agrass.coffeemap.view.map.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;


import rx.Subscriber;
import rx.Subscription;

import static com.agrass.coffeemap.app.Constants.LOCATION;

public class MapPresenter extends BasePresenter {
    private static final String LOG = MapPresenter.class.getName();

    private MapView view;

    public MapPresenter(MapView view) {
        this.view = view;
    }


    public void animateCamera(GoogleMap map, LatLng point) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 8),
                new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
                getPoints(bounds);
            }

            @Override
            public void onCancel() {
                getPoints(map.getProjection().getVisibleRegion().latLngBounds);
            }
        });
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
        if (CoffeeApplication.getInstance().getAccount() == null) {
            activityView.showBottomSheet(BottomSheetSignOnFragment.newInstance(activityView));
            return;
        }
        view.showAddCafeLayout();
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
