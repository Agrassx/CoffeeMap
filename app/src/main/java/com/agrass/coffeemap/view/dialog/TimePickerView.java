package com.agrass.coffeemap.view.dialog;

import android.content.DialogInterface;

import com.agrass.coffeemap.view.base.IView;

public interface TimePickerView extends IView, DialogInterface.OnShowListener {

    void changeTab(String tabTag);
    void dismissDialog();

}
