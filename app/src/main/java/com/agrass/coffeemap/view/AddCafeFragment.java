package com.agrass.coffeemap.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.model.cafe.LocationCafe;
import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.agrass.coffeemap.view.map.MapFragment;

import org.json.JSONException;
import org.json.JSONObject;

import layout.openHourDialog.SetOpenHoursFragment;

import static com.agrass.coffeemap.app.Constants.LOCATION;


//    TODO: Layout, Import ButterKnife, Send to Server new place
public class AddCafeFragment extends BaseFragment implements AddCafeView {
    private static final String LOG = AddCafeFragment.class.getName();
    private MainActivityView activityView;
    private String cafeName;
    private String openHours;
    private float userRating;
    private String comment;
    private double latitude;
    private double longitude;

    private Bundle args;
    private LocationCafe location;

    public static AddCafeFragment newInstance(MainActivityView activityView) {
        Bundle args = new Bundle();
        AddCafeFragment fragment = new AddCafeFragment();
        fragment.setActivityView(activityView);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setActivityView(ActivityView activityView) {
        this.activityView = (MainActivityView) activityView;
    }

    public AddCafeView getIView() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.args = getArguments();
        if (args == null) {
            activityView.callBackButton(true);
            return;
        }
        Log.e(LOG, args.getString(LOCATION));
        location = new LocationCafe(args.getString(LOCATION));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_cafe, container, false);
    }

    @Deprecated
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        latitude = getArguments().getDouble("Latitude");
        longitude = getArguments().getDouble("Longitude");

        final FloatingActionButton saveButton = (FloatingActionButton) getView().findViewById(R.id.buttonSavePoint);
        final RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.ratingBar);
        final RadioGroup radioGroupOpenHours = (RadioGroup) getView().findViewById(R.id.radioGroupOpenHours);
//        final EditText editCafeName = (EditText) getView().findViewById(R.id.editTextCafeName);
        final EditText editComment = (EditText) getView().findViewById(R.id.editTextComment);

        radioGroupOpenHours.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButAlwaysOpen:
                    openHours = "24/7";
                    break;
                case R.id.radioButSecond:
                    openHours = "09:00-20:00";
                    break;
                case R.id.radioButAnother:
                    getOpenHours();
                    break;
            }
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> userRating = rating);

        saveButton.setOnClickListener(v -> {
//            try {
//                postNewPlace(editCafeName.getText().toString(), latitude, longitude);
//                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            getActivity().onBackPressed();

        });

    }

    @Deprecated
    private void getOpenHours() {
        DialogFragment dialog = new SetOpenHoursFragment();
        dialog.show(getFragmentManager(), "openHours");
//        TODO: get data from dialog
    }

    @Deprecated
    private void postNewPlace(String cafeName, double latitude, double longitude) throws JSONException {
        checkFields();

        JSONObject newPoint = new JSONObject();
        JSONObject location = new JSONObject();

        location.put("lat",latitude);
        location.put("lon",longitude);
        newPoint.put("name", cafeName);
        newPoint.put("location", location);
        newPoint.put("rating", userRating);
        newPoint.put("user_id", MapsActivity.account.getId());
        newPoint.put("user_name", MapsActivity.account.getDisplayName());
        newPoint.put("access_token", MapsActivity.account.getIdToken());

        Log.e("AddPoint (JSON): ", newPoint.toString());
        MapFragment.request.addPoint(newPoint);

    }

    @Deprecated
    private void checkFields() {
//        TODO: check fields cafeName, rating bar and comment
    }


    @Override
    public void onBackPressed() {
        activityView.callBackButton(true);
    }

    @Override
    public void showMessage(String error) {

    }
}
