package com.agrass.coffeemap.model.api;

import com.agrass.coffeemap.model.api.response.CafeInfoResponse;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.Status;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("points")
    Observable<PointsResponse> getRepositories(
            @Query("n") String n,
            @Query("s") String s,
            @Query("w") String w,
            @Query("e") String e
    );

    @GET("status")
    Observable<Status> getVersion();

    @GET("cafeinfo")
    Observable<CafeInfoResponse> getCafeInfo(@Query("id") String id);


}
