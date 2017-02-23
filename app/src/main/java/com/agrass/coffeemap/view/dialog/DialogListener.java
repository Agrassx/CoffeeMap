package com.agrass.coffeemap.view.dialog;

import com.agrass.coffeemap.model.parsers.TimeParser;

public interface DialogListener {
    void onPositiveButtonClick(TimeParser timeFrom, TimeParser timeTo);
    void onNegativeButtonClick();
}
