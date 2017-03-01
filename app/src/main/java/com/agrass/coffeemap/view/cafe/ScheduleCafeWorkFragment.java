package com.agrass.coffeemap.view.cafe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.customview.ScheduleWorkView;
import com.agrass.coffeemap.model.parsers.TimeParser;
import com.agrass.coffeemap.presenter.ScheduleCafeWorkPresenter;
import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.agrass.coffeemap.view.base.FragmentView;
import com.agrass.coffeemap.view.dialog.DialogTimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

//  TODO: create two scheduleWorkViews for complete schedule of work

public class ScheduleCafeWorkFragment extends BaseFragment implements ScheduleCafeWorkIView {

    @BindView(R2.id.scheduleWork) ScheduleWorkView scheduleWork;
    @BindView(R2.id.buttonSaveSchedule) FloatingActionButton buttonSaveSchedule;

    private OnScheduleEditComplete onScheduleEditComplete;
    private ScheduleCafeWorkPresenter presenter;

    public static ScheduleCafeWorkFragment newInstance(OnScheduleEditComplete view) {
        ScheduleCafeWorkFragment fragment = new ScheduleCafeWorkFragment();
        fragment.setOnScheduleWorkCompleteView(view);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_cafe_work, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        presenter = new ScheduleCafeWorkPresenter(this);
        scheduleWork.setOnCloseAtClickListener(v -> presenter.showDialog(
                getFragmentManager(), new DialogTimePicker()));
        scheduleWork.setOnOpenAtClickListener(v -> presenter.showDialog(
                getFragmentManager(), new DialogTimePicker()));
        buttonSaveSchedule.setOnClickListener(v -> presenter.onButtonSaveClick(
                scheduleWork.getScheduleWork(),
                onScheduleEditComplete
        ));
        return view;
    }

    public void setOnScheduleWorkCompleteView(OnScheduleEditComplete view) {
        this.onScheduleEditComplete = view;
    }

    @Override
    public void setActivityView(ActivityView activityView) {
    }

    @Override
    public FragmentView getIView() {
        return this;
    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onPositiveButtonClick(TimeParser timeFrom, TimeParser timeTo) {
        scheduleWork.setTextCloseAt(timeFrom.toString());
        scheduleWork.setTextOpenAt(timeTo.toString());
    }

    @Override
    public void onNegativeButtonClick() {

    }
}
