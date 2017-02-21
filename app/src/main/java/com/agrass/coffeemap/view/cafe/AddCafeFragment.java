package com.agrass.coffeemap.view.cafe;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.model.cafe.LocationCafe;
import com.agrass.coffeemap.presenter.AddCafePresenter;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import layout.openHourDialog.SetOpenHoursFragment;

import static com.agrass.coffeemap.app.Constants.LOCATION;

//  TODO: Send to Server new place
public class AddCafeFragment extends BaseFragment implements AddCafeView {
    private static final String LOG = AddCafeFragment.class.getName();
    private MainActivityView activityView;
    private AddCafePresenter presenter;

    @BindView(R2.id.editTextName) EditText editTextName;
    @BindView(R2.id.editTextWorkTime) EditText editTextWorkTime;
    @BindView(R2.id.ratingBarCafe) RatingBar ratingBarCafe;
    @BindView(R2.id.editTextComment) EditText editTextComment;
    @BindView(R2.id.buttonSavePoint) FloatingActionButton buttonSavePoint;

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
        View view = inflater.inflate(R.layout.fragment_add_cafe, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        presenter = new AddCafePresenter(this);
        buttonSavePoint.setOnClickListener(v ->  presenter.onSendButtonClick());
        editTextWorkTime.setOnClickListener(v -> presenter.openDialog(getFragmentManager()));
        return view;
    }

    @Deprecated
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Deprecated
    private void getOpenHours() {
        DialogFragment dialog = new SetOpenHoursFragment();
//        dialog.show(, "openHours");
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
//        newPoint.put("rating", userRating);
//        newPoint.put("user_id", MapsActivity.account.getId());
//        newPoint.put("user_name", MapsActivity.account.getDisplayName());
//        newPoint.put("access_token", MapsActivity.account.getIdToken());
        Log.e("AddPoint (JSON): ", newPoint.toString());
//        MapFragment.request.addPoint(newPoint);

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
    public void showMessage(String message) {

    }
}
