package com.agrass.coffeemap.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.agrass.coffeemap.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleWorkView extends LinearLayout implements CompoundButton.OnCheckedChangeListener {

    private static final String SPLIT = "-";
    private static final String SPACE = " ";
    private static final String SCHEDULE_SPLIT = "; ";

    private Map<String, Integer> weekDays = new HashMap<>();

    private View rootView;
    private Switch switchIsAllDay;
    private TextView textViewOpenAt;
    private TextView textViewCloseAt;
    private LinearLayout linearLayoutWorkTime;
    private LinearLayout layoutOpenAt;
    private LinearLayout layoutCloseAt;
    private CheckBoxWeekView checkBoxWeekView;


    public ScheduleWorkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initWeekDays();
    }

    private void initWeekDays() {
        weekDays.put("Mo", 1);
        weekDays.put("Tu", 2);
        weekDays.put("We", 3);
        weekDays.put("Th", 4);
        weekDays.put("Fr", 5);
        weekDays.put("Sa", 6);
        weekDays.put("Su", 7);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.view_schedule_work, this);
        switchIsAllDay = (Switch) rootView.findViewById(R.id.switchIsAllDay);
        textViewOpenAt = (TextView) rootView.findViewById(R.id.textViewOpenAt);
        textViewCloseAt = (TextView) rootView.findViewById(R.id.textViewCloseAt);
        linearLayoutWorkTime = (LinearLayout) rootView.findViewById(R.id.linearLayoutWorkTime);
        layoutOpenAt = (LinearLayout) rootView.findViewById(R.id.layoutOpenAt);
        layoutCloseAt = (LinearLayout) rootView.findViewById(R.id.layoutCloseAt);
        checkBoxWeekView = (CheckBoxWeekView) rootView.findViewById(R.id.checkBoxWeekView);
        switchIsAllDay.setOnCheckedChangeListener(this);
    }



    public void setOnOpenAtClickListener(OnClickListener listener) {
        layoutOpenAt.setOnClickListener(listener);
    }

    public void setOnCloseAtClickListener(OnClickListener listener) {
        layoutCloseAt.setOnClickListener(listener);
    }

    public String getScheduleWork() {
        return null;
    }

    public void setTextOpenAt(String text) {
        textViewOpenAt.setText(text);
    }

    public void setTextCloseAt(String text) {
        textViewCloseAt.setText(text);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            linearLayoutWorkTime.setVisibility(GONE);
            return;
        }
        linearLayoutWorkTime.setVisibility(VISIBLE);
    }

    /**
    * TODO: Create class for Open Hour Serializing;
    */
    public String test(List<CheckBoxDayView> days, String open, String close) {
        if (switchIsAllDay.isChecked() && checkBoxWeekView.isAllDaysChecked()) {
            return "24/7";
        }

        if (checkBoxWeekView.isAllDaysChecked()) {
            return open + SPLIT + close;
        }

        if (switchIsAllDay.isChecked()) {
            return "";
        }

        getTimeOfWorking(checkBoxWeekView.getDayOn());

        return "";
    }

    private void getTimeOfWorking(List<CheckBoxDayView> dayViews) {

    }
}
