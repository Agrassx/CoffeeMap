package com.agrass.coffeemap.view;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.model.api.response.CafeInfoResponse;
import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.model.parsers.OpenHourParser;
import com.agrass.coffeemap.model.util.MapUtil;
import com.agrass.coffeemap.presenter.CafeInfoFragmentPresenter;
import com.agrass.coffeemap.view.map.ClearSelectionView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetCafeInfo extends BottomSheetDialogFragment implements CafeInfoView {

    @BindView(R2.id.textViewCafeName) TextView textViewCafeName;
    @BindView(R2.id.textViewCafeAddress) TextView textViewCafeAddress;
    @BindView(R2.id.textViewCafeTimeWork) TextView textViewCafeTimeWork;
    @BindView(R2.id.textViewCafeScheduleWork) TextView textViewCafeScheduleWork;
    @BindView(R2.id.textViewBeFirst) TextView textViewBeFirst;
    @BindView(R2.id.ratingBarCafe) RatingBar ratingBarCafe;
    @BindView(R2.id.buttonEstimate) Button buttonEstimate;


    private ClearSelectionView clearSelectionView;
    private CafeInfoFragmentPresenter presenter;
    private Cafe cafe;

    public static BottomSheetCafeInfo newInstance(Cafe cafe, ClearSelectionView clearSelectionView) {
        BottomSheetCafeInfo fragment = new BottomSheetCafeInfo();
        fragment.setCafe(cafe);
        fragment.setClearSelectionView(clearSelectionView);
        return fragment;
    }

    private void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    private void setClearSelectionView(ClearSelectionView clearSelectionView) {
        this.clearSelectionView = clearSelectionView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_cafe_info, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        presenter = new CafeInfoFragmentPresenter(this);
        buttonEstimate.setOnClickListener(v -> presenter.showRatingDialog());

        textViewCafeName.setText(cafe.getName());
        try {
            textViewCafeAddress.setText(MapUtil.getAddress(getActivity(), cafe.getPosition()));
        } catch (IOException e) {
            textViewCafeAddress.setText("Error");
            e.printStackTrace();
        }
//        TODO: Dynamic day of week (fix magic number)
        textViewCafeTimeWork.setText(getOpenTill());
        textViewCafeScheduleWork.setText(cafe.getOpeningHours());
        presenter.getCafeInfo(cafe.getId());
        return view;
    }

    private String getOpenTill() {
        return new OpenHourParser().getOpenHours(
                cafe.getOpeningHours(),
                3
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearSelectionView.clearSelection(cafe);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void cafeInfoFound(CafeInfoResponse cafeInfoResponse) {
        textViewBeFirst.setVisibility(View.GONE);
        ratingBarCafe.setRating(cafeInfoResponse.getRating());
    }

    @Override
    public void cafeInfoNotFound() {
        textViewBeFirst.setVisibility(View.VISIBLE);
        ratingBarCafe.setRating(0);
    }
}
