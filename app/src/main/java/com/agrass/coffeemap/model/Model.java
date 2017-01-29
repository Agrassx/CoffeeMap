package com.agrass.coffeemap.model;

import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.Status;

import org.osmdroid.util.BoundingBoxE6;


import rx.Observable;

public interface Model {

    Observable<PointsResponse> getCafeItemList(BoundingBoxE6 boundingBoxE6);

    Observable<Status> getStatus();


}
