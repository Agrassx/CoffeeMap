package com.agrass.coffeemap.model;

import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.Status;
import com.google.android.gms.maps.model.LatLngBounds;


import rx.Observable;

public interface Model {

    Observable<PointsResponse> getCafeItemList(LatLngBounds bounds);

    Observable<Status> getStatus();
}
