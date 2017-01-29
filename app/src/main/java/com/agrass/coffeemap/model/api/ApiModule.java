package com.agrass.coffeemap.model.api;


import com.agrass.coffeemap.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

    public static ApiInterface getApiInterface() {

        OkHttpClient httpClient = new OkHttpClient();
//        httpClient.interceptors().add(chain -> {
//            Request original = chain.request();
//            Request request = original.newBuilder()
//                    .method(original.method(), original.body())
//                    .build();
//
//            return chain.proceed(request);
//        });
        httpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(BuildConfig.ServerAdress)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        return builder.build().create(ApiInterface.class);
    }


}
