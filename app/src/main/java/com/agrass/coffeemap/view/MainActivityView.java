package com.agrass.coffeemap.view;

import com.agrass.coffeemap.view.base.ActivityView;
import com.agrass.coffeemap.view.base.BaseFragment;

public interface MainActivityView extends ActivityView {
    void redirectTo(BaseFragment fragment);
}
