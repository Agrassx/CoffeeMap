package com.agrass.coffeemap;

import java.util.ArrayList;

public interface TaskGetPointsHandler {
    void taskSuccessful(ArrayList<CafeItem> overlayItemArrayList);
    void taskFailed();
}
