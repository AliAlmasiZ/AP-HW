package models.enums;

/*
Explanation:
- In our app, groups have some types that are constants.
- In these cases, we use enums to define them and use them in our code.
- put those types here and use them in your code.
 */
public enum GroupType {
    HOME,
    TRIP,
    ZAN_O_BACHE,
    OTHER;


    @Override
    public String toString() {
        switch (this) {
            case HOME -> {
                return "Home";
            }
            case TRIP -> {
                return "Trip";
            }
            case ZAN_O_BACHE -> {
                return "Zan-o-Bache";
            }
            case OTHER -> {
                return "Other";
            }
        }
        return null;
    }

    public static GroupType stringToGroupType(String type) {
        switch (type) {
            case "Home" -> {
                return GroupType.HOME;
            }
            case "Trip" -> {
                return GroupType.TRIP;
            }
            case "Zan-o-Bache" -> {
                return GroupType.ZAN_O_BACHE;
            }
            case "Other" -> {
                return GroupType.OTHER;
            }
        }
        return null;
    }
}
