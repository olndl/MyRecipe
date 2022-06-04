package com.example.myrecipe;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myrecipe.Listeners.DetailsListener;
import com.example.myrecipe.Listeners.RecipeResponseListener;
import com.example.myrecipe.Models.DetailsResponce;
import com.example.myrecipe.Models.RecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RecipeResponseListener listener, List<String> tags) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RecipeResponse> call = callRandomRecipes.callRandomRecipe(
                context.getString(R.string.api_key), 5, tags);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeResponse> call, @NonNull Response<RecipeResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<RecipeResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());

            }
        });
    }

    public void getRecipeDetails(DetailsListener listener, int id) {
        CallRecipeDetail callRecipeDetail = retrofit.create(CallRecipeDetail.class);
        Call<DetailsResponce> call = callRecipeDetail.callDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<DetailsResponce>() {
            @Override
            public void onResponse(@NonNull Call<DetailsResponce> call, @NonNull Response<DetailsResponce> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<DetailsResponce> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RecipeResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") int number,
                @Query("tags") List<String> tags
        );

    }

    private interface CallRecipeDetail {
        @GET("recipes/{id}/information")
        Call<DetailsResponce> callDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
