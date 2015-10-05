package com.agrass.coffeemap;

import android.app.Fragment;
import android.content.Context;
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
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.TilesOverlay;

/**
 * Created by Agrass- on 05.10.15.
 */
public class MapFragment extends Fragment {

    protected MapView SputnikMap;
    protected TilesOverlay mTilesOverlay;
    private static final String PREFS_NAME = "MyPrefsFile";
    private ResourceProxy mResourceProxy;
    private Drawable drawable;
    private GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    private CoffeeAPI coffeeAPI = new CoffeeAPI();
    private CoffeeItemizedOverlay coffeeOverlay;
    final private Context context = this.getActivity();
    private static final String	baseUrls[] = { "http://a.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://b.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://c.tiles.maps.sputnik.ru/tiles/kmt2/",
            "http://d.tiles.maps.sputnik.ru/tiles/kmt2/" };


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }



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

//        coffeeOverlay = new CoffeeItemizedOverlay(drawable, mResourceProxy);
//        SputnikMap.get().getOverlays().add(coffeeOverlay);
//
//        SputnikMap.get().setMapListener(new DelayedMapListener(new MapListener() {
//            public boolean onZoom(final ZoomEvent e) {
//                Log.wtf("zoom", "it was zoomed");
//                refreshCoffeeOverlay();
//                return true;
//            }
//
//            public boolean onScroll(final ScrollEvent e) {
//                Log.wtf("scroll", "it was scrolled");
//                Log.wtf("API", "begin");
//
//                refreshCoffeeOverlay();
//
//                Log.wtf("API", "end");
//                return true;
//            }
//        }, 500));
    }


}
