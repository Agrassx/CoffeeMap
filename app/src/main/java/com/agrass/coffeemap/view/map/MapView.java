package com.agrass.coffeemap.view.map;

import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.model.cafe.CafeItem;
import com.agrass.coffeemap.view.base.IView;

import java.util.List;


public interface MapView extends IView {
    void showMarkers(List<Cafe> list);
}
