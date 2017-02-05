package com.agrass.coffeemap.model.map;

import android.content.Context;

import com.agrass.coffeemap.model.cafe.Cafe;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;

public class CafeClusterManger extends ClusterManager<Cafe> {

    public CafeClusterManger(Context context, GoogleMap map) {
        super(context, map);
    }
}
