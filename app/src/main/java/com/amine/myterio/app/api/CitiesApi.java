package com.amine.myterio.app.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public class CitiesApi {
    public interface AutocompleteApi {
        @GET("/maps/api/place/autocomplete/json")
        void autocompletePlace(@Query("input") String location, @Query("key") String apiKey, Callback<Predictions> cb);
    }
}
