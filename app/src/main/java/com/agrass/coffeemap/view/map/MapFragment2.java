package com.agrass.coffeemap.view.map;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.model.cafe.CafeItem;
import com.agrass.coffeemap.model.map.RetinaTileSource;
import com.agrass.coffeemap.presenter.map.MapPresenter;

import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.List;

public class MapFragment2 extends Fragment implements MapView, MapListener {

    private static final String BASE_URLS[] = {
            "http://a.tilessputnik.ru/tiles/kmt2/",
            "http://b.tilessputnik.ru/tiles/kmt2/",
            "http://c.tilessputnik.ru/tiles/kmt2/",
            "http://d.tilessputnik.ru/tiles/kmt2/"};

    private MapPresenter presenter;
    protected org.osmdroid.views.MapView mapView;
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
        mapView.getController().setCenter(new GeoPoint(
                (float) Moscow.getLatitude(),
                (float) Moscow.getLongitude()
        ));
        mapView.getController().setZoom(11);


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

        mapView = (org.osmdroid.views.MapView) view.findViewById(R.id.openMapView);

        float scale = mapView.getResources().getDisplayMetrics().density;
        int imageSize = (int) (256 * scale);
        ITileSource tileSource = new RetinaTileSource("Sputnik", 2, 18, imageSize, ".png", BASE_URLS);

        mapView.setTileSource(tileSource);
//        MapTileProviderBasic mProvider = new MapTileProviderBasic(getActivity(), tileSource);
//        mTilesOverlay = new TilesOverlay(mProvider, context);
//        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);

        mapView.getOverlays().clear();
//        mapView.getOverlays().add(mTilesOverlay);
        mapView.setMultiTouchControls(true);
//        mapView.setMapListener(this);
        mapView.setMapListener(new DelayedMapListener(this, 250));
        return view;
    }


    public void getBbox() {

    }

    @Override
    public void showMarkers(List<CafeItem> list) {

    }

    @Override
    public void showMessage(String error) {

    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        presenter.getPoints(mapView.getBoundingBox());
        return true;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        presenter.getPoints(mapView.getBoundingBox());
        return true;
    }
}
