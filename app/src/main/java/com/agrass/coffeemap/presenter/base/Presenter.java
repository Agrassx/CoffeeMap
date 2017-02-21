package com.agrass.coffeemap.presenter.base;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.agrass.coffeemap.view.base.BaseFragment;

public interface Presenter {

    void redirectTo(FragmentManager fragmentManager, BaseFragment fragment);
    void redirectTo(FragmentManager fragmentManager, BaseFragment fragment, boolean isBackStack);
    void redirectTo(FragmentManager fragmentManager,
                    BaseFragment fragment, Bundle args, boolean isBackStack);
    void showBottomSheetDialogFragment(FragmentManager fragmentManager,
                                       BottomSheetDialogFragment bottomSheetDialogFragment);

    void showDialog(FragmentManager fragmentManager, DialogFragment dialogFragment);
    void onStop();
}
