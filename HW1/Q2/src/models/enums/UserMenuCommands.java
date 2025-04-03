package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserMenuCommands implements Commands {
    /* UserMenu commands */
    LIST_MY_ORDERS("^list\\s+my\\s+orders$"),
    SHOW_ORDER_DETAILS("^show\\s+order\\s+details\\s+-id\\s+(?<orderId>-?\\d+?)$"), //non-integer gets invalid command;
    EDIT_NAME("^edit\\s+name\\s+-fn\\s+(?<firstName>.+?)\\s+-ln\\s+(?<lastName>.+?)\\s+-p\\s+(?<password>.+?)$"),
    NAME("[A-Z][a-z]+"), //length check in controller
    EDIT_EMAIL("edit\\s+email\\s+-e\\s+(?<email>.+?)\\s+-p\\s+(?<password>.+?)"),
    EMAIL("^(?!\\S*[.]\\S*[.]\\S*@)[a-zA-Z\\d.]+@[a-z]+\\.com$"),
    EDIT_PASSWORD("^edit\\s+password\\s+-np\\s+(?<newPass>.+?)\\s+-op\\s+(?<oldPass>.+?)$"),
    PASSWORD("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+"),

    SHOW_MY_INFO("^show\\s+my\\s+info$"),
    ADD_ADDRESS(
            "^add\\s+address\\s+-country\\s+(?<country>.+?)\\s+" +
            "-city\\s+(?<city>.+?)\\s+-street\\s+(?<street>.+?)\\s+-postal\\s+(?<postal>.+?)$"
    ),
    POSTAL("\\d{10}"),
    DELETE_ADDRESS("^delete\\s+address\\s+-id\\s+(?<id>-?\\d+)$"),//non-integer gets invalid command!
    LIST_ADDRESS("^list\\s+my\\s+addresses$"),
    ADD_CREDIT_CARD(
            "^add\\s+a\\s+credit\\s+card\\s+-number\\s+(?<cardNumber>.+?)\\s+-ed\\s+(?<expirationDate>.+?)\\s+" +
                    "-cvv\\s+(?<cvv>.+?)\\s+-initialValue\\s+(?<initialValue>-?\\d+\\.?\\d*)$"

    ),
    CARD_NUMBER("\\d{16}"),
    CVV("\\d{3,4}"),
    EXPIRATION_DATE("(?<month>\\d{2})/(?<year>\\d{2})"),
    CHARGE_CREDIT_CARD("^Charge\\s+credit\\s+card\\s+-a\\s+(?<amount>-?\\d+.?\\d*)\\s+-id\\s+(?<id>-?\\d+?)$"),
    CHECK_CREDIT_CARD_BALANCE("^Check\\s+credit\\s+card\\s+balance\\s+-id\\s+(?<id>-?\\d+?)$"),
    SHOW_PRODUCTS_IN_CART("^show\\s+products\\s+in\\s+cart$"),
    CHECKOUT("^checkout\\s+-card\\s+(?<cardID>-?\\d+)\\s+-address\\s+(?<addressId>-?\\d+)$"),
    REMOVE_PRODUCT("^remove\\s+from\\s+cart\\s+-product\\s+(?<productID>-?\\d+)\\s+-quantity\\s+(?<amount>-?\\d+)$"),

    ;

    UserMenuCommands(String pattern) {
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
