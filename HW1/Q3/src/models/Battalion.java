package models;

import models.enums.BattalionType;
import models.enums.CountryType;
import models.enums.Weather;

public class Battalion {
    private Tile position;
    private BattalionType type;
    private String name;
    private CountryType countryType;
    private int level;
    private final int captureRatio;
    private int power;


    public Battalion(Tile position, BattalionType type, String name, CountryType countryType) {
        this.position = position;
        this.type = type;
        this.name = name;
        this.countryType = countryType;
        this.level = 0;
        this.captureRatio = type.getCaptureRatio(countryType);
        this.power = type.getPower(countryType);
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile destination) {
        this.position = destination;
    }

    public BattalionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        Weather weather = position.getWeather();
        if(type.equals(BattalionType.NAVY))
            return power;
        if(type.equals(BattalionType.AIRFORCE)) {
            int res = power * position.getTerrain().getAirAttack() / 100;
            return  res * weather.getAirAttack() / 100;
        }
        int res = power * position.getTerrain().getAttack() / 100;

        return  res * weather.getAttack() / 100;
    }

    public int getLevel() {
        return level;
    }

    public int getCaptureRatio() {
        return captureRatio;
    }

    public double getUpgradeCostWeight() {
        double weight = 0.5;
        for(int i = 0; i < level; i++)
            weight *= 2;
        return weight;
    }

    public void upgrade() {
        level++;
        switch (level) {
            case 1 -> {
                power += 5;
            }
            case 2 -> {
                power += 7;
            }
            case 3 -> {
                power += 10;
            }
        }
    }

    public int getRawPower() {
        return power;
    }

    public void addPower(int capturePower) {
        power += capturePower;
    }
}
