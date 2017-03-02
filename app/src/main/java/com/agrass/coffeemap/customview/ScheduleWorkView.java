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
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String OFF = "off";
    private static final String ALWAYS = "24/7";
    private static final String ALL_DAY = "00:00-24:00";
    private static final String SCHEDULE_SPLIT = ";";

    private View rootView;
    private Switch switchIsAllDay;
    private TextView textViewOpenAt;
    private TextView textViewCloseAt;
    private LinearLayout linearLayoutWorkTime;
    private LinearLayout layoutOpenAt;
    private LinearLayout layoutCloseAt;
    private CheckBoxWeekView checkBoxWeekView;


    public ScheduleWorkView(Context context) {
        super(context);
        init(context);
    }

    public ScheduleWorkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
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
        return getStringSchedule(
                checkBoxWeekView,
                textViewOpenAt.getText().toString(),
                textViewCloseAt.getText().toString()
        );
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

//    TODO: make class-serializer for work schedule
    private String getStringSchedule(CheckBoxWeekView days, String open, String close) {

        if (days.getDayOff().size() == 7) {
            return "";
        }

        if (switchIsAllDay.isChecked() && days.isAllDaysChecked()) {
            return ALWAYS;
        }

        if (days.isAllDaysChecked()) {
            return open + SPLIT + close;
        }

        if (switchIsAllDay.isChecked() && days.getDayOn().size() > 3) {
            return getScheduleForMoreThen3Days(days, ALL_DAY);
        }

        if (switchIsAllDay.isChecked() && days.getDayOn().size() < 4) {
            return getScheduleForLessThen4Days(days, ALL_DAY);
        }

        if (days.getDayOn().size() > 3) {
            return getScheduleForMoreThen3Days(days, open, close);
        }

        if (days.getDayOn().size() < 4) {
            return getScheduleForLessThen4Days(days, open, close);
        }
//        TODO: Add new rules for For x > 2 days in a row;
//        TODO: Add new rules for days in a row: Su-Tu;
        return "Something was wrong :(";
    }

    private String getScheduleForMoreThen3Days(CheckBoxWeekView days, String time) {
        List<CheckBoxDayView> daysOn = days.getDayOn();
        String schedule = daysOn.get(0).getDayID() + SPLIT + daysOn.get(daysOn.size() - 1).getDayID();

        schedule += SPACE + time + SCHEDULE_SPLIT;

        for (CheckBoxDayView day: days.getDayOff()) {
            schedule += SPACE + day.getDayID() + SPACE + OFF + SCHEDULE_SPLIT;
        }
        schedule = schedule.substring(0, schedule.length() - 1);
        return schedule;
    }

    private String getScheduleForLessThen4Days(CheckBoxWeekView days, String time) {
        String schedule = "";
        for (CheckBoxDayView day: days.getDayOn()) {
            schedule += day.getDayID() + SPACE + time + SCHEDULE_SPLIT + SPACE;
        }
        schedule = schedule.substring(0, schedule.length() - 2);
        return schedule;
    }

    private String getScheduleForMoreThen3Days(CheckBoxWeekView days, String open, String close) {
        List<CheckBoxDayView> daysOn = days.getDayOn();
        String schedule = daysOn.get(0).getDayID() + SPLIT + daysOn.get(daysOn.size() - 1).getDayID();

        schedule += SPACE + open + SPLIT + close + SCHEDULE_SPLIT;

        for (CheckBoxDayView day: days.getDayOff()) {
            schedule += SPACE + day.getDayID() + SPACE + OFF + SCHEDULE_SPLIT;
        }
        schedule = schedule.substring(0, schedule.length() - 1);
        return schedule;
    }

    private String getScheduleForLessThen4Days(CheckBoxWeekView days, String open, String close) {
        String schedule = "";
        for (CheckBoxDayView day: days.getDayOn()) {
            schedule += day.getDayID() + SPACE + open + SPLIT + close + SCHEDULE_SPLIT + SPACE;
        }
        schedule = schedule.substring(0, schedule.length() - 2);
        return schedule;
    }
}
