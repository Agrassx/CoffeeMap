package com.agrass.coffeemap.presenter;

import com.agrass.coffeemap.model.parsers.TimeParser;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.dialog.DialogListener;
import com.agrass.coffeemap.view.dialog.DialogTimePicker;
import com.agrass.coffeemap.view.dialog.TimePickerView;


public class DialogTimePickerPresenter extends BasePresenter {

    private TimePickerView view;
    private DialogListener listener;

    public DialogTimePickerPresenter(TimePickerView view) {
        this.view = view;
    }

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    public void onPositiveButtonClick(String tab, TimeParser timeFrom, TimeParser timeTo) {
        if (tab.equals(DialogTimePicker.TAB_WORK_FROM)) {
            view.changeTab(DialogTimePicker.TAB_WORK_TO);
            return;
        }
        listener.onPositiveButtonClick(timeFrom, timeTo);
        view.dismissDialog();
    }

}
