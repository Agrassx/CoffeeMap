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

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetCafeInfo extends BottomSheetDialogFragment {

    @BindView(R2.id.textViewCafeName) TextView textViewCafeName;
    @BindView(R2.id.textViewCafeAddress) TextView textViewCafeAddress;
    @BindView(R2.id.textViewCafeTimeWork) TextView textViewCafeTimeWork;
    private Cafe cafe;

    public static BottomSheetCafeInfo newInstance(Cafe cafe) {
        BottomSheetCafeInfo fragment = new BottomSheetCafeInfo();
        fragment.setCafe(cafe);
        return fragment;
    }

    private void setCafe(Cafe cafe) {
        this.cafe = cafe;
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
        textViewCafeTimeWork.setText(new OpenHourParser().getOpenHours(cafe.getOpeningHours(), 3));
        return view;
    }

}
