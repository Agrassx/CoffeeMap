package com.agrass.coffeemap.model;

import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.Status;

import org.osmdroid.util.BoundingBox;


import rx.Observable;

public interface Model {

    Observable<PointsResponse> getCafeItemList(BoundingBox boundingBoxE6);

    Observable<Status> getStatus();


}
