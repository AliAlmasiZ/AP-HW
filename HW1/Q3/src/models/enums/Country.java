package models.enums;

import models.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

public enum Country {
    GERMAN_REICH("German Reich", Leader.HITLER, 100, 60000000, 100000, 200000, 300000),
    UNITED_STATES("United States", Leader.ROOSEVELT, 100, 120000000, 200000, 100000, 200000),
    SOVIET_UNION("Soviet Union", Leader.STALIN, 100, 160000000, 300000, 50000, 100000),
    JAPAN("Japan", Leader.HIROHITO, 100, 70000000, 50000, 50000, 50000),
    UNITED_KINGDOM("United Kingdom", Leader.CHURCHILL, 100, 30000000, 0, 1, 10);

    private final String name;
    private Leader leader;
    private int stability;
    private int manPower;
    private int fuel;
    private int steel;
    private int sulfur;

    private final Leader initialLeader;
    private final int initialStability;
    private final int initialManPower;
    private final int initialFuel;
    private final int initialSteel;
    private final int initialSulfur;

    private ArrayList<Country> puppets;
    private ArrayList<String> factions;
    private ArrayList<Tile> tiles;

    Country(String name, Leader leader, int stability, int manPower, int fuel, int sulfur, int steel) {
        this.name = name;
        this.leader = this.initialLeader = leader;
        this.stability = this.initialStability = stability;
        this.manPower = this.initialManPower = manPower;
        this.fuel = this.initialFuel = fuel;
        this.steel = this.initialSteel = steel;
        this.sulfur = this.initialSulfur = sulfur;
        this.factions = new ArrayList<>();
        this.puppets = new ArrayList<>();
    }

    public void reset() {
        this.leader = this.initialLeader;
        this.stability = this.initialStability;
        this.manPower = this.initialManPower ;
        this.fuel = this.initialFuel;
        this.steel = this.initialSteel;
        this.sulfur = this.initialSulfur;
        this.factions = new ArrayList<>();
        this.puppets = new ArrayList<>();

    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Country stringToCountry (String country) {
        for (Country value : Country.values()) {
            if(value.name.equals(country)) return value;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
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

    public ArrayList<Country> getPuppets() {
        return puppets;
    }

    public ArrayList<String> getFactions() {
        return factions;
    }
}
