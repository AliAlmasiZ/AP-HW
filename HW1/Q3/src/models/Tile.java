package models;

import models.enums.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tile {
    private int id;
    private boolean isTerrainChanged;
    private CountryType countryType;
    private List<Integer> landNeighbors;
    private List<Integer> seaNeighbors;
    private Terrain terrain;
    private Weather weather;
    private HashMap<BattalionType, ArrayList<Battalion>> battalions;
    private HashMap<FactoryType, ArrayList<Factory>> factories;


    public Tile(int id, String countryType, List<Integer> neighborsIds, List<Integer> seaNeighbors) {
        this.id = id;
        this.countryType = CountryType.stringToCountry(countryType);
        this.landNeighbors = neighborsIds;
        this.seaNeighbors = seaNeighbors;
        this.isTerrainChanged = false;
        this.terrain = Terrain.PLAIN;
        this.weather = Weather.SUNNY;
        this.battalions = new HashMap<>();
        for (BattalionType value : BattalionType.values()) {
            battalions.put(value, new ArrayList<>());
        }
        factories = new HashMap<>();
        for (FactoryType factoryType : FactoryType.values()) {
            factories.put(factoryType, new ArrayList<>());
        }
    }

    public int getId() {
        return id;
    }

    public CountryType getCountryType() {
        return countryType;
    }

    public void setCountryType(CountryType countryType) {
        this.countryType = countryType;
    }

    public List<Integer> getLandNeighbors() {
        return landNeighbors;
    }

    public List<Integer> getSeaNeighbors() {
        return seaNeighbors;
    }

    public boolean isTerrainChanged() {
        return isTerrainChanged;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Weather getWeather() {
        return weather;
    }

    public Battalion getBattalionByName(String name) {
        for (ArrayList<Battalion> arrayList : battalions.values()) {
            for (Battalion battalion : arrayList) {
                if(battalion.getName().equalsIgnoreCase(name)) return battalion;
            }
        }
        return null;
    }

    public Factory getFactoryByName(String name) {
        for (ArrayList<Factory> arrayList : factories.values()) {
            for (Factory factory : arrayList) {
                if(factory.getName().equalsIgnoreCase(name)) return factory;
            }
        }
        return null;
    }

    public int battalionTypeCount(BattalionType type) {

        return battalions.get(type).size();
    }

    public int factoryTypeCount(FactoryType type) {
        return factories.get(type).size();
    }

    public void addBattalion(Battalion battalion) {
        battalions.get(battalion.getType()).add(battalion);
    }

    public void removeBattalion(Battalion battalion) {
        battalions.get(battalion.getType()).remove(battalion);
    }

    public void addFactory(Factory factory) {
        factories.get(factory.getFactoryType()).add(factory);
    }

    public void removeFactory(Factory factory) {
        factories.get(factory.getFactoryType()).remove(factory);
    }

    public ArrayList<Battalion> getBattalionsByType(BattalionType type) {
        return battalions.get(type);
    }

    public ArrayList<Factory> getFactoriesByType(FactoryType type) {
        return factories.get(type);
    }

    public boolean isLandNeighbor(Tile tile) {
        return landNeighbors.contains(tile.id);
    }

    public boolean isSeaNeighbor(Tile tile) {
        return seaNeighbors.contains(tile.id);
    }

}
