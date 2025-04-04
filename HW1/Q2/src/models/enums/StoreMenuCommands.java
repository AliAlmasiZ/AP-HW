package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreMenuCommands implements Commands {
    /* StoreMenu Commands */
    ADD_PRODUCT(
            "^add\\s+product\\s+-n\\s+\"(?<name>.+)\"\\s+-pc\\s+(?<producerCost>-?\\d+\\.?\\d*)\\s+" +
                    "-p\\s+(?<price>-?\\d+\\.?\\d*)\\s+-about\\s+\"(?<aboutThisItem>.+)\"\\s+-np\\s+(?<NumberOfProductToSell>-?\\d+)$"
    ),
    APPLY_DISCOUNT(
            "^apply\\s+discount\\s+-p\\s+(?<productID>-?\\d+)\\s+" +
                    "-d\\s+(?<discountPercentage>-?\\d+)\\s+-q\\s+(?<quantity>-?\\d+)$"
    ),
    SHOW_PROFIT("^show\\s+profit$"),
    SHOW_LIST_OF_PRODUCTS("^show\\s+list\\s+of\\s+products$"),
    ADD_STOCK("^add\\s+stock\\s+-product\\s+(?<productId>-?\\d+)\\s+-amount\\s+(?<amount>-?\\d+)$"),
    UPDATE_PRICE("^update\\s+price\\s+-product\\s+(?<productId>-?\\d+)\\s+-price\\s+(?<newPrice>-?\\d+\\.?\\d*)$")

    ;

    StoreMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    private final String pattern;

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
