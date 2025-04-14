package models.enums;

public enum Terrain {
    MOUNTAIN(50, 100, 1000, 0),
    PLAIN(120, 125, 100, 100),
    FOREST(90, 100, 500, 0),
    URBAN(110, 100, 10, 20),
    DESERT(100, 140, 100, 100);

    private int attack;
    private int airAttack;
    private int factoryCost;
    private int fuelProduction;

    Terrain(int attack, int airAttack, int factoryCost, int fuelProduction) {
        this.attack = attack;
        this.airAttack = airAttack;
        this.factoryCost = factoryCost;
        this.fuelProduction = fuelProduction;
    }

    public int getAttack() {
        return attack;
    }

    public int getAirAttack() {
        return airAttack;
    }

    public int getFactoryCost() {
        return factoryCost;
    }

    public int getFuelProduction() {
        return fuelProduction;
    }

    public static Terrain stringToTerrain(String name) {
        for (Terrain terrain : Terrain.values()) {
            if(terrain.name().equalsIgnoreCase(name))
                return terrain;
        }
        return null;
    }
}
