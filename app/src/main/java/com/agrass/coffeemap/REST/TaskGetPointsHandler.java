package com.agrass.coffeemap.REST;

import com.agrass.coffeemap.model.CafeItem;

import java.util.ArrayList;

public interface TaskGetPointsHandler {
    void taskSuccessful(ArrayList<CafeItem> overlayItemArrayList);
    void taskFailed();
}
