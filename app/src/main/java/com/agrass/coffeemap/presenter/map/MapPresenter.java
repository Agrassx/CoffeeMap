package com.agrass.coffeemap.presenter.map;

import android.util.Log;

import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.CafeItem;
import com.agrass.coffeemap.model.cafe.Status;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.map.MapView;
import com.google.gson.Gson;

import org.osmdroid.util.BoundingBoxE6;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

public class MapPresenter extends BasePresenter {
    private static final String LOG = MapPresenter.class.getName();

    private MapView view;

    public MapPresenter(MapView view) {
        this.view = view;
    }


    public void getPoints(BoundingBoxE6 boundingBox) {
        Subscription subscription = (Subscription) model.getCafeItemList(boundingBox)
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


}