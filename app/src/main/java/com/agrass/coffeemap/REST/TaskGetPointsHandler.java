package com.agrass.coffeemap.rest;

import com.agrass.coffeemap.model.cafe.CafeItem;

import java.util.ArrayList;

@Deprecated
public interface TaskGetPointsHandler {
    void taskSuccessful(ArrayList<CafeItem> overlayItemArrayList);
    void taskFailed();
}
