package com.agrass.coffeemap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;


import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
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

    public MapView SputnikMap;
    public TilesOverlay mTilesOverlay;
    CompassOverlay mCompassOverlay;
    public MyLocationNewOverlay mLocationOverlay;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
    public MapTileProviderBasic mProvider;
    public MinimapOverlay mMinimapOverlay;
    public static final String PREFS_NAME = "MyPrefsFile";
    public ResourceProxy mResourceProxy;
    public static final String	baseUrls[] = { "http://a.tiles.maps.sputnik.ru/tiles/kmt2/",
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

        Projection projection = SputnikMap.getProjection();
        IGeoPoint NE = projection.getNorthEast();

        Integer bottomLat1 = SputnikMap.getProjection().getNorthEast().getLatitudeE6();
        Integer bottomLat2 = SputnikMap.getBoundingBox().getLatNorthE6();
        Integer bottomLat3 = projection.fromPixels(0, 0).getLatitudeE6();

        Log.e("bottomLat", bottomLat1.toString());
        Log.e("bottomLon", bottomLat2.toString());
        Log.e("bottomLon", bottomLat3.toString());
//        Log.e("bottomLon", bottomLon.toString());

        ArrayList CoffeeMarkers = new ArrayList<OverlayItem>();
        // CoffeeMarkers.add(new OverlayItem("SW", "SW", NE));
        CoffeeMarkers.add(new OverlayItem("NE", "NE", NE));
        CoffeeMarkers.add(new OverlayItem("Moscow", "Test", new GeoPoint(55.75, 37.616667)));

        ItemizedIconOverlay<OverlayItem> CoffeeMarkersItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(
                this, CoffeeMarkers, null);

        SputnikMap.getOverlays().add(CoffeeMarkersItemizedIconOverlay);


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
//        editor.putString(PREFS_TILE_SOURCE, SputnikMap.getTileProvider().getTileSource().name());
//        editor.putBoolean(PREFS_SHOW_LOCATION, mLocationOverlay.isMyLocationEnabled());
//        editor.putBoolean(PREFS_SHOW_COMPASS, mCompassOverlay.isCompassEnabled());
        editor.commit();
    }

   @Override
    public void onResume() {
       super.onResume();
       Log.e("On Resume", "On Resume…");
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       float SX = settings.getFloat("PREFS_SCROLL_X", 55751556);
       float SY = settings.getFloat("PREFS_SCROLL_Y", 37624482);
       SputnikMap.getController().setCenter(new GeoPoint(SX,SY));
       SputnikMap.getController().setZoom(settings.getInt("PREFS_ZOOM_LEVEL", 11));

//       final String tileSourceName = mPrefs.getString(PREFS_TILE_SOURCE,
//                TileSourceFactory.DEFAULT_TILE_SOURCE.name());
//       try {
//           final ITileSource tileSource = TileSourceFactory.getTileSource(tileSourceName);
//           SputnikMap.setTileSource(tileSource);
//       } catch (final IllegalArgumentException e) {
//           SputnikMap.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
//       }
//       if (mPrefs.getBoolean(PREFS_SHOW_LOCATION, false)) {
//           this.mLocationOverlay.enableMyLocation();
//       }
//       if (mPrefs.getBoolean(PREFS_SHOW_COMPASS, false)) {
//           this.mCompassOverlay.enableCompass();
//       }
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.e("On Stop", "On Stop…");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("On destroy", "On Destroy…");
    }

    public MapView getMapView() {
        return SputnikMap;
    }

}
