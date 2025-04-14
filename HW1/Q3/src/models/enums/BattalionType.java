package models.enums;

import java.util.HashMap;

public enum BattalionType {
    INFANTRY(
            0, 10000, 10000, 15000,
            15, 20, 15, 20, 15
    ),
    NAVY(
            30000, 50000, 10000, 5000,
            20, 15, 15, 15, 20
    ),
    PANZER(
            10000, 20000, 10000, 5000,
            25, 20, 20, 10, 25
    ),
    AIRFORCE(
            50000, 35000, 10000, 1000,
            10, 25, 15, 20, 15
    );

    private final HashMap<Country, Integer> powerHashmap = new HashMap<>(5);

    int fuelCost;
    int steelCost;
    int sulfurCost;
    int manPowerCost;

    BattalionType(int fuelCost, int steelCost, int sulfurCost, int manPowerCost,
            int germanReichPower, int sovietUnionPower, int unitedStatesPower, int unitedKingdomPower, int japanPower
    ) {
        this.fuelCost = fuelCost;
        this.steelCost = steelCost;
        this.sulfurCost = sulfurCost;
        this.manPowerCost = manPowerCost;



        powerHashmap.put(Country.GERMAN_REICH, germanReichPower);
        powerHashmap.put(Country.SOVIET_UNION, sovietUnionPower);
        powerHashmap.put(Country.UNITED_STATES, unitedStatesPower);
        powerHashmap.put(Country.UNITED_KINGDOM, unitedKingdomPower);
        powerHashmap.put(Country.JAPAN, japanPower);
    }

    public int getPower(Country country) {
        return powerHashmap.get(country);
    }

    public int getFuelCost(boolean isDouble) {
        if(isDouble)
            return fuelCost * 2;
        return fuelCost;
    }

    public int getSteelCost(boolean isDouble) {
        if(isDouble)
            return steelCost * 2;
        return steelCost;
    }

    public int getSulfurCost(boolean isDouble) {
        if(isDouble)
            return sulfurCost * 2;
        return sulfurCost;
    }

    public int getManPowerCost(boolean isDouble) {
        if(isDouble)
            return manPowerCost * 2;
        return manPowerCost;
    }

    public static BattalionType stringToBattalionType(String string) {
        for (BattalionType battalionType : BattalionType.values()) {
            if(battalionType.name().equalsIgnoreCase(string))
                return battalionType;
        }
        return null;
    }
}
