package com.amine.myterio.app.model;

import java.util.List;

public class City {

    public String name;
    public int id;
    public MainInfo main;
    public List<Weather> weather;
    public Wind wind;

    public City(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public City() {


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityIdentifier() {
        return id;
    }

    public void setCityIdentifier(int cityIdentifier) {
        this.id = cityIdentifier;
    }

    public MainInfo getMain() {
        return main;
    }

    public void setMain(MainInfo main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
