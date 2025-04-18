package models;

import models.enums.CountryType;

import java.util.ArrayList;

public class Faction {
    private String name;
    private final ArrayList<Country> countries = new ArrayList<>();

    public Faction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void removeCountry(Country country) {
        countries.remove(country);
    }

    public boolean hasCountry(Country country) {
        return countries.contains(country);
    }
}
