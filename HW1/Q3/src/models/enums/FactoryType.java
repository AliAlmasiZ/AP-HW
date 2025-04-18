package models.enums;

import models.App;
import models.Tile;

public enum FactoryType {
    STEEL_FACTORY( 10000, 0, 10, 30000),
    SULFUR_FACTORY(20000, 0, 20, 20000),
    FUEL_REFINERY(50000, 5000, 50, 100000);

    private final int manPowerCost;
    private final int steelCost;
    private final int productionPerManPower;
    private final int maximumProduction;

    FactoryType(int manPowerCost, int steelCost, int productionPerManPower, int maximumProduction) {
        this.manPowerCost = manPowerCost;
        this.steelCost = steelCost;
        this.productionPerManPower = productionPerManPower;
        this.maximumProduction = maximumProduction;
    }

    public static FactoryType stringToFactoryType(String name) {
        for (FactoryType value : FactoryType.values()) {
            if(name.equalsIgnoreCase(value.getName()))
                return value;
        }
        return null;
    }

    public String getName() {
        return this.name().toLowerCase().replace("ـ", " ");
    }

    public int getManPowerCost(Tile tile) {
        int cost = manPowerCost  * tile.getTerrain().getFactoryCost() / 100;
        if(App.getActiveUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.COMMUNISM))
            cost = manPowerCost  * tile.getTerrain().getFactoryCost() / 200;
        return cost;
    }

    public int getSteelCost(Tile tile) {
        int cost = steelCost * tile.getTerrain().getFactoryCost() / 100;
        if(App.getActiveUser().getPlayingCountry().getLeader().getIdeology().equals(Ideology.COMMUNISM))
            cost = steelCost  * tile.getTerrain().getFactoryCost() / 200;
        return cost;
    }

    public int getProductionPerManPower() {
        return productionPerManPower;
    }

    public int getMaximumProduction() {
        return maximumProduction;
    }

}
