package com.agrass.coffeemap.model.api;

import com.agrass.coffeemap.model.api.response.CafeInfoResponse;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.NewPlace;
import com.agrass.coffeemap.model.cafe.Status;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("points")
    Observable<PointsResponse> getPoints(
            @Query("n") String n,
            @Query("s") String s,
            @Query("w") String w,
            @Query("e") String e
    );

    @GET("status")
    Observable<Status> getVersion();

    @GET("cafeinfo")
    Observable<CafeInfoResponse> getCafeInfo(@Query("id") String id);

//    TODO: Think about answer
    @POST("addPoint")
    Observable<String> postNewPlace(@Body NewPlace newPlace);


}
