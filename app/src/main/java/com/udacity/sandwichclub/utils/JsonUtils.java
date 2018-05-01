package com.udacity.sandwichclub.utils;
import android.media.Image;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String jsonStr) {
    Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichObject = new JSONObject(jsonStr);
            JSONObject nameJSON = sandwichObject.optJSONObject("name");

        //parsing the strings
            String mainName = nameJSON.optString("mainName");
            sandwich.setMainName(mainName);
            String desc = sandwichObject.optString("description");
            sandwich.setDescription(desc);
            String origin = sandwichObject.optString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(origin);
            String image = sandwichObject.optString("image");
            sandwich.setImage(image);

        //parsing the arrays
            JSONArray ingredientsListArray = sandwichObject.getJSONArray("ingredients");

            List<String> ingredientsList = new ArrayList<String>();

                for (int i = 0; i < ingredientsListArray.length(); i++) {
                    String ingredient = ingredientsListArray.getString(i);
                    ingredientsList.add(ingredient);
                    Log.i("ingredientsListArray","Ingredient: " + ingredient);
                }
                sandwich.setIngredients(ingredientsList);


            JSONArray akaListArray = nameJSON.getJSONArray("alsoKnownAs");

            List<String> alsoKnownAs = new ArrayList<String>();
                for (int i = 0; i < akaListArray.length(); i++) {
                    String aka = akaListArray.getString(i);
                    alsoKnownAs.add(aka);
                    Log.i("akaListArray","alsoKnownAs: " + aka);
                }
                sandwich.setAlsoKnownAs(alsoKnownAs);

        } catch (Exception jsone) {
            Log.e("JsonUtils: ","Exception while parsing");
            jsone.printStackTrace();
            return null;
        }

        return sandwich;
    }
}

