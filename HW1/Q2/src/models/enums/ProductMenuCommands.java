package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProductMenuCommands implements Commands{
    /* ProductMenu Commands */
    SHOW_PRODUCTS("^show\\s+products\\s+-sortBy\\s+(?<sortBy>rate|higher price to lower|lower price to higher|number of sold)$"),
    SHOW_INFORMATION("^show\\s+information\\s+of\\s+-id\\s+(?<productId>.+?)"),
    RATE_PRODUCT("Rate\\s+product\\s+-r\\s+(?<number>.+?)(\\s+-m \"(?<message>.+?)\")?\\s+-id\\s+(?<id>-?\\d+)$"),

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
