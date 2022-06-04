package com.example.myrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrecipe.Adapters.IngredientsAdapter;
import com.example.myrecipe.Listeners.DetailsListener;
import com.example.myrecipe.Models.DetailsResponce;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    int id;
    int maxLength = 200;
    String full_instructions;
    TextView tv_food_title, tv_food_source, tv_food_instructions;
    ImageView im_food_detail;
    RecyclerView recycler_food_ing;
    RequestManager manager;
    IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findViews();
        id = Integer.parseInt(getIntent().getStringExtra("id"));

        manager = new RequestManager(this);
        manager.getRecipeDetails(details_listener, id);

    }

    private void findViews() {
        tv_food_title = findViewById(R.id.tv_food_title);
        tv_food_source = findViewById(R.id.tv_food_sourse);
        tv_food_instructions = findViewById(R.id.tv_food_instructions);
        im_food_detail = findViewById(R.id.im_food_detail);
        recycler_food_ing = findViewById(R.id.recycler_food_ing);

    }

    private final DetailsListener details_listener = new DetailsListener() {

        @Override
        public void didFetch(DetailsResponce response, String message) {
            tv_food_title.setText(response.title);
            tv_food_source.setText(response.sourceName);

            full_instructions = response.instructions;
            String showContent = "\nShow all...";
            if (full_instructions.length() > maxLength) {
                full_instructions = full_instructions
                        .substring(0, maxLength - showContent.length()) + showContent;
                tv_food_instructions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((TextView) view).setText(response.instructions);
                    }
                });
            }
            tv_food_instructions.setText(full_instructions);

            Picasso.get().load(response.image).into(im_food_detail);

            recycler_food_ing.setHasFixedSize(true);
            recycler_food_ing.setLayoutManager(new LinearLayoutManager(DetailsActivity.this,
                    LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(DetailsActivity.this, response.extendedIngredients);
            recycler_food_ing.setAdapter(ingredientsAdapter);


        }

        @Override
        public void didError(String message) {
            Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };
}