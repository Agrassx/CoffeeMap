package com.agrass.coffeemap.view.cafe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.app.CoffeeApplication;
import com.agrass.coffeemap.model.cafe.LocationCafe;
import com.agrass.coffeemap.model.cafe.NewPlace;
import com.agrass.coffeemap.presenter.AddCafePresenter;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.agrass.coffeemap.app.Constants.LOCATION;

public class AddCafeFragment extends BaseFragment implements AddCafeView {
    public static final String ARG_LOCATION = "location";
    private static final String LOG = AddCafeFragment.class.getName();
    private MainActivityView activityView;
    private AddCafePresenter presenter;

    @BindView(R2.id.editTextName) TextInputEditText editTextName;
    @BindView(R2.id.editTextWorkTime) EditText editTextWorkTime;
    @BindView(R2.id.ratingBarCafe) RatingBar ratingBarCafe;
    @BindView(R2.id.editTextComment) TextInputEditText editTextComment;
    @BindView(R2.id.buttonSavePoint) FloatingActionButton buttonSavePoint;

    private Bundle args;
    private LocationCafe location;

    public static AddCafeFragment newInstance(MainActivityView activityView, LatLng point) {
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION, new Gson().toJson(point));
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
        buttonSavePoint.setOnClickListener(v -> presenter.onSendButtonClick(getNewPlace()));
        editTextWorkTime.setOnClickListener(
                v -> presenter.onEditTextWorkTimeClick(getFragmentManager(), this)
        );
        return view;
    }

    private NewPlace getNewPlace() {
        return new NewPlace(
                location,
                editTextName.getText().toString(),
                ratingBarCafe.getRating(),
                editTextWorkTime.getText().toString(),
                editTextComment.getText().toString(),
                CoffeeApplication.getInstance().getAccount().getId(),
                CoffeeApplication.getInstance().getAccount().getDisplayName(),
                CoffeeApplication.getInstance().getAccount().getPhotoUrl(),
                CoffeeApplication.getInstance().getAccount().getIdToken()
        );
    }

    @Override
    public void onBackPressed() {
        activityView.callBackButton(true);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onScheduleSelect(String schedule) {
        editTextWorkTime.setText(schedule);
    }
}
