package models;

import models.enums.FactoryType;

public class Factory {
    private final Tile position;
    private final String name;
    private final FactoryType factoryType;
    private int produced;

    public Tile getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public FactoryType getFactoryType() {
        return factoryType;
    }

    public Factory(Tile position, String name, FactoryType factoryType) {
        this.position = position;
        this.name = name;
        this.factoryType = factoryType;
    }

    public int productionLeft() {
        return factoryType.getMaximumProduction() - produced;
    }

    public int run(int manPower) {
        int production = manPower * factoryType.getProductionPerManPower();
        if(factoryType.equals(FactoryType.FUEL_REFINERY))
            production = (production * position.getTerrain().getFuelProduction())/ 100;
        if(production > productionLeft())
            production = productionLeft();
        this.produced += production;
        return production;


    }
}
