package com.agrass.coffeemap.model;

import com.agrass.coffeemap.model.api.response.CafeInfoResponse;
import com.agrass.coffeemap.model.api.response.PointsResponse;
import com.agrass.coffeemap.model.cafe.NewPlace;
import com.agrass.coffeemap.model.cafe.Status;
import com.google.android.gms.maps.model.LatLngBounds;


import rx.Observable;

public interface Model {

    Observable<PointsResponse> getCafeItemList(LatLngBounds bounds);

    Observable<Status> getStatus();

    Observable<CafeInfoResponse> getCafeInfo(String id);

    Observable<String> postNewPlace(NewPlace newPlace);
}
