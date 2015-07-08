package com.amine.myterio.app.model;

import java.util.ArrayList;
import java.util.List;

public class Countries {
    public List<Country> countries;

    public Countries() {
        countries = new ArrayList<Country>();
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
