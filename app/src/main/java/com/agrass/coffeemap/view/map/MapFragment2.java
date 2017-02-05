package com.agrass.coffeemap.view.map;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.model.map.ClusterItemCafeRender;
import com.agrass.coffeemap.presenter.map.MapPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.ClusterManager;

import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.List;

public class MapFragment2 extends Fragment implements MapView, MapListener, OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener {

    private static final String LOG = MapFragment2.class.getName();
    private static final String BASE_URLS[] = {
            "http://a.tilessputnik.ru/tiles/kmt2/",
            "http://b.tilessputnik.ru/tiles/kmt2/",
            "http://c.tilessputnik.ru/tiles/kmt2/",
            "http://d.tilessputnik.ru/tiles/kmt2/"};

    String url[] = {"http://tiles.maps.sputnik.ru/"};

    private ClusterManager<Cafe> clusterManager;
    private ClusterItemCafeRender clusterRender;
    private MapPresenter presenter;
    private GoogleMap map;
    private final GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    protected TilesOverlay mTilesOverlay;
    private Drawable drawable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mapView.getController().setCenter(new GeoPoint(
//                (float) Moscow.getLatitude(),
//                (float) Moscow.getLongitude()
//        ));
//        mapView.getController().setZoom(11);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        presenter = new MapPresenter(this);
//        mResourceProxy = new ResourceProxyImpl(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getResources().getDrawable(R.drawable.ic_place_36dp, null);
        } else {
            drawable = getResources().getDrawable(R.drawable.ic_place_36dp);
        }
        com.google.android.gms.maps.MapView mMapView
                = (com.google.android.gms.maps.MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
//        mapView = (org.osmdroid.views.MapView) view.findViewById(R.id.openMapView);
//
//        float scale = mapView.getResources().getDisplayMetrics().density;
//        int imageSize = (int) (256 * scale);
//        ITileSource tileSource = new RetinaTileSource("Sputnik", 2, 18, imageSize, ".png", BASE_URLS);
//        mapView.setTileSource(tileSource);
//
//        MapTileProviderBasic mProvider = new MapTileProviderBasic(getActivity(), tileSource);
//        mTilesOverlay = new TilesOverlay(mProvider, getActivity());
//        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
//        mapView.getOverlays().clear();
//        mapView.getOverlays().add(mTilesOverlay);
//
////        mapView.getTileProvider().setTileSource(tileSource);
//
//        mapView.setMultiTouchControls(true);
//        mapView.setMapListener(new DelayedMapListener(this, 250));
        return view;
    }


    public void getBbox() {

    }

    @Override
    public void showMessage(String error) {

    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        presenter.getPoints(map.getProjection().getVisibleRegion().latLngBounds);
        return true;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.clusterManager = new ClusterManager<>(getActivity(), googleMap);
        this.clusterRender = new ClusterItemCafeRender(getActivity(), googleMap, clusterManager);
        this.clusterManager.setRenderer(clusterRender);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                Moscow.getLatitude(),
                Moscow.getLongitude()
        ), 8), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
                presenter.getPoints(bounds);
            }

            @Override
            public void onCancel() {

            }
        });
        map.setOnCameraMoveListener(this);
    }

    @Override
    public void onCameraMove() {
        clusterManager.cluster();
    }

    @Override
    public void showMarkers(List<Cafe> list) {
        clusterManager.clearItems();
        clusterManager.addItems(list);
        clusterManager.cluster();
    }
}