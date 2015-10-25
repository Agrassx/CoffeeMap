package com.agrass.coffeemap;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.ArrayList;


public class MapFragment extends Fragment {

    protected MapView SputnikMap;
    protected TilesOverlay mTilesOverlay;
    private GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    private ClientIntentRequest request;
    private JsonTaskHandler taskHandler;
    private ResourceProxy mResourceProxy;
    private Drawable drawable;
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
        drawable = mResourceProxy.getDrawable(ResourceProxy.bitmap.marker_default);
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
        MapListener mapListener = new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                refreshCoffeeOverlay();
                Log.wtf("scroll", "it was scrolled");
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                refreshCoffeeOverlay();
                Log.wtf("zoom", "it was zoomed");
                return false;
            }
        };
        SputnikMap.setMapListener(new DelayedMapListener(mapListener, 250));

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
        Context context = this.getActivity().getApplication().getBaseContext();
        Intent intent = new Intent(context, ClientIntentRequest.class);
        BoundingBoxE6 boxE6 = SputnikMap.getBoundingBox();
        request = new ClientIntentRequest(context);
        taskHandler = new JsonTaskHandler() {
            @Override
            public void taskSuccessful(ArrayList<OverlayItem> overlayItemArrayList) {
                SputnikMap.getOverlays().remove(coffeeOverlay);
                SputnikMap.invalidate();
                coffeeOverlay = new CoffeeOverlay(overlayItemArrayList, drawable, null, mResourceProxy);
                SputnikMap.getOverlays().add(coffeeOverlay);
                SputnikMap.invalidate();
            }

            @Override
            public void taskFaild() {
                Log.wtf("Task","FAILD");
            }
        };
        request.setJsonTaskHandler(taskHandler);
        request.setBbox(boxE6);
        request.onHandleIntent(intent);
    }


}
