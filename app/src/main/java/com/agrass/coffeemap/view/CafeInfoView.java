package com.agrass.coffeemap.view;

import com.agrass.coffeemap.model.api.response.CafeInfoResponse;
import com.agrass.coffeemap.view.base.FragmentView;

public interface CafeInfoView extends FragmentView {
    void cafeInfoFound(CafeInfoResponse cafeInfoResponse);
    void cafeInfoNotFound();
}
