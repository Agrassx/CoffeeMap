package com.agrass.coffeemap.presenter.base;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.model.Model;
import com.agrass.coffeemap.model.ModelImpl;
import com.agrass.coffeemap.view.base.BaseFragment;
import com.agrass.coffeemap.view.base.FragmentView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter implements Presenter {

    protected Model model = new ModelImpl();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private FragmentView fragmentView;

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public BasePresenter() {

    }

    @Override
    public void redirectTo(FragmentManager fragmentManager, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentView = fragment.getIView();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void redirectTo(FragmentManager fragmentManager, BaseFragment fragment, boolean isBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentView = fragment.getIView();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (isBackStack) fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public void redirectTo(FragmentManager fragmentManager,
                           BaseFragment fragment, Bundle args, boolean isBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentView = fragment.getIView();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (isBackStack) fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public void showBottomSheetDialogFragment(FragmentManager fragmentManager,
                                              BottomSheetDialogFragment bottomSheetDialogFragment) {
        bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.getTag());
    }

    @Override
    public void showDialog(FragmentManager fragmentManager, DialogFragment dialogFragment) {
        dialogFragment.show(fragmentManager, dialogFragment.getTag());
    }

    public void onBackPressed() {
        fragmentView.onBackPressed();
    }

    @Override
    public void onStop() {
        compositeSubscription.clear();
    }

}
