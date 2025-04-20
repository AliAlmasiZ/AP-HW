package models.enums;

import models.Faction;
import models.Tile;

import java.util.*;

public enum CountryType {
    GERMAN_REICH("German Reich", Leader.HITLER, 60000000, 100000, 200000, 300000),
    UNITED_STATES("United States", Leader.ROOSEVELT,  120000000, 200000, 100000, 200000),
    SOVIET_UNION("Soviet Union", Leader.STALIN,  160000000, 300000, 50000, 100000),
    JAPAN("Japan", Leader.HIROHITO,  70000000, 50000, 50000, 50000),
    UNITED_KINGDOM("United Kingdom", Leader.CHURCHILL, 30000000, 0, 1, 10);

    private final String name;

    private final Leader initialLeader;
    private final int initialManPower;
    private final int initialFuel;
    private final int initialSteel;
    private final int initialSulfur;
    private final ArrayList<Tile> tiles;

    CountryType(String name, Leader leader, int manPower, int fuel, int sulfur, int steel) {
        this.name = name;
        this.initialLeader = leader;
        this.initialManPower = manPower;
        this.initialFuel = fuel;
        this.initialSteel = steel;
        this.initialSulfur = sulfur;
        tiles = new ArrayList<>();
    }
    @Override
    public String toString() {
        return this.name;
    }

    public static CountryType stringToCountry (String country) {
        for (CountryType value : CountryType.values()) {
            if(value.name.equals(country)) return value;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Leader getInitialLeader() {
        return initialLeader;
    }

    public int getInitialStability() {
        return 100;
    }

    public int getInitialManPower() {
        return initialManPower;
    }

    public int getInitialFuel() {
        return initialFuel;
    }

    public int getInitialSteel() {
        return initialSteel;
    }

    public int getInitialSulfur() {
        return initialSulfur;
    }

    public ArrayList<Tile> getTiles() {

        return new ArrayList<>(tiles);
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }




}
