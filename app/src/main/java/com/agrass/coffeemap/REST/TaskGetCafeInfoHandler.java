package com.agrass.coffeemap.rest;

import com.agrass.coffeemap.model.cafe.CafeInfo;

@Deprecated
public interface TaskGetCafeInfoHandler {
    void taskSuccessful(CafeInfo cafeInfo);
    void taskFailed();
}
