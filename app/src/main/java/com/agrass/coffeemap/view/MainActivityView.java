package com.agrass.coffeemap.view;

import android.os.Bundle;

import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;

public interface MainActivityView extends ActivityView {
    void redirectTo(BaseFragment fragment);
    void redirectTo(BaseFragment fragment, Bundle args);
}
