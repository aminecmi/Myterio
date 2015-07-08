package com.amine.myterio.app.api;

import java.util.ArrayList;
import java.util.List;

public class Predictions {
    public List<PredictionCities> predictions;

    public List<PredictionCities> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionCities> predictions) {
        this.predictions = predictions;
    }

    public ArrayList<String> getCities() {
        ArrayList<String> cities = new ArrayList<String>();
        for (PredictionCities city : predictions) {
            cities.add(city.toCity().getName());
        }
        return cities;
    }
}
