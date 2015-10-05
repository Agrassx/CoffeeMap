package com.agrass.coffeemap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

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


public class MapsActivity extends Activity {

    protected ThreadLocal<MapView> SputnikMap = new ThreadLocal<>();
    protected ThreadLocal<TilesOverlay> mTilesOverlay = new ThreadLocal<>();
    private static final String PREFS_NAME = "MyPrefsFile";
    private ResourceProxy mResourceProxy;
    private Drawable drawable;
    private GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    private CoffeeAPI coffeeAPI = new CoffeeAPI();
    private CoffeeItemizedOverlay coffeeOverlay;
    private static final String	baseUrls[] = { "http://a.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://b.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://c.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://d.tiles.maps.sputnik.ru/tiles/kmt2/"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf("On Create", "On Create…");
        setContentView(R.layout.activity_maps);
        drawable = getDrawable(R.drawable.common_ic_googleplayservices);
        mResourceProxy = new ResourceProxyImpl(getApplicationContext());
        float scale = getBaseContext().getResources().getDisplayMetrics().density;
        int imageSize = (int) (256 * scale);
        ITileSource tileSource = new RetinaTileSource("Sputnik", null, 1, 18, imageSize, ".png", baseUrls);
        MapTileProviderBasic mProvider = new MapTileProviderBasic(getApplicationContext(), tileSource);
        mTilesOverlay.set(new TilesOverlay(mProvider, this.getBaseContext()));
        SputnikMap.set((MapView) findViewById(R.id.openmapview));
        mTilesOverlay.get().setLoadingBackgroundColor(Color.TRANSPARENT);
        SputnikMap.get().getOverlays().clear();
        SputnikMap.get().getOverlays().add(mTilesOverlay.get());
        SputnikMap.get().setMultiTouchControls(true);
        SputnikMap.get().getController().setZoom(11);
        SputnikMap.get().getController().setCenter(Moscow);
        SputnikMap.get().setTileSource(tileSource);

        coffeeOverlay = new CoffeeItemizedOverlay(drawable, mResourceProxy);
        SputnikMap.get().getOverlays().add(coffeeOverlay);

        SputnikMap.get().setMapListener(new DelayedMapListener(new MapListener() {
            public boolean onZoom(final ZoomEvent e) {
                Log.wtf("zoom", "it was zoomed");
                refreshCoffeeOverlay();
                return true;
            }

            public boolean onScroll(final ScrollEvent e) {
                Log.wtf("scroll", "it was scrolled");
                Log.wtf("API", "begin");

                refreshCoffeeOverlay();

                Log.wtf("API", "end");
                return true;
            }
        }, 500));

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("On pause", "On pause…");
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("PREFS_SCROLL_X", (float) SputnikMap.get().getMapCenter().getLatitude());
        editor.putFloat("PREFS_SCROLL_Y", (float) SputnikMap.get().getMapCenter().getLongitude());
        editor.putInt("PREFS_ZOOM_LEVEL", SputnikMap.get().getZoomLevel());
//        editor.apply();
    }

   @Override
    public void onResume() {
       super.onResume();
       Log.e("On Resume", "On Resume…");
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       float SX = settings.getFloat("PREFS_SCROLL_X", 55751556);
       float SY = settings.getFloat("PREFS_SCROLL_Y", 37624482);
       SputnikMap.get().getController().animateTo(new GeoPoint(SX, SY));
       SputnikMap.get().getController().setZoom(settings.getInt("PREFS_ZOOM_LEVEL", 11));
//       SputnikMap.get().getController().setCenter(new GeoPoint(SX, SY));

   }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("On Stop", "On Stop…");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("On destroy", "On Destroy…");
    }

    private void refreshCoffeeOverlay() {

        if (coffeeOverlay == null) {

            Log.wtf("coffeeOverlay","Coffee overlay is null");
            coffeeAPI.getCoffeeAPI(this, SputnikMap.get().getBoundingBox());
            coffeeOverlay.addAll(coffeeAPI.getOverlayList());
            SputnikMap.get().invalidate();

        } else {
            Log.wtf("coffeeOverlay", "Coffee overlay not null<><><>");
//            coffeeOverlay.clear();
            SputnikMap.get().invalidate();
            coffeeAPI.getCoffeeAPI(this, SputnikMap.get().getBoundingBox());
            coffeeOverlay.addAll(coffeeAPI.getOverlayList());
            SputnikMap.get().invalidate();
        }

    }

}
