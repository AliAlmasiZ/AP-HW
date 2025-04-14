package models;

import models.enums.BattalionType;

public class Battalion {
    private Tile position;
    private BattalionType type;
    private String name;

    public Battalion(Tile position, BattalionType type, String name) {
        this.position = position;
        this.type = type;
        this.name = name;
    }

    public Tile getPosition() {
        return position;
    }

    public BattalionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
