package com.agrass.coffeemap;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
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
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.List;

public class MapFragment extends Fragment {

    protected MapView SputnikMap;
    protected TilesOverlay mTilesOverlay;
    private static final String PREFS_NAME = "MyPrefsFile";
    private GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    private CoffeeAPI coffeeAPI;
//    private CoffeeItemizedOverlay coffeeOverlay;
    private ItemizedIconOverlay<OverlayItem> coffeeOverlay;
    private static final String	baseUrls[] = { "http://a.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://b.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://c.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://d.tiles.maps.sputnik.ru/tiles/kmt2/" };


    public static MapFragment newInstance() {
        return new MapFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ResourceProxy mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
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


        SputnikMap.setMapListener(new DelayedMapListener(new MapListener() {
            public boolean onZoom(final ZoomEvent e) {
                Log.wtf("zoom", "it was zoomed");
                SputnikMap.postInvalidate();
                refreshCoffeeOverlay();
                SputnikMap.invalidate();
                return true;
            }

            public boolean onScroll(final ScrollEvent e) {
                Log.wtf("scroll", "it was scrolled");
                SputnikMap.postInvalidate();
                refreshCoffeeOverlay();
                SputnikMap.invalidate();
                return true;
            }
        }, 500));
    }

    @Override
    public void onPause() {
//        mPrefs =
//        SharedPreferences.Editor edit = mPrefs.edit();
//        edit.putInt("PREFS_SCROLL_X", SputnikMap.getScrollX());
//        edit.putInt("PREFS_SCROLL_Y", SputnikMap.getScrollX());
//        edit.putInt("PREFS_ZOOM_LEVEL", SputnikMap.getZoomLevel());
//        edit.apply();
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
//       Log.e("On Resume", "On Resume…");
//       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//       float SX = settings.getFloat("PREFS_SCROLL_X", 55751556);
//       float SY = settings.getFloat("PREFS_SCROLL_Y", 37624482);
//       SputnikMap.get().getController().animateTo(new GeoPoint(SX, SY));
//       SputnikMap.get().getController().setZoom(settings.getInt("PREFS_ZOOM_LEVEL", 11));
//       SputnikMap.get().getController().setCenter(new GeoPoint(SX, SY));
    }

        private void refreshCoffeeOverlay() {
            List<Overlay> overlayList = SputnikMap.getOverlays();
            Context mapContext = SputnikMap.getContext();
            ResourceProxy resourceProxy = new ResourceProxyImpl(mapContext);
            coffeeAPI.getCoffeeAPI(mapContext, SputnikMap.getBoundingBox());

            if (coffeeOverlay == null) {

                coffeeOverlay = new ItemizedIconOverlay<OverlayItem>(mapContext, coffeeAPI.getOverlayList(),null);
                overlayList.add(coffeeOverlay);
                SputnikMap.invalidate();

            } else {

                overlayList.remove(coffeeOverlay);
                coffeeOverlay = new ItemizedIconOverlay<OverlayItem>(mapContext, coffeeAPI.getOverlayList(),null);
                SputnikMap.invalidate();
                overlayList.add(coffeeOverlay);
                SputnikMap.invalidate();
            }
        }


}
