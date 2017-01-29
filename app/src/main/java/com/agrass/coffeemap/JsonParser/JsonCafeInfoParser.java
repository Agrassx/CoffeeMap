package com.agrass.coffeemap.JsonParser;

import com.agrass.coffeemap.model.cafe.CafeInfo;

import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class JsonCafeInfoParser {

    public JsonCafeInfoParser() {

    }

    public CafeInfo getCafeInfo(JSONObject jsonCafeInfo) throws JSONException {
        if (jsonCafeInfo.getBoolean("found")) {
            return new CafeInfo(
                    jsonCafeInfo.getString("_id"),
                    (float) jsonCafeInfo.getJSONObject("_source").getDouble("last_rating")
            );
        } else {
            return new CafeInfo(jsonCafeInfo.getString("_id"));
        }

    }

}
