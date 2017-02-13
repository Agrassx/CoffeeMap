package com.agrass.coffeemap.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.agrass.coffeemap.app.CoffeeApplication;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.model.cafe.Status;
import com.agrass.coffeemap.model.map.ClusterItemCafeRender;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.AddCafeFragment;
import com.agrass.coffeemap.view.BottomSheetSignOnFragment;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.agrass.coffeemap.view.map.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;


import rx.Subscriber;
import rx.Subscription;

import static com.agrass.coffeemap.app.Constants.LOCATION;

public class MapPresenter extends BasePresenter {
    private static final String LOG = MapPresenter.class.getName();

    private MapView view;
    private ClusterManager<Cafe> clusterManager;
    private ClusterItemCafeRender clusterRender;

    public MapPresenter(MapView view) {
        this.view = view;
    }


    public void init(GoogleMap googleMap, Context context) {
        googleMap.getUiSettings().setAllGesturesEnabled(true);
//        this.map.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        clusterManager = new ClusterManager<>(context, googleMap);
        clusterRender = new ClusterItemCafeRender(context, googleMap, clusterManager);
        clusterManager.setRenderer(clusterRender);
        clusterRender.setOnClusterClickListener(view);
        clusterRender.setOnClusterItemClickListener(view);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraMoveListener(view);
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
                        clusterManager.clearItems();
                        clusterManager.addItems(response.getList());
                        clusterManager.cluster();
//                        view.showMarkers(response.getList());
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

    public void addPointClick(FragmentManager fragmentManager, MainActivityView activityView) {
        if (CoffeeApplication.getInstance().getAccount() == null) {
            showBottomSheetDialogFragment(
                    fragmentManager,
                    BottomSheetSignOnFragment.newInstance(activityView)
            );
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

    public void changeMarkerColor(Cafe cafe) {
//        TODO change marker color
        clusterRender.getMarker(cafe);
    }

    public void cluster() {
        clusterManager.cluster();
    }
}
