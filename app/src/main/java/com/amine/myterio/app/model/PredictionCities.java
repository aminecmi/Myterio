package com.amine.myterio.app.model;

public class PredictionCities {
    private String description;

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
