package com.agrass.coffeemap.view.map;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.model.map.ClusterItemCafeRender;
import com.agrass.coffeemap.presenter.MapPresenter;
import com.agrass.coffeemap.view.BottomSheetCafeInfo;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment2 extends BaseFragment implements MapView {

    private static final String LOG = MapFragment2.class.getName();
    private static final String BASE_URLS[] = {
            "http://a.tilessputnik.ru/tiles/kmt2/",
            "http://b.tilessputnik.ru/tiles/kmt2/",
            "http://c.tilessputnik.ru/tiles/kmt2/",
            "http://d.tilessputnik.ru/tiles/kmt2/"};

    String url[] = {"http://tiles.maps.sputnik.ru/"};

    private MapPresenter presenter;
    private MainActivityView activityView;
    private GoogleMap map;
    private final GeoPoint Moscow = new GeoPoint(55751556, 37624482);
    protected TilesOverlay mTilesOverlay;


    @BindView(R2.id.buttonAddPoint) FloatingActionButton buttonAddPoint;
    @BindView(R2.id.layoutAddCafeHelp) RelativeLayout layoutAddCafeHelp;
    @BindView(R2.id.dialogButtonOk) Button dialogButtonOk;
    @BindView(R2.id.dialogButtonCancel) Button dialogButtonCancel;

    public static MapFragment2 newInstance(MainActivityView activityView) {
        MapFragment2 fragment = new MapFragment2();
        fragment.setActivityView(activityView);
        return fragment;
    }

    private void setActivityView(MainActivityView activityView) {
        this.activityView = activityView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
//        ButterKnife.bind(getActivity());

        presenter = new MapPresenter(this);
        buttonAddPoint.setOnClickListener(v ->
                presenter.addPointClick(getFragmentManager(), activityView)
        );
        dialogButtonCancel.setOnClickListener(v -> presenter.cancelButtonClick());
        dialogButtonOk.setOnClickListener(v -> presenter.okButtonClick(
                activityView,
                this.map.getCameraPosition().target
        ));
//        mResourceProxy = new ResourceProxyImpl(context);
        com.google.android.gms.maps.MapView mMapView
                = (com.google.android.gms.maps.MapView) view.findViewById(R.id.mapView);
//      FIXME:  mMapView.onCreate(); mMapView.onResume();
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
//        mapView = (org.osmdroid.views.MapView) view.findViewById(R.id.openMapView);
//
//        float scale = mapView.getResources().getDisplayMetrics().density;
//        int imageSize = (int) (256 * scale);
//        ITileSource tileSource = new RetinaTileSource("Sputnik", 2, 18, imageSize, ".png", BASE_URLS);
//        mapView.setTileSource(tileSource);
//
//        MapTileProviderBasic mProvider = new MapTileProviderBasic(getActivity(), tileSource);
//        mTilesOverlay = new TilesOverlay(mProvider, getActivity());
//        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
//        mapView.getOverlays().clear();
//        mapView.getOverlays().add(mTilesOverlay);
//
////        mapView.getTileProvider().setTileSource(tileSource);
//
//        mapView.setMultiTouchControls(true);
//        mapView.setMapListener(new DelayedMapListener(this, 250));
        return view;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        presenter.getPoints(map.getProjection().getVisibleRegion().latLngBounds);
        return true;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        presenter.init(googleMap, getActivity());
        presenter.animateCamera(googleMap, new LatLng(
                Moscow.getLatitude(),
                Moscow.getLongitude()
        ));
    }

    @Override
    public void onCameraMove() {
        presenter.cluster();
    }

    @Override
    public void showMarkers(List<Cafe> list) {
//        clusterManager.clearItems();
//        clusterManager.addItems(list);
//        clusterManager.cluster();
    }

    @Override
    public void showAddCafeLayout() {
        buttonAddPoint.animate();
        buttonAddPoint.hide();
        layoutAddCafeHelp.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (buttonAddPoint.isShown()) {
            activityView.callBackButton(true);
            return;
        }
        layoutAddCafeHelp.setVisibility(View.GONE);
        buttonAddPoint.show();
    }

    @Override
    public void setActivityView(ActivityView activityView) {

    }

    public MapView getIView() {
        return this;
    }

    @Override
    public boolean onClusterClick(Cluster<Cafe> cluster) {
        return false;
    }

    @Override
    public boolean onClusterItemClick(Cafe cafe) {
        presenter.changeMarkerColor(cafe);
        presenter.showBottomSheetDialogFragment(
                getFragmentManager(),
                BottomSheetCafeInfo.newInstance(cafe, this)
        );
        return false;
    }

    @Override
    public void clearSelection(Cafe cafe) {
        presenter.clearSelection(cafe);
    }
}
