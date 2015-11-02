package com.agrass.coffeemap;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;


public class MapsActivity extends FragmentActivity {

    public BottomSheetLayout bottomSheetLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



//        MenuSheetView menuSheetView =  new MenuSheetView(MapsActivity.this, MenuSheetView.MenuType.GRID, "Create...", null);
//        menuSheetView.inflateMenu(R.menu.activity_main_drawer);
//        bottomSheetLayout.showWithSheetView(menuSheetView);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
