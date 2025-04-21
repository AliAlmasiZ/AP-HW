package models.enums;

import models.App;

import java.util.HashMap;

public enum BattalionType {
    INFANTRY(
            0, 10000, 10000, 15000,
            15, 20, 5, 10, 10,
            50, 20, 30, 60, 40,
            5
    ),
    NAVY(
            30000, 50000, 10000, 5000,
            5, 10, 15, 20, 10,
            20, 30, 60, 40, 50,
            10
    ),
    PANZER(
            10000, 20000, 10000, 5000,
            20, 15, 10, 5, 10,
            30, 60, 40, 50, 20,
            7
    ),
    AIRFORCE(
            50000, 35000, 10000, 1000,
            10, 5, 20, 15, 10,
            40, 40, 50, 20, 30,
            15
    );

    private final HashMap<CountryType, Integer> powerHashmap = new HashMap<>(5);
    private final HashMap<CountryType, Integer> capturePercent = new HashMap<>(5);
    private final int fuelCost;
    private final int steelCost;
    private final int sulfurCost;
    private final int manPowerCost;
    private final int pointWeight;

    BattalionType(int fuelCost, int steelCost, int sulfurCost, int manPowerCost,
            int germanReichPower, int sovietUnionPower, int unitedStatesPower, int unitedKingdomPower, int japanPower,
            int germanReichCapture, int sovietUnionCapture, int unitedStatesCapture, int unitedKingdomCapture, int japanCapture,
            int pointWeight
    ) {
        this.fuelCost = fuelCost;
        this.steelCost = steelCost;
        this.sulfurCost = sulfurCost;
        this.manPowerCost = manPowerCost;
        this.pointWeight = pointWeight;



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
        boolean isDouble = App.getActiveGame().getPlayingUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if(isDouble)
            return fuelCost * 2;
        return fuelCost;
    }

    public int getSteelCost() {
        boolean isDouble = App.getActiveGame().getPlayingUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if(isDouble)
            return steelCost * 2;
        return steelCost;
    }

    public int getSulfurCost() {
        boolean isDouble = App.getActiveGame().getPlayingUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if(isDouble)
            return sulfurCost * 2;
        return sulfurCost;
    }

    public int getManPowerCost() {
        boolean isDouble = App.getActiveGame().getPlayingUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if(isDouble)
            return manPowerCost * 2;
        return manPowerCost;
    }

    public static BattalionType stringToBattalionType(String string) {
        for (BattalionType battalionType : BattalionType.values()) {
            if(battalionType.name().toLowerCase().equals(string))
                return battalionType;
        }
        return null;
    }

    public int getCaptureRatio(CountryType countryType) {
        return capturePercent.get(countryType);
    }

    public int getPointWeight() {
        return this.pointWeight;
    }
}
