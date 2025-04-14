package models;

import models.enums.BattalionType;
import models.enums.Country;
import models.enums.Terrain;
import models.enums.Weather;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private int id;
    private boolean isTerrainChanged;
    private Country country;
    private List<Integer> landNeighbors;
    private List<Integer> seaNeighbors;
    private Terrain terrain;
    private Weather weather;
    private ArrayList<Battalion> battalions;


    public Tile(int id, String country, List<Integer> neighborsIds, List<Integer> seaNeighbors) {
        this.id = id;
        this.country = Country.stringToCountry(country);
        this.landNeighbors = neighborsIds;
        this.seaNeighbors = seaNeighbors;
        this.isTerrainChanged = false;
        this.terrain = Terrain.PLAIN;
        this.weather = Weather.SUNNY;
        this.battalions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Country getCountry() {
        return country;
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

    public Battalion getBattlionByName(String name) {
        for (Battalion battalion : battalions) {
            if(battalion.getName().equalsIgnoreCase(name)) return battalion;
        }
        return null;
    }

    public int typeCount(BattalionType type) {
        int res = 0;
        for (Battalion battalion : battalions) {
            if(battalion.getType().equals(type))
                res++;
        }
        return res;
    }

    public void addBattalion(Battalion battalion) {
        battalions.add(battalion);
    }
}
