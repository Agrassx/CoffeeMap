package com.agrass.coffeemap;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
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
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.ArrayList;
import java.util.Calendar;


public class MapFragment extends Fragment {

    private static final String MAP_PREFS = "MapPrefs";
    private static final String MAP_PREFS_SCROLL_X = "scrollX";
    private static final String MAP_PREFS_SCROLL_Y = "scrollY";
    private static final String MAP_PREFS_ZOOM_LEVEL = "zoom";
    private static final String BASE_URLS[] = {
            "http://a.tilessputnik.ru/tiles/kmt2/",
            "http://b.tilessputnik.ru/tiles/kmt2/",
            "http://c.tilessputnik.ru/tiles/kmt2/",
            "http://d.tilessputnik.ru/tiles/kmt2/"};
    protected MapView SputnikMap;
    protected TilesOverlay mTilesOverlay;
    private SharedPreferences settings;
    private GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    private BottomSheetLayout bottomSheetLayout;
    private ResourceProxy mResourceProxy;
    private CafeOverlay coffeeOverlay;
    private Drawable drawable;
    private View bottomSheetView;
    private Drawable returnDrawable;

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
        drawable = getResources().getDrawable(R.drawable.marker01, null);
        drawable = getResources().getDrawable(R.drawable.ic_place_36dp, null);
        SputnikMap = new MapView(inflater.getContext(), 256, mResourceProxy);
        return SputnikMap;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bottomSheetLayout = (BottomSheetLayout) getActivity().findViewById(R.id.bottomsheet);
        bottomSheetLayout.setPeekOnDismiss(true);
        bottomSheetLayout.setPeekSheetTranslation(200);
        bottomSheetLayout.setVerticalScrollBarEnabled(true);
        bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet,
                bottomSheetLayout, false);
        Context context = getActivity().getBaseContext();
        settings = context.getSharedPreferences(MAP_PREFS, Context.MODE_PRIVATE);
        float scale = SputnikMap.getResources().getDisplayMetrics().density;
        int imageSize = (int) (256 * scale);
        ITileSource tileSource = new RetinaTileSource("Sputnik", null, 1, 18, imageSize, ".png", BASE_URLS);
        SputnikMap.setTileSource(tileSource);
        MapTileProviderBasic mProvider = new MapTileProviderBasic(context, tileSource);
        mTilesOverlay = new TilesOverlay(mProvider, context);
        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
        SputnikMap.getOverlays().clear();
        SputnikMap.getOverlays().add(mTilesOverlay);
        SputnikMap.setMultiTouchControls(true);
        SputnikMap.getController().setZoom(settings.getInt(MAP_PREFS_ZOOM_LEVEL, 11));
        SputnikMap.getController().setCenter(new GeoPoint(settings.getFloat(MAP_PREFS_SCROLL_X,
                (float) Moscow.getLatitude()), settings.getFloat(MAP_PREFS_SCROLL_Y,
                (float) Moscow.getLongitude())));
        MapListener mapListener = new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                refreshCoffeeOverlay();
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                refreshCoffeeOverlay();
                return false;
            }
        };
        SputnikMap.setMapListener(new DelayedMapListener(mapListener, 250));

        final FloatingActionButton buttonAddPoint = (FloatingActionButton) getActivity().findViewById(R.id.buttonAddPoint);
        buttonAddPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(MAP_PREFS_SCROLL_X, (float) SputnikMap.getMapCenter().getLatitude());
        editor.putFloat(MAP_PREFS_SCROLL_Y, (float) SputnikMap.getMapCenter().getLongitude());
        editor.putInt(MAP_PREFS_ZOOM_LEVEL, SputnikMap.getZoomLevel());
        editor.apply();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refreshCoffeeOverlay() {
        final Context context = this.getActivity().getApplication().getBaseContext();
        Intent intent = new Intent(context, ClientIntentRequest.class);
        BoundingBoxE6 boxE6 = SputnikMap.getBoundingBox();
        ClientIntentRequest request = new ClientIntentRequest(context);
        JsonTaskHandler taskHandler = new JsonTaskHandler() {
            @Override
            public void taskSuccessful(ArrayList<CafeItem> overlayItemArrayList) {
                SputnikMap.getOverlays().remove(coffeeOverlay);
                SputnikMap.invalidate();
                coffeeOverlay = new CafeOverlay(overlayItemArrayList, drawable,
                        new ItemizedIconOverlay.OnItemGestureListener<CafeItem>() {
                            @Override
                            public boolean onItemSingleTapUp(int index, CafeItem item) {
                                returnDrawable = item.getDrawable();
                                item.setMarker(drawable);
                                SputnikMap.invalidate();
//                                scrollOnMarker(item.getPoint(), item);
                                showMenuSheet(item);
                                return false;
                            }

                            @Override
                            public boolean onItemLongPress(int index, CafeItem item) {
                                return false;
                            }
                }, mResourceProxy);
                SputnikMap.getOverlays().add(coffeeOverlay);
                SputnikMap.invalidate();
            }

            @Override
            public void taskFailed() {
                Log.wtf("Task", "FAILED");
            }
        };
        request.setJsonTaskHandler(taskHandler);
        request.setBoundingBox(boxE6);
        request.onHandleIntent(intent);
    }

    private void showMenuSheet(final CafeItem item) {
        TextView textName = (TextView) bottomSheetView.findViewById(R.id.name);
        TextView textfullOH = (TextView) bottomSheetView.findViewById(R.id.FullOH);
        TextView textOpenHour = (TextView) bottomSheetView.findViewById(R.id.open_hour);

        textName.setText(item.getName() != null ? item.getName() : "name is null");
        textfullOH.setText(item.getSchedule() != null ? item.getSchedule() : "OH null");
        textOpenHour.setText(item.getEndTimeWork() != null ? item.getEndTimeWork() : "time is null");

        bottomSheetLayout.addOnSheetDismissedListener(new OnSheetDismissedListener() {
            @Override
            public void onDismissed(BottomSheetLayout bottomSheetLayout) {
                item.setMarker(returnDrawable);
                SputnikMap.invalidate();
            }
        });

        bottomSheetLayout.setPeekSheetTranslation(200);
        bottomSheetLayout.showWithSheetView(bottomSheetView);
    }

    private void scrollOnMarker(IGeoPoint markerGeoPoint, CafeItem item) {
        SputnikMap.getController().animateTo(markerGeoPoint);
    }



}
