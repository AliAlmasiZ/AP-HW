package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements Commands {
    SHOW_CURRENT_MENU("show\\s+current\\s+menu"),
    BACK("back"),
    EXIT("exit"),
    LOGIN("^login\\s+-username\\s+(?<username>\\S*)\\s+-password\\s+(?<password>\\S*)$"),
    FORGET_PASSWORD("^forget-password\\s+-username\\s+(?<username>\\S*)\\s+-email\\s+(?<email>\\S*)$"),
    ;


    private final String regex;

    LoginMenuCommands(String regex) {
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
