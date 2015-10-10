package com.agrass.coffeemap;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.ResourceProxy;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.TilesOverlay;


public class MapFragment extends Fragment {

    protected MapView SputnikMap;
    protected TilesOverlay mTilesOverlay;
    //    private static final String PREFS_NAME = "MyPrefsFile";
    private GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    private CoffeeAPI coffeeAPI;
    ResourceProxy mResourceProxy;
//    private ItemizedIconOverlay<OverlayItem> coffeeOverlay;
    private CoffeeOverlay coffeeOverlay;
    private static final String	baseUrls[] = { "http://a.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://b.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://c.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://d.tiles.maps.sputnik.ru/tiles/kmt2/" };

//    public static MapFragment newInstance() {
//        return new MapFragment();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
        SputnikMap = new MapView(inflater.getContext(), 256, mResourceProxy);
        return SputnikMap;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Context mapContext = SputnikMap.getContext();
        float scale = mapContext.getResources().getDisplayMetrics().density;
        int imageSize = (int) (256 * scale);
        ITileSource tileSource = new RetinaTileSource("Sputnik", null, 1, 18, imageSize, ".png", baseUrls);
        SputnikMap.setTileSource(tileSource);
        MapTileProviderBasic mProvider = new MapTileProviderBasic(mapContext, tileSource);
        mTilesOverlay = new TilesOverlay(mProvider, mapContext);
        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
        SputnikMap.getOverlays().clear();
        SputnikMap.getOverlays().add(mTilesOverlay);
        SputnikMap.setMultiTouchControls(true);
        SputnikMap.getController().setZoom(11);
        SputnikMap.getController().setCenter(Moscow);

        coffeeAPI = new CoffeeAPI();
        MapListener mapListener = new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                refreshCoffeeOverlay();
                Log.wtf("scroll", "it was scrolled");
                SputnikMap.invalidate();
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                refreshCoffeeOverlay();
                Log.wtf("zoom", "it was zoomed");
                SputnikMap.invalidate();
                return false;
            }
        };
        SputnikMap.setMapListener(new DelayedMapListener(mapListener, 250));
//        refreshCoffeeOverlay();
//        SputnikMap.invalidate();

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void refreshCoffeeOverlay() {
        BoundingBoxE6 boxE6 = SputnikMap.getBoundingBox();
        Context mapContext = SputnikMap.getContext();
        Intent intent = new Intent(mapContext, CoffeeAPI.class);
        Drawable drawable = mResourceProxy.getDrawable(ResourceProxy.bitmap.marker_default);
        coffeeAPI.getBbox(mapContext, boxE6);
        coffeeAPI.onHandleIntent(intent);
        if (coffeeOverlay == null) {
//            coffeeOverlay = new ItemizedIconOverlay<>(mapContext, coffeeAPI.getOverlayList(), null);
            coffeeOverlay = new CoffeeOverlay(coffeeAPI.getOverlayList(), drawable, null, mResourceProxy);
            SputnikMap.getOverlays().add(coffeeOverlay);
        } else {
            SputnikMap.getOverlays().remove(coffeeOverlay);
//            coffeeOverlay = new ItemizedIconOverlay<>(mapContext, coffeeAPI.getOverlayList(), null);
            coffeeOverlay = new CoffeeOverlay(coffeeAPI.getOverlayList(), drawable, null, mResourceProxy);
            SputnikMap.getOverlays().add(coffeeOverlay);
        }
    }

}
