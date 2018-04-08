package com.agrass.coffeemap.presenter;

import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.cafe.OnScheduleEditComplete;
import com.agrass.coffeemap.view.cafe.ScheduleCafeWorkIView;

public class ScheduleCafeWorkPresenter extends BasePresenter {

    private ScheduleCafeWorkIView view;

    public ScheduleCafeWorkPresenter(ScheduleCafeWorkIView view) {
        this.view = view;
    }

    public void onButtonSaveClick(String schedule, OnScheduleEditComplete onScheduleEditComplete) {
        view.showMessage(schedule);
        view.onBackPressed();
        onScheduleEditComplete.onScheduleSelect(schedule);
    }
}