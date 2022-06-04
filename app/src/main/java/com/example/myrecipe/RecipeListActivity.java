package com.example.myrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myrecipe.Adapters.RecipeAdapter;
import com.example.myrecipe.Listeners.RecipeResponseListener;
import com.example.myrecipe.Listeners.RecipeClickListener;
import com.example.myrecipe.Models.RecipeResponse;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        dialog = new ProgressDialog(this);
        dialog.setTitle(R.string.loading_message);

        spinner = findViewById(R.id.spinner_tag);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_input_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(this);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(recipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final RecipeResponseListener recipeResponseListener = new RecipeResponseListener() {
        @Override
        public void didFetch(RecipeResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(RecipeListActivity.this, 1));
            recipeAdapter = new RecipeAdapter(RecipeListActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(recipeAdapter);
        }


        @Override
        public void didError(String message) {
            Toast.makeText(RecipeListActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(recipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeListActivity.this, DetailsActivity.class)
                    .putExtra("id", id));
        }
    };
}