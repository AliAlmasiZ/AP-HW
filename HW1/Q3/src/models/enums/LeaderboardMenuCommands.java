package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LeaderboardMenuCommands implements Commands{
    SHOW_CURRENT_MENU("show\\s+current\\s+menu"),
    BACK("back"),
    EXIT("exit"),
    SHOW_RANKING("show\\s+ranking"),
    SHOW_HISTORY("show\\s+history")
    ;


    private final String regex;

    LeaderboardMenuCommands(String regex) {
        this.regex = regex;
    }


    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }
}
