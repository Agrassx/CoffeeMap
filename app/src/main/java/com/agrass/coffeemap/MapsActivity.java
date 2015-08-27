package com.agrass.coffeemap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.TilesOverlay;





public class MapsActivity extends Activity {
    public MapView SputnikMap;
    public TilesOverlay mTilesOverlay;
    public MapTileProviderBasic mProvider;
    public static final String	baseUrls[] = { "http://a.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://b.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://c.tiles.maps.sputnik.ru/tiles/kmt2/",
                                               "http://d.tiles.maps.sputnik.ru/tiles/kmt2/"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mProvider = new MapTileProviderBasic(getApplicationContext());
        final ITileSource tileSource = new XYTileSource("Sputnik", null, 4, 18, 256, ".png", baseUrls);
        mProvider.setTileSource(tileSource);
        mTilesOverlay = new TilesOverlay(mProvider, this.getBaseContext());
        SputnikMap = (MapView)findViewById(R.id.openmapview);
        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
        SputnikMap.getOverlays().add(mTilesOverlay);
        SputnikMap.setBuiltInZoomControls(true);
        SputnikMap.getController().setZoom(11);
        SputnikMap.getController().setCenter(new GeoPoint(55751556, 37624482));

    }

   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
        mMapView = new MapView(inflater.getContext(), 256, mResourceProxy,  tileProviderArray);
        setHardwareAccelerationOff();
        return mMapView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //final Context context = this.getActivity();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setHardwareAccelerationOff() {
        // Turn off hardware acceleration here, or in manifest
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
    mMapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }*/

    @Override
    public void onPause() {
        super.onPause();
        /*final SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(PREFS_TILE_SOURCE, mMapView.getTileProvider().getTileSource().name());
        edit.putInt(PREFS_SCROLL_X, mMapView.getScrollX());
        edit.putInt(PREFS_SCROLL_Y, mMapView.getScrollY());
        edit.putInt(PREFS_ZOOM_LEVEL, mMapView.getZoomLevel());
        edit.putBoolean(PREFS_SHOW_LOCATION, mLocationOverlay.isMyLocationEnabled());
        edit.putBoolean(PREFS_SHOW_COMPASS, mCompassOverlay.isCompassEnabled());
        edit.commit();

        this.mLocationOverlay.disableMyLocation();
        this.mCompassOverlay.disableCompass();*/
    }

  /*  @Override
    public void onResume() {
        super.onResume();
        final String tileSourceName = mPrefs.getString(PREFS_TILE_SOURCE,
                TileSourceFactory.DEFAULT_TILE_SOURCE.name());
        try {
            final ITileSource tileSource = TileSourceFactory.getTileSource(tileSourceName);
            mMapView.setTileSource(tileSource);
        } catch (final IllegalArgumentException e) {
            mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        }
        if (mPrefs.getBoolean(PREFS_SHOW_LOCATION, false)) {
            this.mLocationOverlay.enableMyLocation();
        }
        if (mPrefs.getBoolean(PREFS_SHOW_COMPASS, false)) {
            this.mCompassOverlay.enableCompass();
        }
    }
    public MapView getMapView() {
        return mMapView;
    } */

}
