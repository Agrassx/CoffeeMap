package com.agrass.coffeemap.REST;

import com.agrass.coffeemap.model.CafeInfo;


public interface TaskGetCafeInfoHandler {
    void taskSuccessful(CafeInfo cafeInfo);
    void taskFailed();
}
