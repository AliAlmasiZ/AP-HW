package models.enums;

import models.Tile;

import java.util.*;
//
//public enum CountryType {
//    GERMAN_REICH("German Reich", Leader.HITLER, 60000000, 100000, 200000, 300000),
//    UNITED_STATES("United States", Leader.ROOSEVELT,  120000000, 200000, 100000, 200000),
//    SOVIET_UNION("Soviet Union", Leader.STALIN,  160000000, 300000, 50000, 100000),
//    JAPAN("Japan", Leader.HIROHITO,  70000000, 50000, 50000, 50000),
//    UNITED_KINGDOM("United Kingdom", Leader.CHURCHILL, 30000000, 0, 1, 10);
//
//    private final String name;
//
//    private final Leader initialLeader;
//    private final int initialManPower;
//    private final int initialFuel;
//    private final int initialSteel;
//    private final int initialSulfur;
//    private final ArrayList<Tile> tiles;


//    CountryType(String name, Leader leader, int manPower, int fuel, int sulfur, int steel) {
//        this.name = name;
//        this.initialLeader = leader;
//        this.initialManPower = manPower;
//        this.initialFuel = fuel;
//        this.initialSteel = steel;
//        this.initialSulfur = sulfur;
//        tiles = new ArrayList<>();
//    }


public enum CountryType {
    GERMAN_REICH("German Reich", "HITLER",    60_000_000, 100_000, 200_000, 300_000),
    UNITED_STATES("United States", "ROOSEVELT", 120_000_000, 200_000, 100_000, 200_000),
    SOVIET_UNION("Soviet Union", "STALIN",   160_000_000, 300_000,  50_000, 100_000),
    JAPAN("Japan", "HIROHITO",                70_000_000,  50_000,  50_000,  50_000),
    UNITED_KINGDOM("United Kingdom", "CHURCHILL", 30_000_000,      0,      1,     10);

    private final String name;
    private final String   initialLeaderName;
    private Leader         initialLeader;       // ← will be set in static block
    private final int      initialManPower,
            initialFuel,
            initialSteel,
            initialSulfur;
        private final ArrayList<Tile> tiles;

    CountryType(String name, String leaderName,
                int manPower, int fuel, int sulfur, int steel) {
        this.name = name;
        this.initialLeaderName  = leaderName;
        this.initialManPower    = manPower;
        this.initialFuel        = fuel;
        this.initialSteel       = steel;
        this.initialSulfur      = sulfur;

        this.tiles = new ArrayList<>();
    }
    static {
        for (CountryType ct : values()) {
            ct.initialLeader = Leader.valueOf(ct.initialLeaderName);
        }
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

    public ArrayList<Leader> getCountryLeaders() {
        ArrayList<Leader> res = new ArrayList<>();
        for (Leader leader : Leader.values()) {
            if(leader.getCountry().equals(this))
                res.add(leader);
        }
        return res;
    }




}
