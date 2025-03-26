package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Commands {
    /* common commands */
    GO_TO("^go\\s+to\\s+-m\\s+(?<nameOfTheMenu>.+)$"),
    GO_BACK("^go back$"),
    EXIT("^exit$")
    ;

    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
