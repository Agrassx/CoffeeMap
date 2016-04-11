package com.agrass.coffeemap;

public interface TaskStatusHandler {
    void taskSuccessful(String response);
    void taskFailed();
}
