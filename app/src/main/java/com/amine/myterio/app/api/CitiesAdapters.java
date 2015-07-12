package com.amine.myterio.app.api;

import retrofit.RestAdapter;

public class CitiesAdapters {
    private final RestAdapter restAdapter;

    public CitiesAdapters() {
        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://maps.googleapis.com")
                .build();
    }

    public CitiesApi.AutocompleteApi getCitiesForAutocomplete() {
        return restAdapter.create(CitiesApi.AutocompleteApi.class);
    }
}
