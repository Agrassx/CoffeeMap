package com.agrass.coffeemap.presenter;

import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.presenter.base.BasePresenter;
import com.agrass.coffeemap.view.CafeInfoView;

public class CafeInfoFragmentPresenter extends BasePresenter {

    private CafeInfoView view;

    public CafeInfoFragmentPresenter(CafeInfoView view) {
        this.view = view;
    }

    public void getCafeInfo(Cafe cafe) {
//        TODO: get cafeInfo API method
    }

}
