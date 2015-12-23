package com.agrass.coffeemap;

import java.util.ArrayList;

public interface JsonTaskHandler {
    void taskSuccessful(ArrayList<CafeItem> overlayItemArrayList);
    void taskFailed();
}
