package com.agrass.coffeemap.view.base;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment implements FragmentView {
    public abstract void setActivityView(ActivityView activityView);
    public abstract FragmentView getIView();
    public static void newInstanse() {

    }
}

