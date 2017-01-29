package com.agrass.coffeemap.model.api.response;

import com.agrass.coffeemap.model.cafe.Cafe;
import com.agrass.coffeemap.model.cafe.CafeItem;

import java.util.List;

public class PointsResponse {

    private String status;
    private List<Cafe> points;

    public String getStatus() {
        return status;
    }

    public List<Cafe> getList() {
        return points;
    }
}
