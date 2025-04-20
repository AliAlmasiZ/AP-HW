package models.enums;

public enum Weather {
    SUNNY(100, 100),
    RAINY(80, 10),
    BLIZZARD(60, 30),
    SANDSTORM(30, 60),
    FOG(20, 70)
    ;

    private int attack;
    private int airAttack;

    Weather(int attack, int airAttack) {
        this.attack = attack;
        this.airAttack = airAttack;
    }

    public static Weather stringToWeather(String name) {
        for (Weather weather : Weather.values()) {
            if(weather.name().equalsIgnoreCase(name))
                return weather;
        }
        return null;
    }

    public int getAttack() {
        return attack;
    }

    public int getAirAttack() {
        return airAttack;
    }
}
