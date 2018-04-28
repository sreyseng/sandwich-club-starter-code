package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    private static final String name = "name";
    private static final String mainName = "mainName";
    private static final String alsoKnownAs = "alsoKnownAs";
    private static final String placeOfOrigin = "placeOfOrigin";
    private static final String description = "description";
    private static final String image = "image";
    private static final String ingredients = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        if(json == null || json.isEmpty()) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            Sandwich sandwich = new Sandwich();
            sandwich.setDescription(jsonObject.getString(description));
            sandwich.setPlaceOfOrigin(jsonObject.getString(placeOfOrigin));
            sandwich.setImage(jsonObject.getString(image));

            JSONObject nameObject = jsonObject.getJSONObject(name);
            sandwich.setMainName(nameObject.getString(mainName));
            sandwich.setAlsoKnownAs(
                    getListFromJsonArray(nameObject.getJSONArray(alsoKnownAs))
            );

            sandwich.setIngredients(
                    getListFromJsonArray(jsonObject.getJSONArray(ingredients))
            );

            return sandwich;
        } catch (JSONException e) {
            Log.d(TAG, "Unable to parse JSONObject, e=" + e.getMessage());
            return null;
        }
    }

    private static List<String> getListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        if(jsonArray == null) {
            return list;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.get(i).toString());
        }

        return list;
    }
}
