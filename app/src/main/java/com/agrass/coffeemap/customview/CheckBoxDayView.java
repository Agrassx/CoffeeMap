package com.agrass.coffeemap.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrass.coffeemap.R;

public class CheckBoxDayView extends RelativeLayout {

    private String dayName;
    private boolean isChecked;
    private View rootView;
    private TextView dayNameTextView;
    private CheckBox dayCheckBox;

    public CheckBoxDayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CheckBoxDayView,
                0,
                0
        );
        try {
            dayName = attributes.getString(R.styleable.CheckBoxDayView_dayName);
            isChecked = attributes.getBoolean(R.styleable.CheckBoxDayView_isChecked, false);
        } finally {
            attributes.recycle();
        }

        rootView = inflate(context, R.layout.view_day, this);
        dayNameTextView = (TextView) rootView.findViewById(R.id.dayName);
        dayCheckBox = (CheckBox) rootView.findViewById(R.id.dayIsCheck);
        dayNameTextView.setText(dayName);
    }

    public String getDayName() {
        return dayName;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
