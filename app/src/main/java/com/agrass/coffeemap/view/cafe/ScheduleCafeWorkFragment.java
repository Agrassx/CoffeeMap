package com.agrass.coffeemap.view.cafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.customview.ScheduleWorkView;
import com.agrass.coffeemap.model.parsers.TimeParser;
import com.agrass.coffeemap.presenter.ScheduleCafeWorkPresenter;
import com.agrass.coffeemap.view.activity.MainActivityView;
import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.agrass.coffeemap.view.base.FragmentView;
import com.agrass.coffeemap.view.dialog.DialogTimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ScheduleCafeWorkFragment extends BaseFragment implements ScheduleCafeWorkIView {

    @BindView(R2.id.scheduleWork) ScheduleWorkView scheduleWork;

    private ActivityView activityView;
    private ScheduleCafeWorkPresenter presenter;

    public static ScheduleCafeWorkFragment newInstance(MainActivityView activityView) {
        Bundle args = new Bundle();
        ScheduleCafeWorkFragment fragment = new ScheduleCafeWorkFragment();
        fragment.setActivityView(activityView);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_cafe_work, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        presenter = new ScheduleCafeWorkPresenter(this);
        scheduleWork.setOnCloseAtClickListener(v -> presenter.showDialog(
                getFragmentManager(), DialogTimePicker.newInstance(this)));
        scheduleWork.setOnOpenAtClickListener(v -> presenter.showDialog(
                getFragmentManager(), DialogTimePicker.newInstance(this)));
        return view;
    }

    @Override
    public void setActivityView(ActivityView activityView) {
        this.activityView = activityView;
    }

    @Override
    public FragmentView getIView() {
        return this;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onPositiveButtonClick(TimeParser timeFrom, TimeParser timeTo) {
        scheduleWork.setTextCloseAt(timeTo.toString());
        scheduleWork.setTextOpenAt(timeFrom.toString());
    }

    @Override
    public void onNegativeButtonClick() {

    }
}
