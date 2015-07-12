package com.amine.myterio.app.model;

import java.util.ArrayList;
import java.util.List;

class Country {
    private String name;
    private List<City> cities;

    public Country() {
        cities = new ArrayList<City>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
