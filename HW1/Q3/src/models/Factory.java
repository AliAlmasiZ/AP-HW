package models;

import models.enums.FactoryType;

public class Factory {
    private final Tile position;
    private final String name;
    private final FactoryType factoryType;

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
}
