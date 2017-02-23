package com.agrass.coffeemap.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.agrass.coffeemap.R;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxWeekView extends LinearLayout {

    private View rootView;
    private List<CheckBoxDayView> dayViewList;

    public CheckBoxWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.view_week, this);
        dayViewList = new ArrayList<>();

        dayViewList.add((CheckBoxDayView) rootView.findViewById(R.id.Mo));
        dayViewList.add((CheckBoxDayView) rootView.findViewById(R.id.Tu));
        dayViewList.add((CheckBoxDayView) rootView.findViewById(R.id.We));
        dayViewList.add((CheckBoxDayView) rootView.findViewById(R.id.Th));
        dayViewList.add((CheckBoxDayView) rootView.findViewById(R.id.Fr));
        dayViewList.add((CheckBoxDayView) rootView.findViewById(R.id.Sa));
        dayViewList.add((CheckBoxDayView) rootView.findViewById(R.id.Su));
    }

    public boolean isAllDaysChecked() {
        for (CheckBoxDayView day: dayViewList) {
            if (!day.isChecked()) return day.isChecked();
        }
        return true;
    }


    public List<CheckBoxDayView> getDayOff() {
        List<CheckBoxDayView> list = new ArrayList<>();
        for (CheckBoxDayView day: dayViewList) {
            if (!day.isChecked()) {
                list.add(day);
            }
        }
        return list;
    }

    public List<CheckBoxDayView> getDayOn() {
        List<CheckBoxDayView> list = new ArrayList<>();
        for (CheckBoxDayView day: dayViewList) {
            if (day.isChecked()) {
                list.add(day);
            }
        }
        return list;
    }

    public List<CheckBoxDayView> getDayViewList() {
        return dayViewList;
    }
}
