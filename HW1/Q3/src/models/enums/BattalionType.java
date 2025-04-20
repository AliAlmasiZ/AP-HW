package models.enums;

import models.App;

import java.util.HashMap;

public enum BattalionType {
    INFANTRY(
            0, 10000, 10000, 15000,
            15, 20, 15, 20, 15,
            50, 20, 30, 60, 40
    ),
    NAVY(
            30000, 50000, 10000, 5000,
            20, 15, 15, 15, 20,
            20, 30, 60, 40, 50
    ),
    PANZER(
            10000, 20000, 10000, 5000,
            25, 20, 20, 10, 25,
            30, 60, 40, 50, 20
    ),
    AIRFORCE(
            50000, 35000, 10000, 1000,
            10, 25, 15, 20, 15,
            40, 40, 50, 20, 30
    );

    private final HashMap<CountryType, Integer> powerHashmap = new HashMap<>(5);
    private final HashMap<CountryType, Integer> capturePercent = new HashMap<>(5);
    int fuelCost;
    int steelCost;
    int sulfurCost;
    int manPowerCost;

    BattalionType(int fuelCost, int steelCost, int sulfurCost, int manPowerCost,
            int germanReichPower, int sovietUnionPower, int unitedStatesPower, int unitedKingdomPower, int japanPower,
            int germanReichCapture, int sovietUnionCapture, int unitedStatesCapture, int unitedKingdomCapture, int japanCapture

    ) {
        this.fuelCost = fuelCost;
        this.steelCost = steelCost;
        this.sulfurCost = sulfurCost;
        this.manPowerCost = manPowerCost;



        powerHashmap.put(CountryType.GERMAN_REICH, germanReichPower);
        powerHashmap.put(CountryType.SOVIET_UNION, sovietUnionPower);
        powerHashmap.put(CountryType.UNITED_STATES, unitedStatesPower);
        powerHashmap.put(CountryType.UNITED_KINGDOM, unitedKingdomPower);
        powerHashmap.put(CountryType.JAPAN, japanPower);

        capturePercent.put(CountryType.GERMAN_REICH, germanReichCapture);
        capturePercent.put(CountryType.SOVIET_UNION, sovietUnionCapture);
        capturePercent.put(CountryType.UNITED_STATES, unitedStatesCapture);
        capturePercent.put(CountryType.UNITED_KINGDOM, unitedKingdomCapture);
        capturePercent.put(CountryType.JAPAN, japanCapture);
    }

    public int getPower(CountryType country) {
        return powerHashmap.get(country);
    }

    public int getFuelCost() {
        boolean isDouble = App.getActiveUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if(isDouble)
            return fuelCost * 2;
        return fuelCost;
    }

    public int getSteelCost() {
        boolean isDouble = App.getActiveUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if(isDouble)
            return steelCost * 2;
        return steelCost;
    }

    public int getSulfurCost() {
        boolean isDouble = App.getActiveUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if(isDouble)
            return sulfurCost * 2;
        return sulfurCost;
    }

    public int getManPowerCost() {
        boolean isDouble = App.getActiveUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
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

    public int getCaptureRatio(CountryType countryType) {
        return capturePercent.get(countryType);
    }
}
