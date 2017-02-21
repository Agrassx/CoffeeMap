package com.agrass.coffeemap.presenter;

import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.dialog.DialogTimePicker;
import com.agrass.coffeemap.view.dialog.TimePickerView;


public class DialogTimePickerPresenter extends BasePresenter {

    private TimePickerView view;

    public DialogTimePickerPresenter(TimePickerView view) {
        this.view = view;
    }

    public void onPositiveButtonClick(String tab) {
        if (tab.equals(DialogTimePicker.TAB_WORK_FROM)) {
            view.changeTab(DialogTimePicker.TAB_WORK_TO);
            return;
        }
    }

}
