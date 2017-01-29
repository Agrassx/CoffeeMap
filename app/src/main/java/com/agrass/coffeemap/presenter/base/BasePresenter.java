package com.agrass.coffeemap.presenter.base;

import com.agrass.coffeemap.model.Model;
import com.agrass.coffeemap.model.ModelImpl;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter implements Presenter {

    protected Model model = new ModelImpl();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();


    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void onStop() {
        compositeSubscription.clear();
    }

}
