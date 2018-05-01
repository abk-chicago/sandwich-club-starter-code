package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.udacity.sandwichclub.R.id.also_known_tv;
import static com.udacity.sandwichclub.R.id.origin_tv;
import static com.udacity.sandwichclub.R.id.sandwiches_listview;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView placeOfOriginTv;
    TextView alsoKnownAsTv;
    TextView descriptionTv;
    TextView ingredientsTv;
    ImageView imageIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageIv = (ImageView) findViewById(R.id.image_iv);
        placeOfOriginTv = (TextView) findViewById(R.id.origin_tv);
        alsoKnownAsTv =  (TextView) findViewById(R.id.also_known_tv);
        descriptionTv =  (TextView) findViewById(R.id.description_tv);
        ingredientsTv =  (TextView) findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e("Error1","Error receiving Intent *******");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            Log.e("Error2","Error receiving Intent POSITION *******");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Log.e("Error3","Error receiving sandwich object *******");
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Log.e("Populating UI","Intent Extra: " + getIntent().getExtras());
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTv.setText("n/a");
            ingredientsTv.setText(sandwich.getIngredients().toString());
        } else {
            alsoKnownAsTv.setText(sandwich.getAlsoKnownAs().toString());
            ingredientsTv.setText(sandwich.getIngredients().toString());
        }
    }
}
