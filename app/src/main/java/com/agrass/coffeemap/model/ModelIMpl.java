package com.agrass.coffeemap.model;

import com.agrass.coffeemap.model.api.ApiInterface;
import com.agrass.coffeemap.model.api.ApiModule;
import com.agrass.coffeemap.model.api.response.CafeInfoResponse;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.NewPlace;
import com.agrass.coffeemap.model.cafe.Status;
import com.google.android.gms.maps.model.LatLngBounds;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ModelImpl implements Model {

    private ApiInterface apiInterface = ApiModule.getApiInterface();
    private final Observable.Transformer schedulersTransformer;

    public ModelImpl() {
        schedulersTransformer = o -> ((Observable) o).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<PointsResponse> getCafeItemList(LatLngBounds bounds) {
        return apiInterface.getPoints(
                String.valueOf(bounds.northeast.latitude),
                String.valueOf(bounds.southwest.latitude),
                String.valueOf(bounds.southwest.longitude),
                String.valueOf(bounds.northeast.longitude)
        ).compose(applySchedulers());
    }

    @Override
    public Observable<Status> getStatus() {
        return apiInterface.getVersion().compose(applySchedulers());
    }

    @Override
    public Observable<CafeInfoResponse> getCafeInfo(String id) {
        return apiInterface.getCafeInfo(id).compose(applySchedulers());
    }

    @Override
    public Observable<String> postNewPlace(NewPlace newPlace) {
        return apiInterface.postNewPlace(newPlace).compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }
}
