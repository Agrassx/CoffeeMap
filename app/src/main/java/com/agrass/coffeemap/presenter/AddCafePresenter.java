package com.agrass.coffeemap.presenter;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.agrass.coffeemap.model.cafe.NewPlace;
import com.agrass.coffeemap.model.cafe.Status;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.cafe.AddCafeView;
import com.agrass.coffeemap.view.cafe.OnScheduleEditComplete;
import com.agrass.coffeemap.view.cafe.ScheduleCafeWorkFragment;
import com.agrass.coffeemap.view.dialog.DialogTimePicker;
import com.google.gson.Gson;

import rx.Subscriber;
import rx.Subscription;


public class AddCafePresenter extends BasePresenter {
    private static final String LOG = AddCafePresenter.class.getName();
    private AddCafeView view;

    public AddCafePresenter(AddCafeView view) {
        this.view = view;
    }

    public void openDialog(FragmentManager fragmentManager) {
        showDialog(fragmentManager, new DialogTimePicker());
    }

    public void onSendButtonClick(NewPlace newPlace) {
//        TODO: Check user data
        Subscription subscription = (Subscription) model.postNewPlace(newPlace).subscribe(
                new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String status) {

                    }
                }
        );
        addSubscription(subscription);
    }

    public void onEditTextWorkTimeClick(FragmentManager fragmentManager,
                                        OnScheduleEditComplete view) {
        redirectTo(fragmentManager, ScheduleCafeWorkFragment.newInstance(view), true);
    }
}
