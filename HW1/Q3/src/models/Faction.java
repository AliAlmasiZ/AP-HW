package models;

import models.enums.CountryType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Faction {
    private String name;
    private final Set<Country> countries = new HashSet<>();

    public Faction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Country> getCountries() {
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
