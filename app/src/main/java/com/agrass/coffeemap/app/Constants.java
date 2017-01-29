package com.agrass.coffeemap.app;

import com.agrass.coffeemap.BuildConfig;

public class Constants {
    public static final String URL_MAIN = BuildConfig.ServerAdress;
    public static final String URL_ADD_POINT = BuildConfig.ServerAdress + "addPoint";
    public static final String URL_STATUS = BuildConfig.ServerAdress + "status";
    public static final String URL_TOKEN = BuildConfig.ServerAdress + "testValidate?token=";
    public static final String URL_CAFE_INFO = BuildConfig.ServerAdress + "cafeinfo?id=";
}
