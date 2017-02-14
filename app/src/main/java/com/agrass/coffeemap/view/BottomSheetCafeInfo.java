package com.agrass.coffeemap.view;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agrass.coffeemap.R;
import com.agrass.coffeemap.R2;
import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.model.parsers.OpenHourParser;
import com.agrass.coffeemap.model.util.MapUtil;
import com.agrass.coffeemap.view.map.ClearSelectionView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetCafeInfo extends BottomSheetDialogFragment implements CafeInfoView {

    @BindView(R2.id.textViewCafeName) TextView textViewCafeName;
    @BindView(R2.id.textViewCafeAddress) TextView textViewCafeAddress;
    @BindView(R2.id.textViewCafeTimeWork) TextView textViewCafeTimeWork;
    @BindView(R2.id.textViewCafeScheduleWork) TextView textViewCafeScheduleWork;

    private ClearSelectionView clearSelectionView;
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
        textViewCafeName.setText(cafe.getName());
        try {
            textViewCafeAddress.setText(MapUtil.getAddress(getActivity(), cafe.getPosition()));
        } catch (IOException e) {
            textViewCafeAddress.setText("Error");
            e.printStackTrace();
        }
//        TODO: Dynamic day of week (fix magic number)
        textViewCafeTimeWork.setText(new OpenHourParser().getOpenHours(
                cafe.getOpeningHours(),
                3
        ));
        textViewCafeScheduleWork.setText(cafe.getOpeningHours());
        return view;
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
}
