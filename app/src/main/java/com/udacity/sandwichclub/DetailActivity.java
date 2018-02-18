package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        /* Set title to the sandwich name */
        String title = sandwich.getMainName();
        setTitle( sandwich.getMainName() );

        populateUI();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        /* Populate the sandwich image */
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        /* Populate the text view 'Also known as:' */
        TextView alsoKnownAsTv = findViewById( R.id.also_known_tv );
        String alsoKnownAs = sandwich.getAlsoKnownAs().toString();
        alsoKnownAsTv.setText( alsoKnownAs.substring(1, alsoKnownAs.length() - 1 ) );

        /* Populate the text view 'Ingredients:' */
        TextView ingredientsTv = findViewById( R.id.ingredients_tv );
        String ingredients = sandwich.getIngredients().toString();
        ingredientsTv.setText( ingredients.substring(1, ingredients.length() - 1 ) );

        /* Populate the text view 'Place Of Origin:' */
        TextView placeOfOriginTv = findViewById( R.id.origin_tv );

        placeOfOriginTv.setText( sandwich.getPlaceOfOrigin() );

        /* Populate the text view 'Description:' */
        TextView descriptionTv = findViewById( R.id.description_tv );

        descriptionTv.setText( sandwich.getDescription() );
    }

}
