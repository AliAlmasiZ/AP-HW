package models;

import models.enums.CountryType;
import models.enums.Ideology;
import models.enums.Leader;
import models.enums.TileData;

import java.util.*;

public class Country {
    private final CountryType countryType;
    private Leader leader;
    private int stability;
    private int manPower;
    private int fuel;
    private int steel;
    private int sulfur;
    private ArrayList<Tile> tiles;
    private ArrayList<Country> puppets;
    private LinkedHashMap<String, Faction> factions;

    public Country(CountryType countryType) {
        this.countryType = countryType;
        this.leader = countryType.getInitialLeader();
        this.stability = countryType.getInitialStability();
        this.manPower = countryType.getInitialManPower();
        this.fuel = countryType.getInitialFuel();
        this.steel = countryType.getInitialSteel();
        this.sulfur = countryType.getInitialSulfur();
        this.puppets = new ArrayList<>();
        this.factions = new LinkedHashMap<>();
        this.tiles = countryType.getTiles();
        tiles.forEach(e -> e.setCountryType(this.countryType));
    }

    public void addFaction(Faction faction) {
        factions.put(faction.getName(), faction);
    }

    public void removeFaction(String name) {
        factions.remove(name);
    }

    public CountryType getCountryType() {
        return countryType;
    }

    public Leader getLeader() {
        return leader;
    }

    public int getStability() {
        return stability;
    }

    public int getManPower() {
        return manPower;
    }

    public int getFuel() {
        return fuel;
    }

    public int getSteel() {
        return steel;
    }

    public int getSulfur() {
        return sulfur;
    }

    public boolean canMakePuppet(CountryType countryType) {
        Country puppet = App.getActiveGame().getCountryByType(countryType);
        Country country = App.getActiveUser().getPlayingCountry();
        for (TileData tileData : TileData.values()) {
            if(CountryType.stringToCountry(tileData.getCountry()).equals(country.countryType))
                if(puppet.containsTileID(tileData.getId()))
                    return false;
        }
        return true;
    }

    public boolean containsTileID(int id) {
        for (Tile tile : tiles) {
            if(tile.getId() == id)
                return true;
        }
        return false;
    }

    public ArrayList<Country> getPuppets() {
        return puppets;
    }

    public void addPuppet(Country puppet) {
        puppets.add(puppet);
    }

    public List<String> getFactionsNames() {
        return factions.keySet().stream().toList();
    }

    public boolean isInFaction(Country country) {
        for (Faction faction : factions.values()) {
            if(faction.hasCountry(country))
                return true;
        }
        return false;
    }

    public boolean canAccessTile(Tile tile) {
        Country country = App.getActiveGame().getCountryByType(tile.getCountryType());
        return isInFaction(country) || puppets.contains(country);
    }

    public boolean canAttackWithTile(Tile tile) {
        Country country = App.getActiveGame().getCountryByType(tile.getCountryType());
        return puppets.contains(country) || country.equals(this);
    }

    public void handleCosts(int steel, int manPower, int sulfur, int fuel) {
        this.steel -= steel;
        this.fuel -= fuel;
        this.manPower -= manPower;
        this.sulfur -= sulfur;
    }

    public Set<Country> alies() {
        Set<Country> res = new HashSet<>();
        for (Faction faction : factions.values()) {
            res.addAll(faction.getCountries());
        }
        res.remove(this);
        return res;
    }

    public Set<Country> alliesByIdeology(Ideology ideology) {
        Set<Country> res = new HashSet<>();
        for (Country aly : alies()) {
            if(aly.leader.getIdeology().equals(ideology))
                res.add(aly);
        }
        return res;

    }

    public boolean isPuppeteer(CountryType countryType) {
        Country country = App.getActiveGame().getCountryByType(countryType);
        return country.puppets.contains(this);
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
        tile.setCountryType(this.countryType);
    }

    public void removeTile(Tile tile) {
        tiles.remove(tile);
    }

    public void multiplyStability(double weight) {
        this.stability = (int) (this.stability * weight);
        if(this.stability > 100)
            this.stability = 100;
    }

}
