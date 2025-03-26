package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreMenuCommands implements Commands {
    /* common commands */
    GO_TO("^go\\s+to\\s+-m\\s+(?<nameOfTheMenu>.+)$"),
    GO_BACK("^go back$"),
    EXIT("^exit$"),
    /* StoreMenu Commands */
    ADD_PRODUCT(
            "^add product\\s+-n\\s+\"(?<name>.+?)\"\\s+-pc\\s+(?<producerCost>.+?)\\s+" +
                    "-p\\s+(?<price>.+?)]\\s+-about \"<aboutThisItem>\" -np <NumberOfProductToSell>"
    ),
    APPLY_DISCOUNT(
            "apply\\s+discount\\s+-p\\s+(?<productID>.+?)\\s+" +
                    "-d\\s+(?<discountPercentage>.+?)\\s+-q\\s+(?<quantity>.+?)$"
    ),
    SHOW_PROFIT("show\\s+profit"),
    SHOW_LIST_OF_PRODUCTS("show\\s+list\\s+of\\s+products"),
    ADD_STOCK("add\\s+stock\\s+-product\\s+(?<productId>.+?)\\s+-amount\\s+(?<amount>.+?)"),

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
