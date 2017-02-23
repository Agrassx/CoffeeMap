package com.agrass.coffeemap.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.Toast;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.model.parsers.TimeParser;
import com.agrass.coffeemap.presenter.DialogTimePickerPresenter;
import com.agrass.coffeemap.view.cafe.ScheduleCafeWorkFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogTimePicker extends DialogFragment implements TimePickerView {
    public static final String TAB_WORK_FROM = "from";
    public static final String TAB_WORK_TO = "to";

    private DialogTimePickerPresenter presenter;
    private DialogListener listener;

    @BindView(R2.id.tabHost) TabHost tabHost;
    @BindView(R2.id.timePickerFrom) TimePicker timePickerFrom;
    @BindView(R2.id.timePickerTo) TimePicker timePickerTo;

    public static DialogTimePicker newInstance(DialogListener listener) {
        DialogTimePicker fragment = new DialogTimePicker();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_set_time_work, null);
        ButterKnife.bind(this, view);
        presenter = new DialogTimePickerPresenter(this);
        presenter.setDialogListener(listener);
        initTabHost();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(R.string.positive_button, null)
                .setNegativeButton(R.string.negative_button, null)
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    private void initTabHost() {
        tabHost.setup();
        timePickerFrom.setIs24HourView(true);
        timePickerTo.setIs24HourView(true);

        setTimeFrom(9, 0);
        setTimeTo(18, 0);

        TabHost.TabSpec spec = tabHost.newTabSpec(TAB_WORK_FROM);
        spec.setContent(R.id.tabWorkFrom);
        spec.setIndicator(getResources().getString(R.string.tab_from_name));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(TAB_WORK_TO);
        spec.setContent(R.id.tabWorkTo);
        spec.setIndicator(getResources().getString(R.string.tab_to_name));
        tabHost.addTab(spec);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    private void setTimeFrom(int hour, int minute) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerFrom.setHour(hour);
            timePickerFrom.setMinute(minute);
        } else {
            timePickerFrom.setCurrentHour(hour);
            timePickerFrom.setCurrentMinute(minute);
        }
    }

    private void setTimeTo(int hour, int minute) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerTo.setHour(hour);
            timePickerTo.setMinute(minute);
        } else {
            timePickerTo.setCurrentHour(hour);
            timePickerTo.setCurrentMinute(minute);
        }
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            positiveButton.setOnClickListener(v -> presenter.onPositiveButtonClick(
                    tabHost.getCurrentTabTag(),
                    new TimeParser(timePickerFrom.getHour(), timePickerFrom.getMinute()),
                    new TimeParser(timePickerTo.getHour(), timePickerTo.getMinute())

            ));
        } else {
            positiveButton.setOnClickListener(v -> presenter.onPositiveButtonClick(
                    tabHost.getCurrentTabTag(),
                    new TimeParser(timePickerFrom.getCurrentHour(), timePickerFrom.getCurrentMinute()),
                    new TimeParser(timePickerTo.getCurrentHour(), timePickerTo.getCurrentMinute())
            ));
        }
        negativeButton.setOnClickListener(v -> dismiss());

    }

    @Override
    public void changeTab(String tabTag) {
        tabHost.setCurrentTabByTag(tabTag);
    }

    @Override
    public void dismissDialog() {
        this.dismiss();
    }
}
