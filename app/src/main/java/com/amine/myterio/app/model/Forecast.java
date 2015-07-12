package com.amine.myterio.app.model;

import java.util.List;

public class Forecast {
    private City city;
    private List<ForecastWeather> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<ForecastWeather> getList() {
        return list;
    }

    public void setList(List<ForecastWeather> list) {
        this.list = list;
    }
}
