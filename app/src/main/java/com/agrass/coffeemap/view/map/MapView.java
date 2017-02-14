package com.agrass.coffeemap.view.map;

import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.view.base.FragmentView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.maps.android.clustering.ClusterManager;

import org.osmdroid.events.MapListener;

import java.util.List;


public interface MapView extends FragmentView, MapListener, OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener, ClusterManager.OnClusterClickListener<Cafe>,
        ClusterManager.OnClusterItemClickListener<Cafe>, ClearSelectionView {
    void showMarkers(List<Cafe> list);
    void showAddCafeLayout();
}
