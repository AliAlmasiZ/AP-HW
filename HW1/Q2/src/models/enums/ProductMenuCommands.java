package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProductMenuCommands implements Commands{
    /* ProductMenu Commands */
    SHOW_PRODUCTS("^show\\s+products\\s+-sortBy\\s+(?<sortBy>rate|higher price to lower|lower price to higher|number of sold)$"),
    SHOW_INFORMATION("^show\\s+information\\s+of\\s+-id\\s+(?<productId>-?\\d+)"),
    RATE_PRODUCT("^Rate\\s+product\\s+-r\\s+(?<number>-?\\d+?)(\\s+-m\\s+\"(?<message>.+?)\")?\\s+-id\\s+(?<id>-?\\d+)$"),
    ADD_TO_CART("^add\\s+to\\s+cart\\s+-product\\s+(?<productID>-?\\d+)\\s+-quantity\\s+(?<amount>-?\\d+)$"),
    NEXT_PAGE("show\\s+next\\s+10\\s+products"),
    PAST_PAGE("show\\s+past\\s+10\\s+products"),

    ;

    ProductMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    private final String pattern;

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
