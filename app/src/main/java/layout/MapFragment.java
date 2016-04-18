package layout;import android.app.AlertDialog;import android.app.Fragment;import android.app.FragmentManager;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.content.SharedPreferences;import android.graphics.Color;import android.graphics.drawable.Drawable;import android.location.Location;import android.os.Build;import android.os.Bundle;import android.provider.Settings;import android.support.annotation.Nullable;import android.support.design.widget.FloatingActionButton;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.view.animation.AlphaAnimation;import android.view.animation.Animation;import android.view.animation.AnimationUtils;import android.widget.Button;import android.widget.ImageView;import android.widget.RelativeLayout;import android.widget.TextView;import android.widget.Toast;import com.agrass.coffeemap.BuildConfig;import com.agrass.coffeemap.CafeItem;import com.agrass.coffeemap.CafeOverlay;import com.agrass.coffeemap.ClientIntentRequest;import com.agrass.coffeemap.CurrentLocationManager;import com.agrass.coffeemap.MapsActivity;import com.agrass.coffeemap.TaskGetPointsHandler;import com.agrass.coffeemap.R;import com.agrass.coffeemap.RetinaTileSource;import com.agrass.coffeemap.TaskStatusHandler;import com.flipboard.bottomsheet.BottomSheetLayout;import com.flipboard.bottomsheet.OnSheetDismissedListener;import com.google.android.gms.auth.api.Auth;import com.google.android.gms.auth.api.signin.GoogleSignInOptions;import com.google.android.gms.common.ConnectionResult;import com.google.android.gms.common.GoogleApiAvailability;import com.google.android.gms.common.SignInButton;import com.google.android.gms.common.api.GoogleApiClient;import org.osmdroid.ResourceProxy;import org.osmdroid.api.IGeoPoint;import org.osmdroid.events.DelayedMapListener;import org.osmdroid.events.MapListener;import org.osmdroid.events.ScrollEvent;import org.osmdroid.events.ZoomEvent;import org.osmdroid.tileprovider.MapTileProviderBasic;import org.osmdroid.tileprovider.tilesource.ITileSource;import org.osmdroid.util.BoundingBoxE6;import org.osmdroid.util.GeoPoint;import org.osmdroid.util.ResourceProxyImpl;import org.osmdroid.views.MapView;import org.osmdroid.views.overlay.ItemizedIconOverlay;import org.osmdroid.views.overlay.TilesOverlay;import java.util.ArrayList;public class MapFragment extends Fragment {    private static final String MAP_PREFS = "MapPrefs";    private static final String MAP_PREFS_SCROLL_X = "scrollX";    private static final String MAP_PREFS_SCROLL_Y = "scrollY";    private static final String MAP_PREFS_ZOOM_LEVEL = "zoom";    private static final String UI_PREFS_VISIBLE_ADD_BUTTONS = "visible_buttons";    private static final String BASE_URLS[] = {            "http://a.tilessputnik.ru/tiles/kmt2/",            "http://b.tilessputnik.ru/tiles/kmt2/",            "http://c.tilessputnik.ru/tiles/kmt2/",            "http://d.tilessputnik.ru/tiles/kmt2/"};    public static ClientIntentRequest request;    protected MapView SputnikMap;    protected TilesOverlay mTilesOverlay;    private SharedPreferences settings;    private GeoPoint Moscow = new GeoPoint(55751556, 37624482);    private BottomSheetLayout bottomSheetLayout;    private ResourceProxy mResourceProxy;    private CafeOverlay coffeeOverlay;    private Drawable drawable;    private View bottomSheetView;    private Drawable returnDrawable;    private Animation FadeInAnim = new AlphaAnimation(0, 1);    private Animation FadeOutAnim = new AlphaAnimation(1, 0);    private boolean isVisibleAddsElements;    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        FadeInAnim.setDuration(500);        FadeOutAnim.setDuration(500);        Log.e("onCreate", "Was");    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        Context context = inflater.getContext().getApplicationContext();        mResourceProxy = new ResourceProxyImpl(context);        Log.e("onCreateView","Was");        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {            drawable = getResources().getDrawable(R.drawable.ic_place_36dp, null);        } else {            drawable = getResources().getDrawable(R.drawable.ic_place_36dp);        }        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_map, container, false);        SputnikMap = (MapView) relativeLayout.findViewById(R.id.openMapView);        float scale = SputnikMap.getResources().getDisplayMetrics().density;        int imageSize = (int) (256 * scale);        ITileSource tileSource = new RetinaTileSource("Sputnik", 1, 18, imageSize, ".png", BASE_URLS);        SputnikMap.setTileSource(tileSource);        MapTileProviderBasic mProvider = new MapTileProviderBasic(context, tileSource);        mTilesOverlay = new TilesOverlay(mProvider, context);        mTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);        SputnikMap.getOverlays().clear();        SputnikMap.getOverlays().add(mTilesOverlay);        SputnikMap.setMultiTouchControls(true);        return relativeLayout;    }    @Override    public void onActivityCreated(final Bundle savedInstanceState) {        super.onActivityCreated(savedInstanceState);        Context context = getActivity();        Log.e("onActivityCreated","Was");        request = new ClientIntentRequest(context);        request.setTaskGetPointsHandler(new TaskGetPointsHandler() {            @Override            public void taskSuccessful(ArrayList<CafeItem> overlayItemArrayList) {                SputnikMap.getOverlays().remove(coffeeOverlay);                SputnikMap.invalidate();                coffeeOverlay = new CafeOverlay(overlayItemArrayList, drawable,                        new ItemizedIconOverlay.OnItemGestureListener<CafeItem>() {                            @Override                            public boolean onItemSingleTapUp(int index, CafeItem item) {                                returnDrawable = item.getDrawable();                                item.setMarker(drawable);                                SputnikMap.invalidate();                                showMenuSheet(item);                                return true;                            }                            @Override                            public boolean onItemLongPress(int index, CafeItem item) {                                return true;                            }                        }, mResourceProxy);                SputnikMap.getOverlays().add(coffeeOverlay);                SputnikMap.invalidate();            }            @Override            public void taskFailed() {                Log.wtf("Task", "FAILED");            }        });        request.setTaskStatusHandler(new TaskStatusHandler() {            @Override            public void taskSuccessful(String response) {                showStatusDialog(response);            }            @Override            public void taskFailed() {                showStatusDialog(null);            }        });        Intent intent = new Intent(context, ClientIntentRequest.class);        request.onHandleIntent(intent);        bottomSheetLayout = (BottomSheetLayout) getActivity().findViewById(R.id.bottomSheet);        bottomSheetLayout.setPeekOnDismiss(true);        bottomSheetLayout.setVerticalScrollBarEnabled(true);        bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, bottomSheetLayout, false);        settings = context.getSharedPreferences(MAP_PREFS, Context.MODE_PRIVATE);        changeVisibilityAddButtons(settings.getBoolean(UI_PREFS_VISIBLE_ADD_BUTTONS, false));        SputnikMap.getController().setZoom(settings.getInt(MAP_PREFS_ZOOM_LEVEL, 11));        SputnikMap.getController().setCenter(new GeoPoint(                settings.getFloat(MAP_PREFS_SCROLL_X, (float) Moscow.getLatitude()),                settings.getFloat(MAP_PREFS_SCROLL_Y, (float) Moscow.getLongitude())        ));        refreshCoffeeOverlay();        SputnikMap.setMapListener(new DelayedMapListener( new MapListener() {            @Override            public boolean onScroll(ScrollEvent event) {                refreshCoffeeOverlay();                Log.wtf("onScroll", "event");                return true;            }            @Override            public boolean onZoom(ZoomEvent event) {                refreshCoffeeOverlay();                Log.wtf("onZoom", "event");                return true;            }        }, 250));        final FloatingActionButton buttonAddPoint = (FloatingActionButton) getActivity().findViewById(R.id.buttonAddPoint);        final FloatingActionButton buttonCurrentPosition = (FloatingActionButton) getActivity().findViewById(R.id.buttonCurrentLocation);        final FloatingActionButton buttonAbout = (FloatingActionButton) getActivity().findViewById(R.id.buttonAbout);        final Button dialogButCancel = (Button) getActivity().findViewById(R.id.dialogButCancel);        final Button dialogButOk = (Button) getActivity().findViewById(R.id.dialogButOk);        if (BuildConfig.isAdminVer) {            buttonAbout.setVisibility(View.VISIBLE);        } else {            buttonAbout.setVisibility(View.GONE);        }        buttonAbout.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                request.getApiVersion();            }        });        dialogButOk.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                IGeoPoint location = SputnikMap.getMapCenter();                FragmentManager fragmentManager = getFragmentManager();                Fragment addCafeFragment = new AddCafeFragment();                Bundle bundle = new Bundle();                bundle.putDouble("Latitude", location.getLatitude());                bundle.putDouble("Longitude", location.getLongitude());                addCafeFragment.setArguments(bundle);                fragmentManager.beginTransaction()                        .addToBackStack("MapFragment")                        .hide(MapFragment.this)                        .replace(R.id.fragment_layout, addCafeFragment)                        .commit();            }        });        dialogButCancel.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                changeVisibilityAddButtons(isAddButtonVisible());            }        });        buttonAddPoint.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                if (MapsActivity.account == null) {                    Toast.makeText(getActivity(), R.string.SignIn, Toast.LENGTH_LONG).show();                    SignInButton signInBtn = (SignInButton) getActivity().findViewById(R.id.sign_in_button);                    Animation signInBtnAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.button_sign_anim);                    signInBtn.startAnimation(signInBtnAnim);                } else {                    changeVisibilityAddButtons(isAddButtonVisible());                }            }        });        buttonCurrentPosition.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {            }        });    }    public void changeVisibilityAddButtons(boolean visible) {        Button dialogButOk = (Button) getActivity().findViewById(R.id.dialogButOk);        Button dialogButCancel = (Button) getActivity().findViewById(R.id.dialogButCancel);        TextView helpTextView = (TextView) getActivity().findViewById(R.id.textView_help_add_button);        ImageView newCafeMarker = (ImageView) getActivity().findViewById(R.id.imageViewCenterMarker);        FloatingActionButton buttonAddPoint = (FloatingActionButton) getActivity().findViewById(R.id.buttonAddPoint);        if (visible) {            dialogButOk.setVisibility(View.VISIBLE);            helpTextView.setVisibility(View.VISIBLE);            newCafeMarker.setVisibility(View.VISIBLE);            dialogButCancel.setVisibility(View.VISIBLE);            buttonAddPoint.setVisibility(View.INVISIBLE);            isVisibleAddsElements = true;        } else {            dialogButOk.setVisibility(View.INVISIBLE);            helpTextView.setVisibility(View.INVISIBLE);            newCafeMarker.setVisibility(View.INVISIBLE);            dialogButCancel.setVisibility(View.INVISIBLE);            buttonAddPoint.setVisibility(View.VISIBLE);            isVisibleAddsElements = false;        }    }    private boolean isAddButtonVisible() {        FloatingActionButton buttonAddPoint = (FloatingActionButton) getActivity().findViewById(R.id.buttonAddPoint);        return buttonAddPoint.getVisibility() == View.VISIBLE;    }    @Override    public void onPause() {        SharedPreferences.Editor editor = settings.edit();        editor.putFloat(MAP_PREFS_SCROLL_X, (float) SputnikMap.getMapCenter().getLatitude());        editor.putFloat(MAP_PREFS_SCROLL_Y, (float) SputnikMap.getMapCenter().getLongitude());        editor.putInt(MAP_PREFS_ZOOM_LEVEL, SputnikMap.getZoomLevel());        editor.putBoolean(UI_PREFS_VISIBLE_ADD_BUTTONS, isVisibleAddsElements);        editor.apply();        super.onPause();        Log.e("OnResume", "Was");    }    @Override    public void onResume() {        super.onResume();        Log.e("OnResume","Was");        changeVisibilityAddButtons(settings.getBoolean(UI_PREFS_VISIBLE_ADD_BUTTONS, false));    }    private void refreshCoffeeOverlay() {        BoundingBoxE6 boxE6 = SputnikMap.getBoundingBox();        request.refreshPoints(boxE6);    }    private void showMenuSheet(final CafeItem item) {        final TextView textName = (TextView) bottomSheetView.findViewById(R.id.name);        final TextView textFullOpenHours = (TextView) bottomSheetView.findViewById(R.id.FullOH);        final TextView textOpenHour = (TextView) bottomSheetView.findViewById(R.id.open_hour);        textName.setText(item.getName() != null ? item.getName() : "name is null");        textFullOpenHours.setText(item.getSchedule() != null ? item.getSchedule() : "OH null");        textFullOpenHours.setVisibility(View.INVISIBLE);        textOpenHour.setText(item.getEndTimeWork() != null ? item.getEndTimeWork() : "time is null");        bottomSheetLayout.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener() {            @Override            public void onSheetStateChanged(BottomSheetLayout.State state) {                if (state.equals(BottomSheetLayout.State.EXPANDED)) {                    textFullOpenHours.setVisibility(View.VISIBLE);                    textFullOpenHours.startAnimation(FadeInAnim);                } else {                    textFullOpenHours.setVisibility(View.INVISIBLE);                    textFullOpenHours.startAnimation(FadeOutAnim);                }            }        });        bottomSheetLayout.addOnSheetDismissedListener(new OnSheetDismissedListener() {            @Override            public void onDismissed(BottomSheetLayout bottomSheetLayout) {                item.setMarker(returnDrawable);                SputnikMap.invalidate();            }        });        bottomSheetLayout.showWithSheetView(bottomSheetView);    }    private Location enableLocationManager() {        try {            CurrentLocationManager curLocManager = new CurrentLocationManager(getActivity());            Location location = curLocManager.getCurrentLocation();            Toast.makeText(getActivity(),                    String.valueOf(location.getLatitude()) +                            String.valueOf(location.getLongitude()),                    Toast.LENGTH_LONG).show();            return location;        } catch (Exception err) {            Log.wtf("Location manager exception", err.getMessage());        }        return null;    }    private void scrollOnMarker(IGeoPoint markerGeoPoint, CafeItem item) {        SputnikMap.getController().animateTo(markerGeoPoint);    }    public void showInternetAlertDialog(final Context context) {        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);        alertDialog.setTitle("GPS is settings");        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");        alertDialog.setIcon(R.drawable.ic_vpn_lock_24dp);        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {            public void onClick(DialogInterface dialog,int which) {                Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);                context.startActivity(intent);            }        });        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {            public void onClick(DialogInterface dialog, int which) {                dialog.cancel();            }        });        alertDialog.show();    }    public void showGPSAlertDialog(final Context mContext) {        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);        alertDialog.setTitle("GPS is not enabled ");        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");        alertDialog.setIcon(R.drawable.ic_gps_off_24dp);        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {            public void onClick(DialogInterface dialog,int which) {                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);                mContext.startActivity(intent);            }        });        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {            public void onClick(DialogInterface dialog, int which) {                dialog.cancel();            }        });        alertDialog.show();    }    public void showStatusDialog(@Nullable String message) {        if (message != null) {            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());            builder.setMessage(String.format("Версия сервера: %s\nВерсия приложения: %s", message, BuildConfig.VERSION_NAME))                    .setTitle(R.string.about);            builder.setPositiveButton("ок", new DialogInterface.OnClickListener() {                @Override                public void onClick(DialogInterface dialog, int which) {                    dialog.dismiss();                }            });            AlertDialog dialog = builder.create();            dialog.show();        } else {            message = "Сервер недоступен";            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());            builder.setMessage(message)                    .setTitle(R.string.about);            builder.setPositiveButton("ок", new DialogInterface.OnClickListener() {                @Override                public void onClick(DialogInterface dialog, int which) {                    dialog.dismiss();                }            });            AlertDialog dialog = builder.create();            dialog.show();        }    }}