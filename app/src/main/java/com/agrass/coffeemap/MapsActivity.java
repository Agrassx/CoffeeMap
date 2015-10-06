package com.agrass.coffeemap;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MapsActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf("On Create", "On Create…");
        setContentView(R.layout.activity_maps);

        android.app.FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.findFragmentById(R.id.MapFragment) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.MapFragment, new MapFragment());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.e("On pause", "On pause…");
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putFloat("PREFS_SCROLL_X", (float) SputnikMap.get().getMapCenter().getLatitude());
//        editor.putFloat("PREFS_SCROLL_Y", (float) SputnikMap.get().getMapCenter().getLongitude());
//        editor.putInt("PREFS_ZOOM_LEVEL", SputnikMap.get().getZoomLevel());
//        editor.apply();
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



}
