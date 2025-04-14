package models.enums;

import java.util.Arrays;
import java.util.List;

public enum TileData {
    TILE_1(1, "Soviet Union", Arrays.asList(2, 5), Arrays.asList()),
    TILE_2(2, "Soviet Union", Arrays.asList(1, 3, 6), Arrays.asList()),
    TILE_3(3, "Soviet Union", Arrays.asList(2, 4, 7), Arrays.asList()),
    TILE_4(4, "Soviet Union", Arrays.asList(3, 8), Arrays.asList()),
    TILE_5(5, "Soviet Union", Arrays.asList(1, 6, 9), Arrays.asList()),
    TILE_6(6, "Soviet Union", Arrays.asList(2, 5, 7, 10), Arrays.asList()),
    TILE_7(7, "Soviet Union", Arrays.asList(6, 8, 3, 11), Arrays.asList()),
    TILE_8(8, "Soviet Union", Arrays.asList(4, 7, 12), Arrays.asList(35, 36, 54)),
    TILE_9(9, "Soviet Union", Arrays.asList(5, 10, 13), Arrays.asList()),
    TILE_10(10, "Soviet Union", Arrays.asList(6, 9, 11, 14), Arrays.asList()),
    TILE_11(11, "Soviet Union", Arrays.asList(7, 10, 12, 15), Arrays.asList()),
    TILE_12(12, "Soviet Union", Arrays.asList(8, 11, 35, 16), Arrays.asList()),
    TILE_13(13, "Soviet Union", Arrays.asList(9, 14), Arrays.asList()),
    TILE_14(14, "Soviet Union", Arrays.asList(10, 13, 15, 17, 18), Arrays.asList()),
    TILE_15(15, "Soviet Union", Arrays.asList(11, 14, 16, 19, 20), Arrays.asList()),
    TILE_16(16, "Soviet Union", Arrays.asList(15, 12, 39, 21, 22), Arrays.asList()),
    TILE_17(17, "United States", Arrays.asList(14, 18, 23), Arrays.asList()),
    TILE_18(18, "United States", Arrays.asList(14, 17, 19, 24), Arrays.asList()),
    TILE_19(19, "United States", Arrays.asList(15, 18, 20, 25), Arrays.asList()),
    TILE_20(20, "United States", Arrays.asList(15, 19, 21, 26), Arrays.asList()),
    TILE_21(21, "United States", Arrays.asList(16, 20, 22, 27), Arrays.asList()),
    TILE_22(22, "United States", Arrays.asList(16, 21, 43, 28), Arrays.asList()),
    TILE_23(23, "United States", Arrays.asList(17, 24, 29), Arrays.asList()),
    TILE_24(24, "United States", Arrays.asList(18, 23, 25, 30), Arrays.asList()),
    TILE_25(25, "United States", Arrays.asList(19, 24, 26, 31), Arrays.asList()),
    TILE_26(26, "United States", Arrays.asList(20, 25, 27, 32), Arrays.asList()),
    TILE_27(27, "United States", Arrays.asList(21, 26, 28, 33), Arrays.asList()),
    TILE_28(28, "United States", Arrays.asList(22, 27, 47, 34), Arrays.asList()),
    TILE_29(29, "United States", Arrays.asList(23, 30), Arrays.asList()),
    TILE_30(30, "United States", Arrays.asList(24, 29, 31), Arrays.asList()),
    TILE_31(31, "United States", Arrays.asList(25, 30, 32), Arrays.asList()),
    TILE_32(32, "United States", Arrays.asList(26, 31, 33), Arrays.asList()),
    TILE_33(33, "United States", Arrays.asList(27, 32, 34), Arrays.asList()),
    TILE_34(34, "United States", Arrays.asList(28, 33), Arrays.asList(47, 48, 49, 52)),
    TILE_35(35, "German Reich", Arrays.asList(12, 36, 39), Arrays.asList(8, 36, 54)),
    TILE_36(36, "German Reich", Arrays.asList(35, 37, 40), Arrays.asList(8, 35, 54)),
    TILE_37(37, "German Reich", Arrays.asList(36, 38, 41, 54), Arrays.asList()),
    TILE_38(38, "German Reich", Arrays.asList(37, 42, 55), Arrays.asList()),
    TILE_39(39, "German Reich", Arrays.asList(35, 16, 40, 43), Arrays.asList()),
    TILE_40(40, "German Reich", Arrays.asList(36, 39, 41, 44), Arrays.asList()),
    TILE_41(41, "German Reich", Arrays.asList(37, 40, 42, 45), Arrays.asList()),
    TILE_42(42, "German Reich", Arrays.asList(38, 41, 46), Arrays.asList()),
    TILE_43(43, "German Reich", Arrays.asList(22, 39, 44, 47), Arrays.asList()),
    TILE_44(44, "German Reich", Arrays.asList(40, 43, 45, 48), Arrays.asList()),
    TILE_45(45, "German Reich", Arrays.asList(41, 44, 46, 49), Arrays.asList()),
    TILE_46(46, "German Reich", Arrays.asList(42, 45, 50), Arrays.asList()),
    TILE_47(47, "German Reich", Arrays.asList(28, 43, 48), Arrays.asList(34, 48, 49, 52)),
    TILE_48(48, "German Reich", Arrays.asList(47, 49, 44), Arrays.asList(34, 47, 49, 52)),
    TILE_49(49, "German Reich", Arrays.asList(48, 50, 45), Arrays.asList(34, 47, 48, 52)),
    TILE_50(50, "German Reich", Arrays.asList(49, 46, 51, 52), Arrays.asList()),
    TILE_51(51, "Japan", Arrays.asList(50, 53), Arrays.asList()),
    TILE_52(52, "Japan", Arrays.asList(50, 53), Arrays.asList(34, 47, 48, 49)),
    TILE_53(53, "Japan", Arrays.asList(51, 52), Arrays.asList()),
    TILE_54(54, "United Kingdom", Arrays.asList(55, 37), Arrays.asList(8, 35, 36)),
    TILE_55(55, "United Kingdom", Arrays.asList(54, 56, 38), Arrays.asList()),
    TILE_56(56, "United Kingdom", Arrays.asList(55), Arrays.asList());

    private final int id;
    private final String country;
    private final List<Integer> landNeighbors;
    private final List<Integer> seaNeighbors;

    TileData(int id, String country, List<Integer> landNeighbors, List<Integer> seaNeighbors) {
        this.id = id;
        this.country = country;
        this.landNeighbors = landNeighbors;
        this.seaNeighbors = seaNeighbors;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public List<Integer> getLandNeighbors() {
        return landNeighbors;
    }

    public List<Integer> getSeaNeighbors() {
        return seaNeighbors;
    }
}
