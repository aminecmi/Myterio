package com.amine.myterio.app.api;

import com.amine.myterio.app.model.City;

public class PredictionCities {
    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City toCity() {
        return new City(this.description);
    }
}
