package com.example.myrecipe.Listeners;

import com.example.myrecipe.Models.RecipeResponse;

public interface RecipeResponseListener {
    void didFetch(RecipeResponse response, String message);
    void didError(String message);
}

