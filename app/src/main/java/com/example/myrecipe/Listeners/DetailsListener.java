package com.example.myrecipe.Listeners;

import com.example.myrecipe.Models.DetailsResponce;

public interface DetailsListener {
    void didFetch(DetailsResponce response, String message);
    void didError(String message);
}
