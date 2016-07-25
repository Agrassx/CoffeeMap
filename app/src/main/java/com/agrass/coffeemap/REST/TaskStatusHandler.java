package com.agrass.coffeemap.REST;

public interface TaskStatusHandler {
    void taskSuccessful(String response);
    void taskFailed();
}
