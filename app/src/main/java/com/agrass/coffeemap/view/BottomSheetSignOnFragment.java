package com.agrass.coffeemap.view;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.presenter.SignInFragmentPresenter;
import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetSignOnFragment extends BottomSheetDialogFragment implements SignInView {

    @BindView(R2.id.sign_in_button) SignInButton signInButton;
    private SignInFragmentPresenter presenter;
    private MainActivityView activityView;

    public static BottomSheetSignOnFragment newInstance(MainActivityView activityView) {
        BottomSheetSignOnFragment fragment = new BottomSheetSignOnFragment();
        fragment.setActivityView(activityView);
        return fragment;
    }

    private void setActivityView(MainActivityView activityView) {
        this.activityView = activityView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_sign_in, container, false);
        presenter = new SignInFragmentPresenter(this, activityView);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        signInButton.setOnClickListener(v -> presenter.onSignInButtonClick(getActivity()));
        return view;
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hide() {
        this.dismiss();
    }
}
