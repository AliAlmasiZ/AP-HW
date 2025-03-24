package models.enums;

/*
Explanation:
- We need to define a currency enum.
- currencies in out app are some constants that we need to define them in our code once and use them in our code.
- each currency has some data, put them here and use some methods to work with currencies so simply.
 */

public enum Currency {
    GTC(10),
    SUD(5),
    QTR(2);

    private final Integer value;

    Currency(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case GTC -> {
                return "GTC";
            }
            case SUD -> {
                return "SUD";
            }
            case QTR -> {
                return "QTR";
            }
            default -> {
                return null;
            }
        }
    }

    public static Currency stringToCurrency(String input) {
        switch (input) {
            case "GTC" -> {
                return GTC;
            }
            case "SUD" -> {
                return SUD;
            }
            case "QTR" -> {
                return QTR;
            }
            default -> {
                return null;
            }
        }
    }
}
