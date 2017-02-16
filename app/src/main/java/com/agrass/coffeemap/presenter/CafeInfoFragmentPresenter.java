package com.agrass.coffeemap.presenter;

import android.util.Log;

import com.agrass.coffeemap.model.api.response.CafeInfoResponse;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.CafeInfoView;
import com.google.gson.Gson;

import rx.Subscriber;
import rx.Subscription;

public class CafeInfoFragmentPresenter extends BasePresenter {
    private static final String LOG = CafeInfoFragmentPresenter.class.getName();

    private CafeInfoView view;

    public CafeInfoFragmentPresenter(CafeInfoView view) {
        this.view = view;
    }

    public void getCafeInfo(String id) {
        Subscription subscription = (Subscription) model.getCafeInfo(id).subscribe(
                new Subscriber<CafeInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.getMessage());
                    }

                    @Override
                    public void onNext(CafeInfoResponse response) {
                        Log.e(LOG, new Gson().toJson(response));
                        if (response.isOk() && response.isFound()) {
                            view.cafeInfoFound(response);
                            return;
                        }
                        view.cafeInfoNotFound();
                    }
                }
        );
        addSubscription(subscription);
    }

    public void showRatingDialog() {
        Log.e(LOG, "Set rating click");
//      TODO: Create rating dialog
    }

}
