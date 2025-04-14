package models.enums;

public enum BattalionType {
    INFANTRY(
            15, 20, 15, 20, 15
    ),
    NAVY(
            20, 15, 15, 15, 20
    ),
    PANZER(
            25, 20, 20, 10, 25
    ),
    AIRFORCE(
            10, 25, 15, 20, 15
    );

    private final int germanReichPower;
    private final int sovietUnionPower;
    private final int unitedStatesPower;
    private final int unitedKingdomPower;
    private final int japanPower;

    private

    BattalionType(int germanReichPower, int sovietUnionPower,
              int unitedStatesPower, int unitedKingdomPower,
              int japanPower) {
        this.germanReichPower = germanReichPower;
        this.sovietUnionPower = sovietUnionPower;
        this.unitedStatesPower = unitedStatesPower;
        this.unitedKingdomPower = unitedKingdomPower;
        this.japanPower = japanPower;
    }

    public int getGermanReichPower() {
        return germanReichPower;
    }

    public int getSovietUnionPower() {
        return sovietUnionPower;
    }

    public int getUnitedStatesPower() {
        return unitedStatesPower;
    }

    public int getUnitedKingdomPower() {
        return unitedKingdomPower;
    }

    public int getJapanPower() {
        return japanPower;
    }
}
