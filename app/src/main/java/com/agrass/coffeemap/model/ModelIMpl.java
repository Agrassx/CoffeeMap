package com.agrass.coffeemap.model;

import com.agrass.coffeemap.model.api.ApiInterface;
import com.agrass.coffeemap.model.api.ApiModule;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.CafeItem;
import com.agrass.coffeemap.model.cafe.Status;

import org.osmdroid.util.BoundingBoxE6;

import java.util.List;


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
    public Observable<PointsResponse> getCafeItemList(BoundingBoxE6 boundingBoxE6) {
        return apiInterface.getRepositories(
                String.valueOf(boundingBoxE6.getLatNorthE6() / 1E6),
                String.valueOf(boundingBoxE6.getLatSouthE6() / 1E6),
                String.valueOf(boundingBoxE6.getLonWestE6() / 1E6),
                String.valueOf(boundingBoxE6.getLonEastE6() / 1E6)
        ).compose(applySchedulers());
    }

    @Override
    public Observable<Status> getStatus() {
        return apiInterface.getVersion().compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }
}
