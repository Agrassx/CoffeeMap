package com.agrass.coffeemap.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.agrass.coffeemap.R;

public class ScheduleWorkView extends LinearLayout {

    private View rootView;

    public ScheduleWorkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.view_week, this);
//      TODO: Complete View
    }


}
