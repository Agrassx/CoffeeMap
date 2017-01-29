package com.agrass.coffeemap.rest;


@Deprecated
public interface TaskStatusHandler {
    void taskSuccessful(String response);
    void taskFailed();
}
