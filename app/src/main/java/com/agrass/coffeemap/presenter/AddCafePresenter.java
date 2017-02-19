package com.agrass.coffeemap.presenter;

import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.AddCafeView;


public class AddCafePresenter extends BasePresenter {
    private AddCafeView view;

    public AddCafePresenter(AddCafeView view) {
        this.view = view;
    }

    public void onSendButtonClick() {
        view.showMessage("Click!");
    }

}
