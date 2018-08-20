package com.kdas.ddash.restaurant;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultParser {

    private static final String TAG = "ResultParser";

    List<Restaurant> parseResult(String serverResponse) {

        List<Restaurant> resultList = new ArrayList<>();

        try {
            JSONArray result = new JSONArray(serverResponse);
            for (int i = 0; i < result.length(); i++) {
                JSONObject item = result.getJSONObject(i);
                Restaurant restaurant = new Restaurant();
                restaurant.setId(item.get("id").toString());
                restaurant.setName(item.get("name").toString());
                restaurant.setDescription(item.get("description").toString());
                restaurant.setStatus(item.get("status").toString());
                restaurant.setCoverImageUrl(item.get("cover_img_url").toString());
                resultList.add(restaurant);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in parsing response from server", e);
            return null;
        }
        return resultList;
    }
}
