package com.agrass.coffeemap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapsActivity extends Activity {

    private MapView SputnikMap;
    private TilesOverlay mTilesOverlay;
    CompassOverlay mCompassOverlay;
    private MyLocationNewOverlay mLocationOverlay;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
    private MapTileProviderBasic mProvider;
    private MinimapOverlay mMinimapOverlay;
    private static final String PREFS_NAME = "MyPrefsFile";
    private ResourceProxy mResourceProxy;
    private static final String	baseUrls[] = { "http://a.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://b.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://c.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://d.tiles.maps.sputnik.ru/tiles/kmt2/"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("On Create", "On Create…");
        setContentView(R.layout.activity_maps);


        final float scale = getBaseContext().getResources().getDisplayMetrics().density;
        final int imageSize = (int) (256 * scale);
        final ITileSource tileSource = new RetinaTileSource("Sputnik", null, 1, 18, imageSize, ".png", baseUrls);
        mProvider = new MapTileProviderBasic(getApplicationContext(), tileSource);
        mTilesOverlay = new TilesOverlay(mProvider, this.getBaseContext());
        SputnikMap = (MapView)findViewById(R.id.openmapview);
        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
        SputnikMap.getOverlays().clear();
        SputnikMap.getOverlays().add(mTilesOverlay);
        SputnikMap.setBuiltInZoomControls(true);
        SputnikMap.setMultiTouchControls(true);
        SputnikMap.getController().setZoom(11);
        SputnikMap.getController().setCenter(new GeoPoint(55751556, 37624482));

        mCompassOverlay = new CompassOverlay(getApplicationContext(), new InternalCompassOrientationProvider(getApplicationContext()),
                SputnikMap);
        mLocationOverlay = new MyLocationNewOverlay(getApplicationContext(), new GpsMyLocationProvider(getApplicationContext()),
                SputnikMap);
        mMinimapOverlay = new MinimapOverlay(getApplicationContext(), SputnikMap.getTileRequestCompleteHandler());

        SputnikMap.setTileSource(tileSource);

        ArrayList CoffeeMarkers = new ArrayList<OverlayItem>();
        GeoPoint NE = new GeoPoint(SputnikMap.getBoundingBox().getLatNorthE6(),SputnikMap.getBoundingBox().getLonEastE6());
        GeoPoint SW = new GeoPoint(SputnikMap.getBoundingBox().getLatSouthE6(),SputnikMap.getBoundingBox().getLonWestE6());
        CoffeeMarkers.add(new OverlayItem("NE", "NE", NE));
        CoffeeMarkers.add(new OverlayItem("SW", "SW", SW));
        ItemizedIconOverlay<OverlayItem> CoffeeMarkersItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(
                this, CoffeeMarkers, null);

        SputnikMap.getOverlays().add(CoffeeMarkersItemizedIconOverlay);


        SputnikMap.setMapListener(new DelayedMapListener(new MapListener() {
            public boolean onZoom(final ZoomEvent e) {
                Log.wtf("zoom", "it was zoomed");
                Integer Zoom = SputnikMap.getZoomLevel();
                Log.wtf("zoom", "Zoom is "+Zoom.toString());
                Integer N = SputnikMap.getBoundingBox().getLatNorthE6();
                Integer E = SputnikMap.getBoundingBox().getLonEastE6();
                Integer S = SputnikMap.getBoundingBox().getLatSouthE6();
                Integer W = SputnikMap.getBoundingBox().getLonWestE6();
                Log.wtf("N", "N is " + N.toString());
                Log.wtf("E", "E is " + E.toString());
                Log.wtf("S", "S is " + S.toString());
                Log.wtf("W", "W is " + W.toString());
                return true;
            }

            public boolean onScroll(final ScrollEvent e) {
                Log.e("scroll", "it was scrolled");
                return true;
            }
        }, 1000));

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("On pause", "On pause…");
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("PREFS_SCROLL_X", (float) SputnikMap.getMapCenter().getLatitude());
        editor.putFloat("PREFS_SCROLL_Y", (float) SputnikMap.getMapCenter().getLongitude());
        editor.putInt("PREFS_ZOOM_LEVEL", SputnikMap.getZoomLevel());
        editor.apply();
    }

   @Override
    public void onResume() {
       super.onResume();
       Log.e("On Resume", "On Resume…");
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       float SX = settings.getFloat("PREFS_SCROLL_X", 55751556);
       float SY = settings.getFloat("PREFS_SCROLL_Y", 37624482);
       SputnikMap.getController().setCenter(new GeoPoint(SX, SY));
       SputnikMap.getController().setZoom(settings.getInt("PREFS_ZOOM_LEVEL", 11));

//       Integer bottomLat1 =
       Integer bottomLat2 = SputnikMap.getBoundingBox().getLatNorthE6();


//       Log.e("bottomLat", bottomLat1.toString());
       Log.e("LatNorthE6", bottomLat2.toString());

       GeoPoint NE = new GeoPoint(SputnikMap.getBoundingBox().getLatNorthE6(),SputnikMap.getBoundingBox().getLonEastE6());
       GeoPoint SW = new GeoPoint(SputnikMap.getBoundingBox().getLatSouthE6(),SputnikMap.getBoundingBox().getLonWestE6());
       ArrayList CoffeeMarkers = new ArrayList<OverlayItem>();
       CoffeeMarkers.add(new OverlayItem("NE", "NE", NE));
       CoffeeMarkers.add(new OverlayItem("SW", "SW", SW));
       ItemizedIconOverlay<OverlayItem> CoffeeMarkersItemizedIconOverlay
               = new ItemizedIconOverlay<OverlayItem>(
               this, CoffeeMarkers, null);

       SputnikMap.getOverlays().add(CoffeeMarkersItemizedIconOverlay);

//       final String tileSourceName = mPrefs.getString(PREFS_TILE_SOURCE,
//                TileSourceFactory.DEFAULT_TILE_SOURCE.name());
//       try {
//           final ITileSource tileSource = TileSourceFactory.getTileSource(tileSourceName);
//           SputnikMap.setTileSource(tileSource);
//       } catch (final IllegalArgumentException e) {
//           SputnikMap.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
//       }
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

}
