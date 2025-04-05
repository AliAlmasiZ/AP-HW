package models.enums;

public enum Country {
    GERMAN_REICH,
    UNITED_STATES,
    SOVIET_UNION,
    JAPAN,
    UNITED_KINGDOM;


    @Override
    public String toString() {
        switch (this) {
            case GERMAN_REICH -> {
                return "German Reich";
            }
            case UNITED_STATES -> {
                return "United States";
            }
            case SOVIET_UNION -> {
                return "Soviet Union";
            }
            case JAPAN -> {
                return "Japan";
            }
            case UNITED_KINGDOM -> {
                return "United Kingdom";
            }
            default -> {
                return null;
            }
        }
    }

    public static Country stringToCountry (String country) {
        switch (country) {
            case "German Reich" -> {
                return GERMAN_REICH;
            }
            case "United States" -> {
                return UNITED_STATES;
            }
            case "Soviet Union" -> {
                return SOVIET_UNION;
            }
            case "Japan" -> {
                return JAPAN;
            }
            case "United Kingdom" -> {
                return UNITED_KINGDOM;
            }
        }
    }
}
