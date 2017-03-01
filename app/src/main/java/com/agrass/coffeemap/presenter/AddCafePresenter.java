package com.agrass.coffeemap.presenter;

import android.support.v4.app.FragmentManager;

import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.cafe.AddCafeView;
import com.agrass.coffeemap.view.cafe.OnScheduleEditComplete;
import com.agrass.coffeemap.view.cafe.ScheduleCafeWorkFragment;
import com.agrass.coffeemap.view.dialog.DialogTimePicker;


public class AddCafePresenter extends BasePresenter {
    private AddCafeView view;

    public AddCafePresenter(AddCafeView view) {
        this.view = view;
    }

    public void openDialog(FragmentManager fragmentManager) {
        showDialog(fragmentManager, new DialogTimePicker());
    }

    public void onSendButtonClick() {
        view.showMessage("Click!");
    }

    public void onEditTextWorkTimeClick(FragmentManager fragmentManager,
                                        OnScheduleEditComplete view) {
        redirectTo(fragmentManager, ScheduleCafeWorkFragment.newInstance(view), true);
    }
}
